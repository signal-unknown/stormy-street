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
import dat255.chalmers.stormystreet.services.BusInfoUpdater;

// TODO: Use model listener to automatically get when there is new data - no need for updating thread

/**
 * @author David Fogelberg, Alexander Håkansson
 */
public class BusInfoActivity extends AppCompatActivity implements BusInfoUpdater.IBusInfoListener {

    private static final int UPDATE_INTERVAL = 3000; // How often the bus info will update

    private TextView debugTextView; // TODO: Implement real stat views
    private Toolbar toolbar;
    private Boolean stopThread = false;

    private MainModel model;

    private int busVin = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        setupToolbar();
        getBusData();
        debugTextView = (TextView) findViewById(R.id.debug_text_view);


        // The activity might be finishing if it can't get the bus with the VIN number provided
        if (!isFinishing()) {
            // Set the toolbar text to the bus reg number
            toolbar.setTitle(getString(R.string.bus) + " " + Constants.vinToRegNr(busVin));

            // Starting thread for getting data from model
            new BusInfoUpdater(this).execute(busVin);
            //new Thread(new UpdateInfo()).start();
        }
    }

    private void getBusData() {
        model = ((GlobalState)getApplication()).getModel();
        int vinNumber = getIntent().getIntExtra(Constants.EXTRA_BUS_INFO_BUS_ID, -1);
        try {
            if (vinNumber != -1) {
                busVin = vinNumber;
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
    private void getBusInfo() {
        // TODO: Show info from model;
        model = ((GlobalState)getApplication()).getModel();
        try {
            debugTextView.setText("Next stop: " + model.getBus(busVin).getNextStop());
        } catch (BusNotFoundException e) {
            // TODO: Deal with it 8-D
            e.printStackTrace();
        }

    }

    @Override
    public void busUpdated(IBus bus) {

    }

    private class UpdateInfo implements Runnable {
        @Override
        public void run() {
            while (!stopThread) {
                try {
                    Thread.sleep(UPDATE_INTERVAL);
                    runOnUiThread(new Runnable() {   // for modifying view
                        @Override
                        public void run() {
                            getBusInfo();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
