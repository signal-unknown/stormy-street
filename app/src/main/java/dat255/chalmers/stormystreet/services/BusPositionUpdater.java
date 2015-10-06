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
import dat255.chalmers.stormystreet.utilities.TimedAndAngledPosition;

/**
 * Task for fetching and parsing GPS data from ElectriCitys API
 */
public class BusPositionUpdater extends AsyncTask<Void,Void,Map<TimedAndAngledPosition, String>>{
    private BusPositionListener bpl;

    public BusPositionUpdater(BusPositionListener bpl){
        this.bpl = bpl;
    }

    @Override
    protected Map<TimedAndAngledPosition, String> doInBackground(Void... params) {
        //TODO refactor this method, as it is way too big

        //Create proper URL

        //Base URL and parameter
        String url = "https://ece01.ericsson.net:4443/ecity";
        url += "?sensorSpec=Ericsson$GPS_NMEA";

        //Get current time and a timestamp 10 seconds before that
        long curTime = System.currentTimeMillis();
        long oldTime = curTime - 10000;

        //Add times to base URL
        url += "&t1=" + oldTime + "&t2=" + curTime;

        //I have no idea what i'm doing here
        StringBuffer jsonGPSData = new StringBuffer("");
        HttpsURLConnection http = null;
        try {
            URL urlLat = new URL(url);
            http = (HttpsURLConnection) urlLat.openConnection();
            http.setRequestProperty("Authorization", "Basic " + APIConstants.ELECTRICITY_API_KEY);
            http.setRequestMethod("GET");
            http.connect();
            InputStream is = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = reader.readLine()) != null) {
                jsonGPSData.append(line);
            }

            //Let's pray and hope this works

        } catch (MalformedURLException e) {
            //TODO deal with it
            e.printStackTrace();
        } catch (IOException e) {
            //TODO deal with it
            e.printStackTrace();
        }

        if(http!=null){
            http.disconnect();
        }

        //Parse data

        Map<String,TimedAndAngledPosition> tempMap = new HashMap<String,TimedAndAngledPosition>();
        try {
            JSONArray jsonArray = new JSONArray(jsonGPSData.toString());
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);

                //The following assumes that the position is on the northern hemisphere and east
                //of Greenwich, which always is the case in Sweden, at least for the coming
                //few hundred million years or so, at which point the app should be updated
                if(object.getString("resourceSpec").equals("RMC_Value") &&
                        object.getString("value").length() > 55 && //Ignore empty data
                        !object.getString("gatewayId").equals("Vin_Num_001")){//Ignore simulated bus
                    String resource = object.getString("value");
                    //If you don't understand what is happening here, please educate yourself on
                    //marine GPS coordinates

                    //Parse direction of the bus
                    String trackAngle = resource;
                    trackAngle = trackAngle.substring(trackAngle.indexOf("E") + 2);
                    trackAngle = trackAngle.substring(trackAngle.indexOf(",") + 1);
                    trackAngle = trackAngle.substring(0, trackAngle.indexOf(","));
                    double angle = Double.parseDouble(trackAngle);

                    //Parse decimal longitude and latitude

                    //Remove unnecessary data
                    int beginIndex = resource.indexOf("A") + 2;
                    int lastIndex = resource.lastIndexOf("E") - 1;
                    resource = resource.substring(beginIndex,lastIndex);

                    //Separate longitude and latitude values (NMEA)
                    String nMEALatitude = resource.substring(0,resource.indexOf("N")-1);
                    String nMEALongitude = resource.substring(resource.lastIndexOf("N")+2);

                    //Separate DMS values for latitude
                    String latDegrees = nMEALatitude.substring(0,2);
                    String latMinutes = nMEALatitude.substring(2,9);

                    //Separate DMS values for longitude
                    String lonDegrees = nMEALongitude.substring(0,3);
                    String lonMinutes = nMEALongitude.substring(3,10);

                    //Convert from degrees, minutes and seconds to decimal longitude and latitude
                    double latitude = Double.parseDouble(latDegrees) + Double.parseDouble(latMinutes)/60.0D;
                    double longitude = Double.parseDouble(lonDegrees) + Double.parseDouble(lonMinutes)/60.0D;

                    LatLng position = new LatLng(latitude,longitude);

                    //Find latest position from each bus
                    long timestamp = object.getLong("timestamp");
                    if(tempMap.containsKey(object.getString("gatewayId"))){
                        if(tempMap.get(object.getString("gatewayId")).isOlder(timestamp)){
                            TimedAndAngledPosition timedAndAngledPosition = new TimedAndAngledPosition(position, object.getLong("timestamp"), angle);
                            tempMap.put(object.getString("gatewayId"), timedAndAngledPosition);
                        }
                    }else{
                        TimedAndAngledPosition timedAndAngledPosition = new TimedAndAngledPosition(position, object.getLong("timestamp"), angle);
                        tempMap.put(object.getString("gatewayId"), timedAndAngledPosition);
                    }
                }
            }
        } catch (JSONException e) {
            //TODO Fucking handle it
            e.printStackTrace();
        }

        //Create correct map
        Set<String> iDs = tempMap.keySet();
        Map<TimedAndAngledPosition, String> map = new HashMap<TimedAndAngledPosition,String>();
        for(String iD:iDs){
            // String regNr = Constants.vinToRegNr(Integer.parseInt(iD));

            // Put the VIN in the title so the marker can later be identified
            // The VIN will not be shown since the custom click listener should hide it
            map.put(tempMap.get(iD),iD);
        }
        return map;
    }

    @Override
    protected void onPostExecute(Map<TimedAndAngledPosition, String> map){
        //TODO handle positions
        bpl.updatePositions(map);
    }
}
