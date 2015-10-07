package dat255.chalmers.stormystreet;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import dat255.chalmers.stormystreet.controller.SQLiteDataSource;
import dat255.chalmers.stormystreet.model.AppSQLiteWrapper;
import dat255.chalmers.stormystreet.model.DataValue;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.model.bus.BusTrip;
import dat255.chalmers.stormystreet.model.bus.IBus;
import dat255.chalmers.stormystreet.model.bus.IBusTrip;


public class GlobalState extends Application {
    MainModel model;

    @Override
    public void onCreate(){
        super.onCreate();
        model = new MainModel();
        Log.i("Globalstate", "App started");
        loadModel();
    }

    public void loadModel(){
        SQLiteDataSource dataSource = new SQLiteDataSource(getApplicationContext());
        dataSource.open();
        long startTime, endTime, distance;
        for(DataValue value:dataSource.getAllDataValues()){
            startTime = Long.parseLong(value.getValues().get(0));
            endTime = Long.parseLong(value.getValues().get(1));
            distance = Long.parseLong(value.getValues().get(2));
            model.addBusTrip(new BusTrip(startTime, endTime, distance));
        }
        dataSource.close();
    }

    public void saveModel(){
        SQLiteDataSource dataSource = new SQLiteDataSource(getApplicationContext());
        dataSource.open();

        DataValue value;
        for(IBusTrip trip:model.getAllBusTrips()){
            long lastEndTime = dataSource.getLastTimeStamp();
            if(trip.getStartTime() > lastEndTime) {
                value = new DataValue();
                value.addValues(Long.toString(trip.getStartTime()), Long.toString(trip.getEndTime()), Long.toString(trip.getDistance()));
                dataSource.saveData(value);
            }
        }

        dataSource.close();
    }

    public MainModel getModel(){
        return this.model;
    }
}
