package dat255.chalmers.stormystreet;

import android.app.Application;
import android.util.Log;


public class GlobalState extends Application {

    private static String ELECTRICITY_KEY;
    private static String MAPS_KEY;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("Globalstate", "App started");
        ELECTRICITY_KEY = getString(R.string.electricity_api);
        MAPS_KEY = getString(R.string.google_maps_key);
    }

    public static String getElectricityKey(){
        return ELECTRICITY_KEY;
    }

    public static String getMapsKey(){
        return MAPS_KEY;
    }
}
