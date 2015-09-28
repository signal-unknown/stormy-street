package dat255.chalmers.stormystreet.utilities;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Task for fetching and parsing GPS data from ElectriCitys API
 */
public class BusPositionUpdater extends AsyncTask<Void,Void,Map<LatLng, String>>{

    @Override
    protected Map<LatLng, String> doInBackground(Void... params) {
        //TODO fetch positions, and map them to an identifier (probably registration nr)

        //Create proper URLs

        //Base URLs and parameters
        String uRLLatitude = "https://ece01.ericsson.net:4443/ecity";
        String uRLLongitude = uRLLatitude + "Ericsson$Longitude2_Value";
        uRLLatitude += "?resourceSpec=Ericsson$Latitude2_Value";

        //Get current time and a timestamp 15 seconds before that
        long curTime = System.currentTimeMillis();
        long oldTime = curTime - 15000;

        //Add times to base URLs
        uRLLongitude += "&t1=" + oldTime + "&t2=" + curTime;
        uRLLatitude += "&t1=" + oldTime + "&t2=" + curTime;

        try {
            URL urlLat = new URL(uRLLatitude);
            HttpURLConnection httpLat = (HttpURLConnection) urlLat.openConnection();
            httpLat.setRequestMethod("GET");
        } catch (MalformedURLException e) {
            //TODO deal with it
            e.printStackTrace();
        } catch (IOException e) {
            //TODO deal with it
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Map<LatLng, String> map){
        //TODO handle positions
    }
}
