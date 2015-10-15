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
 * Service that updates the users score to facebook
 * @author Alexander Karlsson
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = null;
        final long score = 12;//model.getTotalScore().getValue();

        if(accessToken != null && !accessToken.isExpired()) {
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
                //Should never occur
                e.printStackTrace();
            }
            request.executeAsync();
        }
    }
}
