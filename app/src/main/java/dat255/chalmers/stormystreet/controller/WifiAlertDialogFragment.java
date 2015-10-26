package dat255.chalmers.stormystreet.controller;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import dat255.chalmers.stormystreet.R;

/**
 * A dialog for prompting the user to turn on the WiFi. WiFi must be enabled for the app to work
 * properly.
 *
 * @author Kevin Hoogendijk
 */
public class WifiAlertDialogFragment extends DialogFragment {

    private static final int MY_PERMISSION_WIFI = 1338;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.wifi_alert_title)
                .setMessage(R.string.wifi_alert_text)
                //Sets the buttons
                .setPositiveButton(R.string.wifi_alert_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
                    }
                }).setNeutralButton(R.string.wifi_alert_neutral, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Need to check for specific permission in Android Marshmallow and higher
                        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                            checkPermissions();
                        }
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

    @TargetApi(Build.VERSION_CODES.M)
    public void checkPermissions(){
        if (getActivity().checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CHANGE_WIFI_STATE}, MY_PERMISSION_WIFI);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch(requestCode){
            case MY_PERMISSION_WIFI:{
                // Attempt to close app if permission not granted
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);
                }
            }
        }
    }
}
