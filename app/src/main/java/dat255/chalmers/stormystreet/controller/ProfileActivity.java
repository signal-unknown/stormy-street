package dat255.chalmers.stormystreet.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dat255.chalmers.stormystreet.R;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        profileImageView = (ImageView) findViewById(R.id.profileImage);

        setupToolbar();

        Bitmap profileImage = BitmapFactory.decodeResource(getResources(),
                R.drawable.drawer_header);

        profileImageView.setImageBitmap(profileImage);

        collapsingToolbarLayout.setTitle("John Doe");

        TextView text = (TextView) findViewById(R.id.debug_text);
        for (int i = 0; i < 100; i++) {
            text.append("Hello I am a test. LOL \n");
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
}
