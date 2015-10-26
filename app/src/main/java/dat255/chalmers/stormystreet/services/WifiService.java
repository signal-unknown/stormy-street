package dat255.chalmers.stormystreet.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dat255.chalmers.stormystreet.Constants;
import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.model.CurrentTrip;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.model.bus.BusTrip;

/**
 * @author Maxim Goretskyy
 * Created by maxim on 15-09-24.
 *
 * This service takes care of when a user is stepping on and off the bus.
 * When your phone scans for mac-addresses and notices one of the busses, it waits a DELAY_TIME
 * (30 seconds) before scanning again and checking if you are still near a bus. We conclude that you
 * are on the bus and start the timers, and tell CurrentTripService to start.
 * Once you turn off your Wifi or walk away from the bus range, the service will stop timer and tell
 * CurrentTripService to stop.
 */
public class WifiService extends IntentService {
    private WifiManager mWifiManager;
    private List<String> macAddresses;
    private HandlerThread hThread;
    private final int DELAY_TIME = 30000; //30 seconds
    private static long startTime;
    private static long endTime;
    private String currMac;
    private int currBusNum;

    public WifiService(){
        super("dat255.chalmers.stormystreet.services.WifiService");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WifiService(String name) {
        super(name);
    }


    /**
     *  Receives intent from WifiReceiver.
     *  If you are near a bus (by MAC-address range) then start counting, otherwise stop counting
     *  incase you moved away from a bus and finished your trip.
     *  If your wifi state changed, check if you finished your trip as well. AKA Wifi turned off.
     */
    @Override
    protected synchronized void onHandleIntent(Intent intent) {
        Log.d("Wifiservice", "Received intent: " + intent.getAction());
        if (intent.getAction().equals(Constants.ACTION_GET_MAC)){
            scanMacs();
            Log.d("Wifiservice", "Scanned MACs");

            if (isNearBus()){
                Log.d("Wifiservice", "Near a bus");
                /*Todo use API to check if you are connected to network as well
                    might not need to wait 30 secs then
                */
                startCount();

            } else {
                checkEndPoint();
            }

        }else if(intent.getAction().equals(Constants.ACTION_WIFI_CHANGED)){
            Log.d("Wifiservice", "Got action wifi changed");
            checkEndPoint();
        }
    }

    /**
     *   Checks if you are near a bus.
     */
    public synchronized boolean isNearBus(){
        for(String mac : Constants.busMacVin.values()){
            if(macAddresses.contains(mac)){
                currMac = mac;
                Log.d("Wifiservice", "Curr buss mac: " + currMac);
                return true;
            }
        }
        return false;
    }


    /**
     *   Scan BSSIDs/MACs around you.
     */
    public synchronized void scanMacs(){
        mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> mScanResults = mWifiManager.getScanResults();
        macAddresses = new ArrayList<>();
        macAddresses.clear();
        for(ScanResult network : mScanResults){
            macAddresses.add(network.BSSID.toLowerCase());
            // Log.d("Scanned",network.BSSID.toLowerCase());
        }

    }

    /**
    *   Starts count if you have been near the same bus after 30 seconds since the first time.
    *   Same bus is determined by the uniquenees of the mac/bssid.
    *   Tells model to start a new currentTrip if succeeded.
    */
    public synchronized void startCount(){
        hThread = new HandlerThread("WifiService");
        hThread.start();
        Handler mHandler = new Handler(hThread.getLooper());
        Runnable mRun = new Runnable() {
            @Override
            public synchronized void run() {
                MainModel model = ((GlobalState)getApplication()).getModel();
                Log.d("Wifiservice", "Inside new thread");
                scanMacs();
                if(isNearBus() && startTime==0){
                    Log.d("Wifiservice", "On bus");
                    if (!currMac.equals("")) {
                        Log.d("Wifiservice", "Mac inside " + currMac);
                        setCurrBus(currMac);
                    }
                    startTime = System.currentTimeMillis()-DELAY_TIME;//compensating for delay
                    Log.d("Wifiservice", startTime + " my starttime");
                    Log.d("WifiService", "Current bus number new " + currBusNum);
                    model.setCurrentTrip(new CurrentTrip(startTime, currBusNum));
                    setUserOnBus(true);

                }
            }
        };
        mHandler.postDelayed(mRun, DELAY_TIME);

    }

    /**
     *   Checks if you're done with your trip.
     *   If you finished the trip, it tells model to add a new finished busTrip.
     *   And reset currentTrip as well as timestamps.
     */
    public synchronized void checkEndPoint(){
        Log.d("Wifiservice", "Inside checkendpoint");
        if(startTime !=0 && endTime == 0 ){//double check
            Log.d("Wifiservice", "No longer on bus");
            endTime = System.currentTimeMillis();
            Log.d("Wifiservice", "Endtime is " + endTime);
            //update model with end time here
            MainModel model = ((GlobalState) getApplication()).getModel();
            long distance = model.getCurrentTrip().getDistance();
            Log.d("Wifiservice", "Distance is " + distance);
            model.addBusTrip(new BusTrip(startTime, endTime, distance));
            model.setCurrentTrip(null);
            resetTimestamps();
            setUserOnBus(false);
        }
    }
    /**
     *  Sets the bus-VIN number of the current bus you are on.
     */
    public synchronized void setCurrBus(String mac){
        for(Map.Entry<Integer, String> entry : Constants.busMacVin.entrySet()){
            if(entry.getValue().equals(mac)){
                this.currBusNum = entry.getKey();
                Log.d("Wifiservice", "Curr buss num: " + this.currBusNum);
            }
        }
    }

    /**
     *  Handles logic when the CurrentTripService should be active or not.
     *  If you are on bus, the CurrentTripService is active and should be turned on, otherwise it
     *  should be turnedo off.
     */
    public synchronized void setUserOnBus(boolean isOn){
        Intent currentTripIntent = new Intent(getApplicationContext(), CurrentTripService.class);

        if(isOn){
            currentTripIntent.setAction(CurrentTripService.ACTION_TURN_ON);
            getApplicationContext().startService(currentTripIntent);
        }else{
            currentTripIntent.setAction(CurrentTripService.ACTION_TURN_OFF);
            getApplicationContext().startService(currentTripIntent);

        }
        ((GlobalState)getApplication()).getModel().setIsOnBus(isOn);
    }

    public synchronized void resetTimestamps(){
        startTime = 0;
        endTime =0;
    }

    public List<String> getMacAddresses(){
        return this.macAddresses;
    }
}
