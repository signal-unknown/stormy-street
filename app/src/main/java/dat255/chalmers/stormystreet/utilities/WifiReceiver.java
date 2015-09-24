package dat255.chalmers.stormystreet.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
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
        if (intent.getAction() == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
            wifiIntent = new Intent(context, WifiService.class);
            wifiIntent.setAction(Constants.ACTION_GET_MAC);
            context.startService(wifiIntent);
        }
    }
}
