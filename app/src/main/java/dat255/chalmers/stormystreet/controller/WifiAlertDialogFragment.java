package dat255.chalmers.stormystreet.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import dat255.chalmers.stormystreet.R;

/**
 * @author Kevin Hoogendijk
 * @since 2015-10-02
 */
public class WifiAlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.wifi_alert_title)
                .setMessage(R.string.wifi_alert_text)
                .setPositiveButton(R.string.wifi_alert_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
                    }
                }).setNeutralButton(R.string.wifi_alert_neutral, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
                        wifiManager.setWifiEnabled(true);
                    }
                })
                .setNegativeButton(R.string.wifi_alert_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                        System.exit(0);
                    }
                });

        return builder.create();
    }
}
