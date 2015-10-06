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

import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import dat255.chalmers.stormystreet.Constants;
import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.services.BusPositionUpdater;
import dat255.chalmers.stormystreet.utilities.TimedAndAngledPosition;

public class MapsActivity extends AppCompatActivity implements BusPositionListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Toolbar toolbar;
    private boolean isVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        isVisible = true;

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
        isVisible = true;
    }

    @Override
    protected void onPause(){
        super.onPause();
        isVisible = false;
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
    }

    @Override
    public void updatePositions(Map<TimedAndAngledPosition, String> positions) {
        if(mMap!=null){
            //Clear map of old markers
            mMap.clear();
            //Add a marker for each bus
            Set<TimedAndAngledPosition> positionSet = positions.keySet();
            for(TimedAndAngledPosition position:positionSet){
                MarkerOptions options = new MarkerOptions();
                options.position(position.getPosition());
                options.flat(true);
                options.title(positions.get(position));
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.navigation));
                options.anchor(0.5f,0.5f);
                options.rotation((float)position.getAngle());
                mMap.addMarker(options);
            }
        }
        //Get new positions after 500 millisconds if the activity is visible onscreen
        if(isVisible) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    new BusPositionUpdater(MapsActivity.this).execute();
                }
            }, 500);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.hideInfoWindow();
        Intent busInfo = new Intent(this, BusInfoActivity.class);
        busInfo.putExtra(Constants.EXTRA_BUS_INFO_BUS_ID, marker.getTitle());
        startActivity(busInfo);
        return false;
    }
}
