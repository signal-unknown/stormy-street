package dat255.chalmers.stormystreet;

import java.util.HashMap;

/**
 * @author Maxim Goretskyy
 * Created by maxim on 15-09-24.
 */
public class Constants {
    public static final String ACTION_GET_MAC = "ACTION_GET_MAC";
    public static final String ACTION_WIFI_CHANGED = "ACTION_WIFI_CHANGED";

    public static final HashMap<Integer, String> busMacVin;
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
        busMacVin.put(100021, "04:f0:21:10:09:df");
        busMacVin.put(100022, "04:f0:21:10:09:e8");
        busMacVin.put(171164, "04:f0:21:10:09:b8");
        busMacVin.put(171235, "04:f0:21:10:09:5b");
        busMacVin.put(171327, "04:f0:21:10:09:53");
        busMacVin.put(171328, "04:f0:21:10:09:b9");
        busMacVin.put(171330, "04:f0:21:10:09:b7");
        busMacVin.put(13379000, "0a:18:d6:29:4c:b0");

    }

}
