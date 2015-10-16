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
 * This class receives broadcasts from operative system of android.
 * Different actions are taking upon different intents that were sent.
 *
 */
public class WifiReceiver extends BroadcastReceiver {

    private Intent wifiIntent;

    /*
        Starts different services depending on the received intent.
        If new mac addresses are seen, starts WifiService to check if you are near a bus, or just
        went off a bus and finished your trip.

        If you changed your connection, it assumes you turned off your wifi therefore it starts
        service and tells it to check if you have finished your trip.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Wifireceiver", "Received intent: " + intent.getAction());
        if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
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
