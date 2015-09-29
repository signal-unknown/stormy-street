package dat255.chalmers.stormystreet;

import android.app.Application;
import android.util.Log;

import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.model.bus.BusTrip;


public class GlobalState extends Application {
    MainModel model;

    @Override
    public void onCreate(){
        super.onCreate();
        model = new MainModel();

        //################      DUMMY DATA      ################################################################
        model.getCurrentUser().getStatistics().addBusTrip(new BusTrip(System.currentTimeMillis()-200000, System.currentTimeMillis(), 20));
        model.getCurrentUser().getStatistics().addBusTrip(new BusTrip(System.currentTimeMillis()-150004, System.currentTimeMillis(), 20));
        //######################################################################################################


        Log.i("Globalstate", "App started");
    }

    public MainModel getModel(){
        return this.model;
    }
}
