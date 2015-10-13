package dat255.chalmers.stormystreet.controller;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dat255.chalmers.stormystreet.Constants;
import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.model.bus.BusNotFoundException;
import dat255.chalmers.stormystreet.model.bus.IBus;
import dat255.chalmers.stormystreet.services.BusInfoUpdater;
import dat255.chalmers.stormystreet.view.StatCardData;

/**
 * @author David Fogelberg, Alexander Håkansson
 */
public class BusInfoActivity extends AppCompatActivity implements BusInfoUpdater.IBusInfoListener {

    private static final int UPDATE_INTERVAL = 3000; // How often the bus info will update

    private Toolbar toolbar;

    private MainModel model;
    private boolean isVisible;

    private int busVin = -1;

    private RecyclerView cardRecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recycleViewManager;

    private Bitmap celsiusIcon;
    private Bitmap speedoIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        isVisible = true;

        celsiusIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_temperature_celsius_black_24dp);
        speedoIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_speedometer_black_24dp);

        setupToolbar();
        setupRecyclerView();
        getBusVIN();

        // The activity might be finishing if it can't get the bus with the VIN number provided
        if (!isFinishing()) {
            // Set the toolbar text to the bus reg number
            toolbar.setTitle(getString(R.string.bus) + " " + Constants.vinToRegNr(busVin));

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

    private void updateUI(final IBus bus) {
        if (bus != null) {
            model.addBus(bus);

            List<StatCardData> stats = new ArrayList<>();


            stats.add(new StatCardData(bus.getNextStop(), getString(R.string.towards) + " " + bus.getDestination(), null));
            if (bus.isStopPressed()) {
                stats.add(new StatCardData(getString(R.string.stopping),null, null));
            }
            stats.add(new StatCardData(Double.toString(bus.getDriverCabinTemperature()), null, celsiusIcon));

            stats.add(new StatCardData(bus.getAcceleratorPedalPosition() + "% ", null, speedoIcon));

            recyclerViewAdapter = new BusStatListAdapter(stats);
            cardRecyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    @Override
    public void busUpdated(final IBus bus) {
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
