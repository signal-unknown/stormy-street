package dat255.chalmers.stormystreet.utilities;
import android.util.Log;

import org.json.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
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
            Log.d("Apiproxy", "my url is " + requestURL);
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

    /**
     * Gets the gps data for all busses
     * @return The gps data for all busses
     */
    public static synchronized String getRawGpsData(){
        //Create proper URL

        //Base URL and parameter
        String url = "https://ece01.ericsson.net:4443/ecity";
        url += "?sensorSpec=Ericsson$GPS_NMEA";

        //Get current time and a timestamp 10 seconds before that
        long curTime = System.currentTimeMillis();
        long oldTime = curTime - 10000;

        //Add times to base URL
        url += "&t1=" + oldTime + "&t2=" + curTime;

        //Connect to server and fetch data
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

        return jsonGPSData.toString();
    }
}
