package dat255.chalmers.stormystreet;

import android.app.Application;
import android.util.Log;


public class GlobalState extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("Globalstate", "App stared");
    }
}
