package dat255.chalmers.stormystreet;

import android.app.Application;
import android.util.Log;

import dat255.chalmers.stormystreet.model.MainModel;


public class GlobalState extends Application {
    MainModel model;

    @Override
    public void onCreate(){
        super.onCreate();
        model = new MainModel();
        Log.i("Globalstate", "App started");
    }
}
