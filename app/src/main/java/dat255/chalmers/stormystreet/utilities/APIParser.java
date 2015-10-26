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
import dat255.chalmers.stormystreet.model.GpsCoord;
import dat255.chalmers.stormystreet.model.IGpsCoord;
import dat255.chalmers.stormystreet.model.bus.BusModel;
import dat255.chalmers.stormystreet.model.bus.IBus;
import dat255.chalmers.stormystreet.model.bus.JourneyInfo;

/**@author Maxim Goretskyy
 * 
 * Created by maxim on 2015-10-06.
 */
public class APIParser {

    private static final String API_VALUE_IDENTIFIER = "value";
    private static final int BUS_INFO_UPDATE_INTERVAL = 180000;

    private APIParser(){

    }
    /**
     *  All possible bus resources should be defined in BusResource Enum.
     *  @return Latest updated value for a given bus, resource and timeframe.
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
    /**
     *  Get latest info about the bus and return the bus.
     */
    public synchronized static IBus getBusInfo(int busVin){
        long startTime = System.currentTimeMillis() - BUS_INFO_UPDATE_INTERVAL;
        long endTime = System.currentTimeMillis();
        String jsonData = APIProxy.getBusInfo(busVin, startTime, endTime);
        IBus result = new BusModel();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);

            IGpsCoord joinedCoord = new GpsCoord(0, 0, 0, 0);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String resource = object.getString("resourceSpec");
                if (resource.equals("Total_Vehicle_Distance_Value")) {
                    int distance = Integer.parseInt(object.getString(API_VALUE_IDENTIFIER));
                    distance *= 5; //compensate for API dividing it by 5
                    result.setTotalDistanceDriven(distance);//will automatically be latest value from API
                } else if (resource.equals("Bus_Stop_Name_Value")) {
                    result.setNextStop(object.getString(API_VALUE_IDENTIFIER));
                } else if (resource.equals(("Open_Door_Value"))) {
                    result.setIsDoorOpen(object.getBoolean(API_VALUE_IDENTIFIER));
                } else if (resource.equals("Position_Of_Doors_Value")) {
                    result.setDoorsPosition(object.getInt(API_VALUE_IDENTIFIER));
                } else if (resource.equals("Pram_Request_Value")) {
                    result.setPramRequest(object.getInt(API_VALUE_IDENTIFIER));
                } else if (resource.equals("Ramp_Wheel_Chair_Lift_Value")) {
                    result.setRampWheelChairLift(object.getInt(API_VALUE_IDENTIFIER));
                } else if (resource.equals("Authenticated_Users_Value")) {
                    result.setAuthenticatedUsers(object.getInt(API_VALUE_IDENTIFIER));
                } else if (resource.equals("Cooling_Air_Conditioning_Value")) {
                    result.setCoolingAirConditioning(object.getInt(API_VALUE_IDENTIFIER));
                } else if (resource.equals("Driver_Cabin_Temperature_Value")) {
                    result.setDriverCabinTemperature(object.getDouble(API_VALUE_IDENTIFIER));
                } else if (resource.equals("Ambient_Temperature_Value")) {
                    result.setAmbientTemperature(object.getDouble(API_VALUE_IDENTIFIER));
                } else if (resource.equals("Stop_Request_Value")) {
                    result.setStopRequest(object.getInt(API_VALUE_IDENTIFIER));
                } else if (resource.equals("Stop_Pressed_Value")) {
                    result.setIsStopPressed(object.getBoolean(API_VALUE_IDENTIFIER));
                }else if(resource.equals("Turn_signals_Value")) {
                    result.setTurnSignals(object.getInt(API_VALUE_IDENTIFIER));
                }else if(resource.equals("Accelerator_Pedal_Position_Value")) {
                    result.setAcceleratorPedalPosition(object.getInt(API_VALUE_IDENTIFIER));
                }else if(resource.equals("Fms_Sw_Version_Supported_Value")) {
                    result.setFmsVersionSupported(object.getInt(API_VALUE_IDENTIFIER));
                }else if(resource.equals("Destination_Value")) {
                    result.setDestination(object.getString(API_VALUE_IDENTIFIER));
                }else if(resource.equals("Mobile_Network_Cell_Info_Value")) {
                    result.setMobileNetworkCellInfo(object.getString(API_VALUE_IDENTIFIER));
                }else if(resource.equals("Mobile_Network_Signal_Strength_Value")) {
                    result.setMobileNetworkSignalStrength(object.getString(API_VALUE_IDENTIFIER));
                }else if(resource.equals("Total_Online_Users_Value")) {
                    result.setOnlineUsers(object.getInt(API_VALUE_IDENTIFIER));
                }else if(resource.equals("Rssi_Value")) {
                    result.setWlanRsslValue(object.getInt(API_VALUE_IDENTIFIER));
                }else if(resource.equals("Rssi2_Value")){
                    result.setWlanRssl2Value(object.getInt(API_VALUE_IDENTIFIER));
                }else if(resource.equals("Cell_Id_Value")){
                    result.setWlanCellIdValue(Integer.parseInt(object.getString(API_VALUE_IDENTIFIER), 16));
                }else if(resource.equals("Cell_Id2_Value")) {
                    result.setWlanRssl2Value(Integer.parseInt(object.getString(API_VALUE_IDENTIFIER), 16));
                } else if (resource.equals("Speed2_Value")) {
                    joinedCoord = new GpsCoord(joinedCoord.getLat(), joinedCoord.getLong(), object.getDouble(API_VALUE_IDENTIFIER) * 3.6, joinedCoord.getDirection());
                    result.setGPSPosition(joinedCoord);
                } else if (resource.equals("Latitude2_Value")) {
                    joinedCoord = new GpsCoord(object.getDouble(API_VALUE_IDENTIFIER), joinedCoord.getLong(), joinedCoord.getSpeed(), joinedCoord.getDirection());
                    result.setGPSPosition(joinedCoord);
                } else if (resource.equals("Longitude2_Value")) {
                    joinedCoord = new GpsCoord(joinedCoord.getLat(), object.getDouble(API_VALUE_IDENTIFIER), joinedCoord.getSpeed(), joinedCoord.getDirection());
                    result.setGPSPosition(joinedCoord);
                } else if (resource.equals("Course2_Value")) {
                    joinedCoord = new GpsCoord(joinedCoord.getLat(), joinedCoord.getLong(), joinedCoord.getSpeed(), object.getDouble(API_VALUE_IDENTIFIER));
                    result.setGPSPosition(joinedCoord);
                }
            }
        }catch (JSONException ex){
            Log.e("API_PARSER", "Couldn't parse JSON.\n" + ex.getMessage());
        }
        return result;
    }

    /**
     * Parses a string containing a JSONArray with NMEA GPS data from the ElectriCity API. The
     * method assumes that the position is on the northern hemisphere and east
     * of Greenwich, which always is the case in Sweden, at least for the coming
     * few hundred million years or so, at which point the app should be updated
     * @param rawGPSData A string containing a JSONArray with GPS data
     * @return A map mapping bus VIN numbers with positions
     */
    public synchronized static Map<String, TimedAndAngledPosition> getGPSMap(String rawGPSData){
        Map<String,TimedAndAngledPosition> map = new HashMap<String,TimedAndAngledPosition>();
        try {
            //Create parsable array object
            JSONArray jsonArray = new JSONArray(rawGPSData);
            //Parse relevant data for each bus in the array
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                if(object.getString("resourceSpec").equals("RMC_Value") &&
                        object.getString("value").length() > 55 && //Ignore empty data
                        !object.getString("gatewayId").equals("Vin_Num_001")){//Ignore simulated bus

                    //Get the NMEA string
                    String nMEAString = object.getString("value");

                    //Parse direction of the bus
                    double angle = nMEAStringToAngle(nMEAString);

                    //Parse decimal longitude and latitude
                    LatLng position = nMEAStringToLatLng(nMEAString);

                    //Add position to the map, if no newer data from the same bus is already
                    //in the map
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

    /**
     * Parses a bus direction
     * @param nMEAGPSString A NMEA formatted GPS string
     * @return An angle in degrees deviating from north
     */
    private static double nMEAStringToAngle(String nMEAGPSString){
        //Parse direction of the bus
        String trackAngle = nMEAGPSString;
        trackAngle = trackAngle.substring(trackAngle.indexOf("E") + 2);
        trackAngle = trackAngle.substring(trackAngle.indexOf(",") + 1);
        trackAngle = trackAngle.substring(0, trackAngle.indexOf(","));
        double angle = Double.parseDouble(trackAngle);
        return angle;
    }

    /**
     * Parses a decimal latitude and longitude from a NMEA formatted GPS string
     * @param nMEAGPSString A NMEA formatted GPS String
     * @return A LatLng object with a parsed location
     */
    private static LatLng nMEAStringToLatLng(String nMEAGPSString){
        //Remove unnecessary data
        int beginIndex = nMEAGPSString.indexOf("A") + 2;
        int lastIndex = nMEAGPSString.lastIndexOf("E") - 1;
        nMEAGPSString = nMEAGPSString.substring(beginIndex,lastIndex);

        //Separate longitude and latitude values (NMEA)
        String nMEALatitude = nMEAGPSString.substring(0,nMEAGPSString.indexOf("N")-1);
        String nMEALongitude = nMEAGPSString.substring(nMEAGPSString.lastIndexOf("N")+2);

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

        return position;
    }
}
