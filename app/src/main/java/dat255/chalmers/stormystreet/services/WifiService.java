package dat255.chalmers.stormystreet.services;

import android.app.IntentService;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import java.util.ArrayList;
import java.util.List;

import dat255.chalmers.stormystreet.Constants;

/**
 * @author Maxim Goretskyy
 * Created by maxim on 15-09-24.
 */
public class WifiService extends IntentService {
    private WifiManager mWifiManager;
    private List<String> macAddresses;

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
            mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
            List<ScanResult> mScanResults = mWifiManager.getScanResults();
            macAddresses = new ArrayList<>();
            macAddresses.clear();
            for(ScanResult network : mScanResults){
                macAddresses.add(network.BSSID.toLowerCase());
            }
            //update model
            //broadcast shit to listeners
        }
    }

    public List<String> getMacAddresses(){
        return this.macAddresses;
    }

    public boolean isNearBus(){
       for(String mac : Constants.busMacVin.values()){
           if(macAddresses.contains(mac)){
               return true;
           }
       }
        return false;
    }
}
