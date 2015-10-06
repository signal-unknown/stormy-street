package dat255.chalmers.stormystreet.controller;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.test.ActivityTestCase;
import android.util.Base64;
import android.util.Log;


import com.facebook.FacebookSdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.jar.Attributes;
import java.lang.*;

import java.security.*;
import android.content.pm.Signature;

import dat255.chalmers.stormystreet.R;


// keyHash : SzvEMa6TDQCI8YSo59L78seu2rQ=

/**
 * Created by David Fogelberg on 2015-10-06.
 */
public class FacebookActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_facebook);
        //initialize Facebook SDK before you can use it


        FacebookSdk.sdkInitialize(getApplicationContext());

        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
        //getKeyHash();
    }
    public void getKeyHash() { // generates a new KeyHash


        try {
            PackageInfo info = getPackageManager().getPackageInfo("dat255.chalmers.stormystreet", PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));

            }
        }
        catch(PackageManager.NameNotFoundException e) {

        }
        catch(NoSuchAlgorithmException e) {

        }

    }

}
