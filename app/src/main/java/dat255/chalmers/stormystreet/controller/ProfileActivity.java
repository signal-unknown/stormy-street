package dat255.chalmers.stormystreet.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import java.util.Arrays;

import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.utilities.CO2Util;

public class ProfileActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProfilePictureView profileImageView;
    private AppBarLayout appBarLayout;

    private View rootView;

    private LoginButton facebookLoginButton;

    private TextView textPoints, textTime, textCO2;

    private CallbackManager facebookCallbackManager;

    private static final String FACEBOOK_PUBLISH_ACTIONS = "publish_actions";
    private static final String FACEBOOK_USER_FRIENDS = "user_friends";
    private static final String FACEBOOK_USER_GAMES = "user_games_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        rootView = findViewById(R.id.profile_activity_root);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        //profileImageView = (ImageView) findViewById(R.id.profileImage);
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

        profileImageView = (ProfilePictureView) findViewById(R.id.profileImage);
        profileImageView.setCropped(true);

        final Bitmap defaultProfileImage = BitmapFactory.decodeResource(getResources(),
                R.drawable.drawer_header);
        profileImageView.post(new Runnable() {
            @Override
            public void run() {
                profileImageView.setDefaultProfilePicture(defaultProfileImage);
            }
        });

        facebookCallbackManager = CallbackManager.Factory.create();
        facebookLoginButton.registerCallback(facebookCallbackManager, this);
        facebookLoginButton.setReadPermissions(FACEBOOK_USER_FRIENDS, FACEBOOK_USER_GAMES);

        AccessToken facebookToken = AccessToken.getCurrentAccessToken();
        if (facebookToken != null && !facebookToken.isExpired()) {
            facebookLoginButton.setVisibility(View.GONE);
            Profile facebookProfile = Profile.getCurrentProfile();
            if (facebookProfile != null) {
                collapsingToolbarLayout.setTitle(Profile.getCurrentProfile().getName());
                profileImageView.setProfileId(Profile.getCurrentProfile().getId());
            } else {
                Log.e("PROFILE", "Facebbok profile is null");
            }

            if (!facebookToken.getPermissions().contains(FACEBOOK_PUBLISH_ACTIONS)) {
                LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList(FACEBOOK_PUBLISH_ACTIONS));
            }
        } else {
            facebookLoginButton.setVisibility(View.VISIBLE);
            profileImageView.setProfileId(null);
        }
    }

    private void setupStats() {
        MainModel model = ((GlobalState)getApplication()).getModel();
        textPoints.setText(model.getTotalScore().toString());
        textTime.setText(DateUtils.formatElapsedTime(model.getTimeSpentOnBus()/1000));
        textCO2.setText(Long.toString(CO2Util.getGramsSavedPerKm(model.getTotalPlusCurrentScore().getValue(), CO2Util.CO2EmissionRegion.EU)));
    }

    private void setupDefaultProfile() {
        Bitmap profileImage = BitmapFactory.decodeResource(getResources(),
                R.drawable.drawer_header);

        //profileImageView.setImageBitmap(profileImage);
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
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        appBarLayout.getLayoutParams().height = width;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        // Hides facebook button when logged in
        facebookLoginButton.setVisibility(View.GONE);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onCancel() {
        Snackbar.make(rootView, getString(R.string.error_facebook_cancel), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onError(FacebookException e) {
        Log.e("HIGHSCORE", e.getMessage());
        Snackbar.make(rootView, getString(R.string.error_facebook_onError), Snackbar.LENGTH_LONG).show();
    }
}
