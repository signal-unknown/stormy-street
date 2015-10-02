package dat255.chalmers.stormystreet.utilities;
import android.util.Log;

import org.json.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import dat255.chalmers.stormystreet.APIConstants;
import dat255.chalmers.stormystreet.BusResource;

/**
 * @author Maxim Goretskyy
 *
 *  This class works as a proxy/bridge between API and our application.
 */
public class APIProxy {

    private APIProxy(){
        //prevent from creating outside
    }
    /*
        Gets all resources given a busVin number and a timeframe in milliseconds.
        @returns String representation of response from server.
     */
    public synchronized static String getBusInfo(int busVIN, long startTime, long endTime){
        String baseURL = "https://ece01.ericsson.net:4443/ecity";
        String busConst = "?dgw=Ericsson$";
        String key = "Basic " + APIConstants.ELECTRICITY_API_KEY;
        StringBuffer response = new StringBuffer();
        baseURL += busConst + "" +busVIN;
        baseURL += "&t1="+startTime+"&t2="+endTime;
        URL requestURL = null;
        HttpsURLConnection con = null;
        int responseCode = 0;
        BufferedReader in = null;
        Log.d("Apiproxy", "Create vars");
        try {
            requestURL = new URL(baseURL);
            con = (HttpsURLConnection) requestURL.openConnection();
            Log.d("Apiproxy", "opened connection");
            con.setRequestMethod("GET");
            Log.d("Apiproxy", "get request set");
            con.setRequestProperty("Authorization", key);
            Log.d("Apiproxy", "authed with key");
            responseCode = con.getResponseCode();
            Log.d("Apiproxy", "Response code " + responseCode);
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Log.d("Apiproxy", "Created bufferreader");
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (ProtocolException e) {

        }catch(IOException ex){

        }finally {
            try{
                if(in != null){
                    in.close();
                    Log.d("Apiproxy", "closed bufferreeader");
                }
                if(con !=null){
                    con.disconnect();
                    Log.d("Apiproxy", "disconnected con");
                }
            }catch(IOException ex){
            }
        }
        return response.toString();
    }
    /*
        All possible bus resources should be defined in BusResource Enum.
        @return Latest updated value for a given bus, resource and timeframe.
     */
    public synchronized static String getBusResource(int busVin, long startTime, long endTime, BusResource busResource) throws IOException, JSONException{
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
        Log.d("Apiproxy", "Last updated value " + result.get(result.size()-1));
        return result.get(result.size()-1);//get the last element, last updated value in the resource
    }
}
