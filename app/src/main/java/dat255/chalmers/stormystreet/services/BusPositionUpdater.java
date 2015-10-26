package dat255.chalmers.stormystreet.services;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import dat255.chalmers.stormystreet.APIConstants;
import dat255.chalmers.stormystreet.controller.BusPositionListener;
import dat255.chalmers.stormystreet.model.GpsCoord;
import dat255.chalmers.stormystreet.utilities.APIParser;
import dat255.chalmers.stormystreet.utilities.APIProxy;

/**
 * Task for fetching and parsing GPS data from ElectriCitys API
 */
public class BusPositionUpdater extends AsyncTask<Void,Void,Map<GpsCoord, String>>{
    private BusPositionListener bpl;

    public BusPositionUpdater(BusPositionListener bpl){
        this.bpl = bpl;
    }

    /**
     * Will fetch the positions of all active ElectriCity busses and create a map of their most
     * recent positions and the VIN number associated with the bus
     * @return A map with positions and VIN numbers of busses
     */
    @Override
    protected Map<GpsCoord, String> doInBackground(Void... params) {
        //Fetch data
        String rawGPSData = APIProxy.getRawGpsData();

        //Parse data
        Map<String, GpsCoord> tempMap = APIParser.getGPSMap(rawGPSData);

        //Create correct map
        Set<String> iDs = tempMap.keySet();
        Map<GpsCoord, String> map = new HashMap<GpsCoord,String>();
        for(String iD:iDs){
            //Reverse mapping
            map.put(tempMap.get(iD),iD);
        }
        return map;
    }

    /**
     * Update all listeners with the data that was fetched
     * @param map The map that will be sent to the listeners. The result of doInBackground();
     */
    @Override
    protected void onPostExecute(Map<GpsCoord, String> map){
        bpl.updatePositions(map);
    }
}
