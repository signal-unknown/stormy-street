package dat255.chalmers.stormystreet;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Maxim Goretskyy
 * Created by maxim on 15-09-24.
 */
public class Constants {
    public static final String ACTION_GET_MAC = "ACTION_GET_MAC";
    public static final String ACTION_WIFI_CHANGED = "ACTION_WIFI_CHANGED";
    public static final String EXTRA_BUS_INFO_BUS_ID = "EXTRA_BUS_INFO_BUS_ID";

    public static final int BUS_MAC_INDEX = 0;
    public static final int BUS_REG_NUMBER_INDEX = 1;

    private static final HashMap<Integer, String[]> joinedVinMap;
    private static final Map<String, LatLng> busStops;//Contains all bus stops for the ElectriCity bus
    static{

        // Maps bus VIN numbers to their MAC address and reg numbers
        joinedVinMap = new HashMap<>();
        joinedVinMap.put(100020, new String[]{"04:f0:21:10:0a:07", "EPO 131"});
        joinedVinMap.put(100021, new String[]{"04:f0:21:10:09:df", "EPO 136"});
        joinedVinMap.put(100022, new String[]{"04:f0:21:10:09:e8", "EPO 143"});
        joinedVinMap.put(171327, new String[]{"04:f0:21:10:09:53", "EOG 622"});
        joinedVinMap.put(171328, new String[]{"04:f0:21:10:09:b9", "EOG 627"});
        joinedVinMap.put(171330, new String[]{"04:f0:21:10:09:b7", "EOG 634"});
        joinedVinMap.put(171234, new String[]{"04:f0:21:10:09:e7", "EOG 606"});
        joinedVinMap.put(171235, new String[]{"04:f0:21:10:09:5b", "EOG 616"});
        joinedVinMap.put(171164, new String[]{"04:f0:21:10:09:b8", "EOG 604"});
        joinedVinMap.put(171329, new String[]{null, "EOG 631"});

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
        busStops.put("Chalmers tvärgata", new LatLng(57.689735, 11.980320));
        busStops.put("Sven Hultins plats", new LatLng(57.685809, 11.977190));
        busStops.put("Chalmersplatsen", new LatLng(57.689315, 11.973429));
        busStops.put("Kapellplatsen", new LatLng(57.693680, 11.973703));
    }

    /**
     * Get the reg number that belong to the bus with the specified VIN number
     *
     * @param vin The VIN number of the bus
     * @return The reg number for the bus
     */
    public static String vinToRegNr(Integer vin){
        return joinedVinMap.get(vin)[BUS_REG_NUMBER_INDEX];
    }

    /**
     * Get the MAC address that belong to the bus with the specified VIN number
     *
     * @param vin The VIN number of the bus
     * @return The MAC address for the bus
     */
    public static String vinToMAC(Integer vin) {
        return joinedVinMap.get(vin)[BUS_MAC_INDEX];
    }

    /**
     * Get a list of all MAC addresses known
     */
    public static List<String> getAllMACS() {
        List<String> result = new ArrayList<>();
        for (String[] stringArray : joinedVinMap.values()) {
            result.add(stringArray[BUS_MAC_INDEX]);
        }
        return result;
    }

    public static Map<Integer, String[]> getVinToMACandRegNrMap() {
        return joinedVinMap;
    }

    /**
     * Returns a map containing names mapped to the positions of busstops of bus 55 in Gothenburg
     */
    public static Map<String, LatLng> getBusStopMap(){
        return busStops;
    }
}
