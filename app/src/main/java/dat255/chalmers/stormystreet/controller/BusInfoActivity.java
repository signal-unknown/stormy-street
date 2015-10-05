package dat255.chalmers.stormystreet.controller;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.lang.Thread;

import dat255.chalmers.stormystreet.Constants;
import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.*;
import dat255.chalmers.stormystreet.model.bus.BusNotFoundException;
import dat255.chalmers.stormystreet.model.bus.IBus;

// TODO: Use model listener to automatically get when there is new data - no need for updating thread
public class BusInfoActivity extends AppCompatActivity {

    private static final int UPDATE_INTERVAL = 3000; // How often the bus info will update

    public TextView busInfoText;
    private Toolbar toolbar;
    GpsCoord gpsInfo;
    public Boolean stopThread = false;


    private IBus busInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        setupToolbar();
        getBusData();

        busInfoText = (TextView) findViewById(R.id.BusInfoView);
        new Thread(new UpdateInfo()).start(); // starting new thread

    }

    private void getBusData() {
        String vinNumber = getIntent().getStringExtra(Constants.EXTRA_BUS_INFO_BUS_ID);
        try {
            if (vinNumber != null) {
                busInfo = ((GlobalState)getApplication()).getModel().getBus(Integer.parseInt(vinNumber));
            } else {
                throw new BusNotFoundException();
            }
        } catch (BusNotFoundException e) {
            finish(); // Close the activity
        }
    }

    private void setupToolbar() {
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
    }

    // Getting info from model
    private void getBusInfo(int v) {
        // TODO: Show info from model
    }

    private class UpdateInfo implements Runnable {
        int v = 0;
        @Override
        public void run() {
            while (!stopThread) {
                try {
                    Thread.sleep(UPDATE_INTERVAL);
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
            }
        }
    }
}
