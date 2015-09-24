package dat255.chalmers.stormystreet.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Goretskyy
 * Created on 15-09-23.
 * Sniffing MAC-addresses near you.
 */
public class WifiSniffer extends BroadcastReceiver {
    private WifiManager wifiManager;
    private Context mContext;
    private List<String> macAdresses;

    public WifiSniffer(Context mContext){
        this.mContext = mContext;
        wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        macAdresses = new ArrayList<>();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
            List<ScanResult> mScanResults = wifiManager.getScanResults();
            macAdresses.clear();
            for (ScanResult network : mScanResults){
                macAdresses.add(network.BSSID.toString());
            }
        }
    }

    public List<String> getMacAdresses(){
        return this.macAdresses;
    }
}
