package dat255.chalmers.stormystreet;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maxim Goretskyy
 * Created by maxim on 15-09-24.
 */
public class Constants {
    public static final String ACTION_GET_MAC = "ACTION_GET_MAC";
    public static final String ACTION_WIFI_CHANGED = "ACTION_WIFI_CHANGED";
    public static final String EXTRA_BUS_INFO_BUS_ID = "EXTRA_BUS_INFO_BUS_ID";

    public static final HashMap<Integer, String> busMacVin;
    private static final HashMap<Integer, String> busVinRegNr;
    private static final Map<String, LatLng> busStops;//Contains all bus stops for the ElectriCity bus
    static{
        busMacVin  = new HashMap<>();
        busMacVin.put(100020, "00:13:95:13:49:f5");
        busMacVin.put(100021, "00:13:95:13:4b:be");
        busMacVin.put(100022, "00:13:95:14:3b:f0");
        busMacVin.put(171164, "00:13:95:14:69:8a");
        busMacVin.put(171234, "00:13:95:13:49:f7");
        busMacVin.put(171235, "00:13:95:0f:92:a4");
        busMacVin.put(171327, "00:13:95:13:62:96");
        busMacVin.put(171328, "00:13:95:13:4b:bc");
        busMacVin.put(171329, "00:13:95:14:3b:f2");
        busMacVin.put(171330, "00:13:95:13:5f:20");

        //Updated BSSIDs below
        busMacVin.put(100020, "04:f0:21:10:0a:07");
        busMacVin.put(100021, "04:f0:21:10:09:df");
        busMacVin.put(100022, "04:f0:21:10:09:e8");
        busMacVin.put(171164, "04:f0:21:10:09:b8");
        busMacVin.put(171235, "04:f0:21:10:09:5b");
        busMacVin.put(171327, "04:f0:21:10:09:53");
        busMacVin.put(171328, "04:f0:21:10:09:b9");
        busMacVin.put(171330, "04:f0:21:10:09:b7");

        //Initialize map with bus vin numbers and registration numbers
        busVinRegNr = new HashMap<Integer, String>();
        busVinRegNr.put(100020,"EPO 131");
        busVinRegNr.put(100021,"EPO 136");
        busVinRegNr.put(100022,"EPO 143");
        busVinRegNr.put(171164,"EOG 604");
        busVinRegNr.put(171234,"EOG 606");
        busVinRegNr.put(171235,"EOG 616");
        busVinRegNr.put(171327,"EOG 622");
        busVinRegNr.put(171328,"EOG 627");
        busVinRegNr.put(171329,"EOG 631");
        busVinRegNr.put(171330,"EOG 634");

        //Initialize map with bus stops names and positions
        busStops = new HashMap<String, LatLng>();
        busStops.put("Lindholmen", new LatLng(57.708099, 11.93801));
        busStops.put("Teknikgatan", new LatLng(57.706913, 11.937230));
        busStops.put("Lindholmsplatsen", new LatLng(57.707019, 11.938480));
        busStops.put("Regnbågsgatan", new LatLng(57.710770, 11.942782));
        busStops.put("Pumpgatan", new LatLng(57.712745, 11.946183));
        busStops.put("Frihamnsporten", new LatLng(57.718182, 11.959530));
        busStops.put("Lilla bommen", new LatLng(57.709145, 11.966777));
        busStops.put("Brunnsparken", new LatLng(57.707204, 11.967815));
        busStops.put("Kungsportsplatsen", new LatLng(57.704005, 11.969682));
        busStops.put("Valand", new LatLng(57.700223, 11.975162));
        busStops.put("Götaplatsen", new LatLng(57.697597, 11.979000));
        busStops.put("Ålandsgatan", new LatLng(57.692238, 11.977710));
        busStops.put("Chalmers tvärgatan", new LatLng(57.689735, 11.980320));
        busStops.put("Sven Hultins plats", new LatLng(57.685809, 11.977190));
        busStops.put("Chalmersplatsen", new LatLng(57.689315, 11.973429));
        busStops.put("Kapellplatsen", new LatLng(57.693680, 11.973703));
    }

    /**
     * Converts a bus vin to a Reg nr, returns null if the vin is invalid
     */
    public static String vinToRegNr(Integer vin){
        String regNr = busVinRegNr.get(vin);
        return regNr;
    }

    public static Map<String, LatLng> getBusStopMap(){
        return busStops;
    }
}
