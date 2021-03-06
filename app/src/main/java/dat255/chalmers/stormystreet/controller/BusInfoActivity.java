package dat255.chalmers.stormystreet.controller;


import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import dat255.chalmers.stormystreet.Constants;
import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.model.bus.BusNotFoundException;
import dat255.chalmers.stormystreet.model.bus.IBus;
import dat255.chalmers.stormystreet.services.BusInfoUpdater;
import dat255.chalmers.stormystreet.utilities.BusStatsUtil;

/**
 * This class if for representing information about bus collected from the ElectriCity API. The bus
 * to collect info from is identified by its VIN number (from the API) which must be sent in a bundle
 * as the activity intent is launched.
 *
 * @author David Fogelberg, Alexander Håkansson
 */
public class BusInfoActivity extends AppCompatActivity implements BusInfoUpdater.IBusInfoListener {

    private static final int UPDATE_INTERVAL = 1500; // How often the bus info will update from the API

    private Toolbar toolbar;

    private MainModel model;
    private boolean isVisible;

    private int busVin = -1;

    private RecyclerView cardRecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recycleViewManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        isVisible = true;

        // Setup view components
        setupToolbar();
        setupRecyclerView();

        getBusVIN();

        // The activity might be finishing if it can't get the bus with the VIN number provided
        if (!isFinishing()) {
            // Set the toolbar text to the bus reg number
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.bus) + " " + Constants.vinToRegNr(busVin));
            }

            // Starting thread for getting data from model
            new BusInfoUpdater(this).execute(busVin);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
        new BusInfoUpdater(this).execute(busVin);
    }

    @Override
    protected void onPause(){
        super.onPause();
        isVisible = false;
        ((GlobalState)getApplication()).saveModel();
    }

    private void getBusVIN() {
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
                    onBackPressed();
                }
            });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // Updates the UI with the bus info
    private void updateUI(final IBus bus) {
        if (bus != null) {
            model.addBus(bus);

            recyclerViewAdapter = new BusStatListAdapter(BusStatsUtil.getBusStatCards(this, bus));
            cardRecyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    @Override
    public void busUpdated(final IBus bus) {
        // Check if on UI thread
        if (Looper.myLooper() == Looper.getMainLooper()) {
            updateUI(bus);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateUI(bus);
                }
            });
        }

        // Get new info after the specified time interval has passed
        if(isVisible) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    new BusInfoUpdater(BusInfoActivity.this).execute(busVin);
                }
            }, UPDATE_INTERVAL);
        }
    }

    private void setupRecyclerView() {
        cardRecyclerView = (RecyclerView) findViewById(R.id.businfo_list);
        recycleViewManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cardRecyclerView.setLayoutManager(recycleViewManager);
    }
}
