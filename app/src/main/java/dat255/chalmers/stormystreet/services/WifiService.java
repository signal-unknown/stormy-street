package dat255.chalmers.stormystreet.services;

import android.app.IntentService;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import dat255.chalmers.stormystreet.Constants;
import dat255.chalmers.stormystreet.GlobalState;

/**
 * @author Maxim Goretskyy
 * Created by maxim on 15-09-24.
 */
public class WifiService extends IntentService {
    private WifiManager mWifiManager;
    private List<String> macAddresses;
    private final int DELAY_TIME = 30000; //30 seconds

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WifiService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent.getAction().equals(Constants.ACTION_GET_MAC)){
            scanMacs();

            if(isNearBus()){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scanMacs();//if you still can see the mac address of the bus after 30 seconds
                                    //then you are probably on the bus
                        if(isNearBus()){
                            //Todo update model with mac addresses and if user is near bus
                        }
                    }
                }, DELAY_TIME);
            }
        }
    }

    public boolean isNearBus(){
       for(String mac : Constants.busMacVin.values()){
           if(macAddresses.contains(mac)){
               return true;
           }
       }
        return false;
    }

    public void scanMacs(){
        mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> mScanResults = mWifiManager.getScanResults();
        macAddresses = new ArrayList<>();
        macAddresses.clear();
        for(ScanResult network : mScanResults){
            macAddresses.add(network.BSSID.toLowerCase());
        }
    }

    public List<String> getMacAddresses(){
        return this.macAddresses;
    }
}
