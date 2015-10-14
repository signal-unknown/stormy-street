package dat255.chalmers.stormystreet.services;

import android.app.ActivityManager;
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

    public synchronized void setCurrBus(String mac){
        for(Map.Entry<Integer, String> entry : Constants.busMacVin.entrySet()){
            if(entry.getValue().equals(mac)){
                this.currBusNum = entry.getKey();
                Log.d("Wifiservice", "Curr buss num: " + this.currBusNum);
            }
        }
    }

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
    public synchronized void resetTimestamps(){
        startTime = 0;
        endTime =0;
    }
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
                    model.setCurrentTrip(new CurrentTrip(startTime, currBusNum)); //Set correct distance
                    setUserOnBus(true);

                } else {
                    //setUserOnBus(false);
                }
            }
        };
        mHandler.postDelayed(mRun, DELAY_TIME);

    }
    public synchronized void setUserOnBus(boolean isOn){
        Intent currentTripIntent = new Intent(getApplicationContext(), CurrentTripService.class);

        if(isOn){
            currentTripIntent.setAction("Turnon");
            getApplicationContext().startService(currentTripIntent);
        }else{
            currentTripIntent.setAction("Turnoff");
            getApplicationContext().startService(currentTripIntent);

        }
        ((GlobalState)getApplication()).getModel().setIsOnBus(isOn);
    }

    public List<String> getMacAddresses(){
        return this.macAddresses;
    }
}
