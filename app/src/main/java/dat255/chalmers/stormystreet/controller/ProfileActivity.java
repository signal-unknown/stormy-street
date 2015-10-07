package dat255.chalmers.stormystreet.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.utilities.CO2Util;

public class ProfileActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView profileImageView;

    private LoginButton facebookLoginButton;

    private TextView textPoints, textTime, textCO2;

    private CallbackManager facebookCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        profileImageView = (ImageView) findViewById(R.id.profileImage);
        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);

        textPoints = (TextView) findViewById(R.id.profile_stats_points_value);
        textTime = (TextView) findViewById(R.id.profile_stats_time_value);
        textCO2 = (TextView) findViewById(R.id.profile_stats_co2_value);

        setupToolbar();
        setupDefaultProfile();
        setupFacebook();
        setupStats();
    }

    @Override
    public void onPause(){
        super.onPause();
        ((GlobalState)getApplication()).saveModel();
    }

    private void setupFacebook() {
        int buttonPadding = getResources().getDimensionPixelSize(R.dimen.facebook_button_padding);
        facebookLoginButton.setPadding(buttonPadding, buttonPadding, buttonPadding, buttonPadding);

        facebookCallbackManager = CallbackManager.Factory.create();
        facebookLoginButton.registerCallback(facebookCallbackManager, this);

        AccessToken facebookToken = AccessToken.getCurrentAccessToken();
        if (facebookToken == null || facebookToken.isExpired()) {
            facebookLoginButton.setVisibility(View.VISIBLE);
        } else {
            facebookLoginButton.setVisibility(View.GONE);
            collapsingToolbarLayout.setTitle(Profile.getCurrentProfile().getName());
        }
    }

    private void setupStats() {
        MainModel model = ((GlobalState)getApplication()).getModel();
        textPoints.setText(model.getTotalScore().toString());
        textTime.setText(DateUtils.formatElapsedTime(model.getTimeSpentOnBus()));
        textCO2.setText(Long.toString(CO2Util.getGramsSavedPerKm(model.getTotalScore().getValue(), CO2Util.CO2EmissionRegion.EU)));
    }

    private void setupDefaultProfile() {
        Bitmap profileImage = BitmapFactory.decodeResource(getResources(),
                R.drawable.drawer_header);

        profileImageView.setImageBitmap(profileImage);
        collapsingToolbarLayout.setTitle("");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        // Hides facebook button when logged in

        // TODO: Update UI with facebook data

        facebookLoginButton.setVisibility(View.GONE);
        collapsingToolbarLayout.setTitle(Profile.getCurrentProfile().getName());
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException e) {

    }
}
