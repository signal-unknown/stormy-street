package dat255.chalmers.stormystreet;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.FacebookSdk;

import dat255.chalmers.stormystreet.controller.SQLiteDataSource;
import dat255.chalmers.stormystreet.model.AppSQLiteWrapper;
import dat255.chalmers.stormystreet.model.DataValue;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.model.bus.BusTrip;
import dat255.chalmers.stormystreet.model.bus.IBus;
import dat255.chalmers.stormystreet.model.bus.IBusTrip;
import dat255.chalmers.stormystreet.services.AlarmReceiver;

/**
 * This is a singleton class that will be created by created by the system when the app launches.
 * It represents the whole application and since it's globally available within the app it also
 * hold our app model. The GlobalState class handles loading and saving the model from and to
 * persistent data storage.
 */
public class GlobalState extends Application {
    private MainModel model;
    private SQLiteDataSource dataSource;


    @Override
    public void onCreate(){
        super.onCreate();
        // Initialize before usage
        FacebookSdk.sdkInitialize(getApplicationContext());

        model = new MainModel();
        dataSource = new SQLiteDataSource(getApplicationContext());
        Log.i("Globalstate", "App started");
        loadModel();

        startFacebookScoreUpdater();
    }

    public void loadModel(){
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
        dataSource.open();
        long lastEndTime = 0;
        DataValue value;
        try {
            lastEndTime= dataSource.getLastTimeStamp();
        }catch(CursorIndexOutOfBoundsException ex){

        }
        for(IBusTrip trip:model.getAllBusTrips()){

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

    /**
     * Starts sending the AlarmReceiver an alarm ever fifteen minutes which in turn
     * updates the users current score to facebook
     */
    private void startFacebookScoreUpdater(){
        AlarmReceiver.setModel(model);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),900000,
                pendingIntent);
        Log.d("GlobalState", "Started facebook updating");
    }
}
