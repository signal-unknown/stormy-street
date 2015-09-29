package dat255.chalmers.stormystreet.controller;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import dat255.chalmers.stormystreet.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private final int MY_PERMISSION_LOCATION = 1337;

    private static final String KEY_CURRENT_FRAGMENT_TAG = "home_activity_current_fragment";
    private static final String TAG_HOME_SCREEN = "fragment_home_screen";
    private static final String TAG_PROFILE_SCREEN = "fragment_profile_screen";
    private static final String TAG_MAP_SCREEN = "fragment_map_screen";
    private static final String TAG_SAVE_DATA ="save_data_screen";               // ändrat


    private String currentFragmentTag = TAG_HOME_SCREEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState != null) {
            currentFragmentTag = savedInstanceState.getString(KEY_CURRENT_FRAGMENT_TAG);
        }

        setupFragments();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        setupNavigationDrawer();
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            checkPermissions();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            // Open the navigation drawer
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        drawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.menu_drawer_home:
                switchFragment(TAG_HOME_SCREEN, false);
                break;
            case R.id.menu_drawer_profile:
                switchFragment(TAG_PROFILE_SCREEN, false);
                navigationView.getMenu().findItem(R.id.menu_drawer_home).setChecked(true);
                menuItem.setChecked(false);
                break;
            case R.id.menu_drawer_map:
                switchFragment(TAG_MAP_SCREEN, false);
                navigationView.getMenu().findItem(R.id.menu_drawer_home).setChecked(true);
                menuItem.setChecked(false);
                break;
            case R.id.menu_drawer_save:
                switchFragment(TAG_SAVE_DATA, false);
                navigationView.getMenu().findItem(R.id.menu_drawer_home).setChecked(true);
                menuItem.setChecked(false);
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CURRENT_FRAGMENT_TAG, currentFragmentTag);
    }

    private void switchFragment(final String fragmentTag, final boolean addToBackStack) {
        fragmentManager = getSupportFragmentManager();

        currentFragmentTag = fragmentTag;
        setupFragments();

    }

    private void setupFragments() {

        fragmentManager = getSupportFragmentManager();

        // get fragment
        Fragment fragment = fragmentManager.findFragmentByTag(currentFragmentTag);
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragment == null) {
            switch (currentFragmentTag) {
                case TAG_HOME_SCREEN:
                    fragment = new HomeFragment();
                    break;
                case TAG_PROFILE_SCREEN:
                    Intent profile = new Intent(this, ProfileActivity.class);
                    startActivity(profile);
                    return;
                case TAG_MAP_SCREEN:
                    Intent i = new Intent(this, MapsActivity.class);
                    startActivity(i);
                    return;
                case TAG_SAVE_DATA:
                    Intent s = new Intent(this, DatabaseActivity.class);
                    startActivity(s);
                    return;
                default:
                    fragment = new HomeFragment();
                    break;
            }
        }

        transaction.replace(R.id.fragment_container, fragment, currentFragmentTag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setupNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.navigtaion_drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.menu_drawer_home).setChecked(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void checkPermissions(){
        if(checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_LOCATION);

        }
    }

    public void onRequestPermissionResult(int requestCode,
                                          String permissions[], int[] grantResults){
        switch(requestCode){
            case MY_PERMISSION_LOCATION:{
                //close application if permission isnt granted
                if(!(grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    //android.os.Process.killProcess(android.os.Process.myPid());
                }
                return;
            }
        }
    }
}
