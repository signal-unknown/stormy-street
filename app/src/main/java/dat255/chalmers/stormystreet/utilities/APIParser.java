package dat255.chalmers.stormystreet.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
}
