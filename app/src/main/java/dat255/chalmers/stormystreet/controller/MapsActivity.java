package dat255.chalmers.stormystreet.controller;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import dat255.chalmers.stormystreet.Constants;
import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.GpsCoord;
import dat255.chalmers.stormystreet.services.BusPositionUpdater;

/**
 * This screen represents a map with all the buses connected to the ElectriCity API pointed out.
 * Their position will live update and show detailed information when pressed on.
 *
 * @author Alexander Karlsson
 */
public class MapsActivity extends AppCompatActivity implements BusPositionListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Toolbar toolbar;
    private boolean isVisible;
    private List<Marker> busMarkers;

    private static final int UPDATE_INTERVAL = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        isVisible = true;
        busMarkers = new ArrayList<Marker>();

        //Set up toolbar
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

        //Set up map and get bus positions
        setUpMapIfNeeded();
        new BusPositionUpdater(this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        new BusPositionUpdater(this).execute();
        isVisible = true;
    }

    @Override
    protected void onPause(){
        super.onPause();
        isVisible = false;
        ((GlobalState)getApplication()).saveModel();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * Sets up the map by zooming over central Gothenburg and shows the users current position
     * Should not be ran without proper nullcheck
     */
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
        //Zoom over central Gothenburg and zoom enough to show the entire ElectriCity bus line
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(57.708870,11.974560),12.5f));
        //Add markers for bus stops
        Map<String, LatLng> busStops = Constants.getBusStopMap();
        for(String stopName : busStops.keySet()){
            MarkerOptions options = new MarkerOptions();
            options.title(stopName);
            options.position(busStops.get(stopName));
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(options);
        }
    }

    /**
     * Updates the positions of the busses
     * @param positions A map containing positions mapped to a proper bus VIN number
     */
    @Override
    public void updatePositions(Map<GpsCoord, String> positions) {
        if(mMap != null){
            //Clear map of old bus markers
            for (Marker oldMarker : busMarkers) {
                oldMarker.remove();
            }

            //Add a marker for each bus
            Set<GpsCoord> positionSet = positions.keySet();
            for(GpsCoord position:positionSet){
                MarkerOptions options = new MarkerOptions();
                options.position(new LatLng(position.getLat(), position.getLong()));
                options.flat(true);
                options.title(positions.get(position));
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.navigation));
                options.anchor(0.5f,0.5f);//Rotate around center of icon
                options.rotation((float)position.getAngle());//Rotate depending on direction of the bus
                busMarkers.add(mMap.addMarker(options));
            }
        }
        //Get new positions after the specified interval if the activity is visible onscreen
        if(isVisible) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    new BusPositionUpdater(MapsActivity.this).execute();
                }
            }, UPDATE_INTERVAL);
        }
    }

    /**
     * Will take the user to a screen with info about a bus if a bus's marker is pressed onscreen
     * @param marker The marker that was pressed
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        //Check if the marker pressed was a bus or a bus stop
        if (busMarkers.contains(marker)) {
            // Open a new screen with detailed bus info
            Intent busInfo = new Intent(this, BusInfoActivity.class);
            busInfo.putExtra(Constants.EXTRA_BUS_INFO_BUS_ID, Integer.parseInt(marker.getTitle())); // Add the bus VIN so it can be identified in the next screen
            startActivity(busInfo);
            return true;
        } else {
            return false;
        }
    }
}
