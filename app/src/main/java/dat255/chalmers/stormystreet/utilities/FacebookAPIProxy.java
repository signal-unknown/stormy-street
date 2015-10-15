package dat255.chalmers.stormystreet.utilities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.model.FacebookFriend;
import dat255.chalmers.stormystreet.model.MainModel;

/**
    @author Alexander Karlsson && Maxim Goretskyy
 */
public class FacebookAPIProxy {
    private FacebookAPIProxy(){

    }
    private static final long APP_ID = 733566680083257L;

    public static void getScores(MainModel input){
        final List<FacebookFriend> result = new ArrayList<>();
        final MainModel model = input;
        AccessToken facebookToken = AccessToken.getCurrentAccessToken();
        if (facebookToken == null || facebookToken.isExpired()) {
            Log.d("FacebookAPIProxy", "shit facebooktoken is null");
        }
        GraphRequest request = GraphRequest.newGraphPathRequest(
                facebookToken,
                "/"+APP_ID+"/scores",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        if(response == null){
                            Log.d("FacebookAPIProxy", "no response lol");
                        }
                        JSONObject facebookJson = response.getJSONObject();
                        if(facebookJson !=null){
                            try {
                                JSONArray dataArr = facebookJson.getJSONArray("data");
                                for (int i = 0; i <dataArr.length(); i++) {
                                    String tempScore = dataArr.getJSONObject(i).getString("score");
                                    String user = dataArr.getJSONObject(i).getString("user");
                                    int score = Integer.parseInt(tempScore);
                                    JSONObject tempObj = new JSONObject(user);
                                    String userId = tempObj.getString("id");
                                    String name = tempObj.getString("name");
                                    result.add(new FacebookFriend(name, userId, score));
                                    Log.d("facebook", "result in proxy " + result.toString());
                                    //Log.d("FacebookAPIProxy", "score is " + score + " name is " + name );
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("FacebookAPIProxy", "Completed shit");
                        //klar here
                        model.setHighscoreList(result);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "score");
        request.setParameters(parameters);
        request.executeAsync();

        Log.d("facebook", "i got here yolo");
    }
}
