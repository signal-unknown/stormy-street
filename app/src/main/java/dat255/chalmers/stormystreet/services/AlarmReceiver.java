package dat255.chalmers.stormystreet.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import dat255.chalmers.stormystreet.model.MainModel;

/**
 * Service that sends the user's score to facebook
 * @author Alexander Karlsson
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static MainModel model;

    /**
     * Updates the users current score to Facebook when a broadcast is received
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("FacebookSUS", "Received alarm");
        if(model != null) {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            GraphRequest request = null;
            final long score = model.getTotalScore().getValue();
            //Check if the user is logged in to Facebook and update the score
            if (accessToken != null && !accessToken.isExpired()) {
                try {
                    request = GraphRequest.newPostRequest(
                            accessToken,
                            "/me/scores",
                            new JSONObject("{\"score\":\"" + score + "\"}"),
                            new GraphRequest.Callback() {
                                @Override
                                public void onCompleted(GraphResponse response) {
                                    Log.d("FacebookSUS", "Updated facebook with the score " + score);
                                }
                            });
                } catch (JSONException e) {
                    //Will never occur
                    e.printStackTrace();
                }
                request.executeAsync();
            }
        }
    }

    /**
     * Sets the model object from which the score will be read
     */
    public static void setModel(MainModel model){
        AlarmReceiver.model = model;
    }
}
