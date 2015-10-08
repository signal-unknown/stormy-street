package dat255.chalmers.stormystreet.utilities;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dat255.chalmers.stormystreet.BusResource;
import dat255.chalmers.stormystreet.model.bus.BusModel;
import dat255.chalmers.stormystreet.model.bus.IBus;

/**@author Maxim Goretskyy
 * 
 * Created by maxim on 2015-10-06.
 */
public class APIParser {

    private APIParser(){

    }
    /*
        All possible bus resources should be defined in BusResource Enum.
        @return Latest updated value for a given bus, resource and timeframe.
     */
    public synchronized static String getBusResource(int busVin, long startTime, long endTime, BusResource busResource){
        String jsonAllData = APIProxy.getBusInfo(busVin, startTime, endTime);
        List<String> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonAllData);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String resource = object.getString("resourceSpec");
                switch(busResource){
                    case Total_Vehicle_Distance_Value:
                        if(resource.equals("Total_Vehicle_Distance_Value")){
                            result.add(object.getString("value"));
                        }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size() < 1){
            throw new IllegalArgumentException("Non existing resource");
        }
        Log.d("Apiproxy", "Last updated value " + result.get(result.size() - 1));
        return result.get(result.size()-1);//get the last element, last updated value in the resource
    }
    /*
        Get latest info about the bus and return the bus.
     */
    public synchronized static IBus getBusInfo(int busVin){
        long startTime = System.currentTimeMillis() - 20000;
        long endTime = System.currentTimeMillis();
        String jsonData = APIProxy.getBusInfo(busVin, startTime, endTime);
        IBus result = new BusModel();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String resource = object.getString("resourceSpec");
                if (resource.equals("Total_Vehicle_Distance_Value")) {
                    int distance = Integer.parseInt(object.getString("value"));//Todo errorcheck
                    distance *=5; //compensate for API dividing it by 5
                    result.setTotalDistanceDriven(distance);//will automatically be latest value from API
                }
            }
        }catch (JSONException ex){

        }
        return result;
    }

    public synchronized static Map<String, TimedAndAngledPosition> getGPSMap(String rawGPSData){
        Map<String,TimedAndAngledPosition> map = new HashMap<String,TimedAndAngledPosition>();
        try {
            JSONArray jsonArray = new JSONArray(rawGPSData);
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
                    if(map.containsKey(object.getString("gatewayId"))){
                        if(map.get(object.getString("gatewayId")).isOlder(timestamp)){
                            TimedAndAngledPosition timedAndAngledPosition = new TimedAndAngledPosition(position, object.getLong("timestamp"), angle);
                            map.put(object.getString("gatewayId"), timedAndAngledPosition);
                        }
                    }else{
                        TimedAndAngledPosition timedAndAngledPosition = new TimedAndAngledPosition(position, object.getLong("timestamp"), angle);
                        map.put(object.getString("gatewayId"), timedAndAngledPosition);
                    }
                }
            }
        } catch (JSONException e) {
            //TODO Fucking handle it
            e.printStackTrace();
        }
        return map;
    }
}
