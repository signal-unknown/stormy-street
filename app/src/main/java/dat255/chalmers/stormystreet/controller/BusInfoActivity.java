package dat255.chalmers.stormystreet.controller;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.lang.Thread;

import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.*;

/**
 * Created by DavidF on 2015-10-01.
 */
public class BusInfoActivity extends AppCompatActivity {

    public TextView busInfo;
    private Toolbar toolbar;
    private MainModel model = new MainModel();
    GpsCoord gpsInfo;
    public Boolean stopThread = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopThread = true; // stop thread
                    onBackPressed();
                }
            });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        busInfo = (TextView) findViewById(R.id.BusInfoView);
        new Thread(new UpdateInfo()).start();      // starting new thread

    }
    public void getBusInfo(int v) {             // Getting info from model
        busInfo.setText("Information about selected bus: " + "\n \n" +
                "User name: "
                + model.getCurrentUsername() + "\n\n" +
                "Statistics:  "
                + model.getCurrentUser().getStatistics() + "\n\n " +
               "Distance/time:  "
                + model.getCurrentTrip() + "\n" + "\n\n" +
                "Updating : " + v);

        //Add other information like GPS here
    }
    class UpdateInfo implements Runnable {
        int v = 0;
        @Override
        public void run() {
            while (stopThread != true) {
                try {
                    Thread.sleep(3000);  // calling thread every 3000 ms = 3 seconds for updating information about selected bus...
                    runOnUiThread(new Runnable() {   // for modifying view
                        @Override
                        public void run() {
                            getBusInfo(v);
                            v++;
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Bus info: ", "................This thread is doing some work....................." );
            }
        }
    }
}
