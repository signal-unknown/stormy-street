package dat255.chalmers.stormystreet.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import dat255.chalmers.stormystreet.Constants;
import dat255.chalmers.stormystreet.services.WifiService;

/**
 * @author Maxim Goretskyy
 * Created on 15-09-23.
 */
public class WifiReceiver extends BroadcastReceiver {

    private Intent wifiIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Wifireceiver", "Received intent: " + intent.getAction());
        Log.d("Wifireceiver", "Intent we compare with: " + WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

        if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            Log.d("Wifireceiver", "Inside if");
            wifiIntent = new Intent(context, WifiService.class);
            wifiIntent.setAction(Constants.ACTION_GET_MAC);
            context.startService(wifiIntent);
        }else if (intent.getAction().equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)){
            Log.d("Wifireceiver", "Supp conn change action");
            wifiIntent = new Intent(context, WifiService.class);
            wifiIntent.setAction(Constants.ACTION_WIFI_CHANGED);
            context.startService(wifiIntent);
        }
    }
}
