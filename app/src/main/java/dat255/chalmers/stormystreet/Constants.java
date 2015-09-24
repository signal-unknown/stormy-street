package dat255.chalmers.stormystreet;

import java.util.HashMap;

/**
 * @author Maxim Goretskyy
 * Created by maxim on 15-09-24.
 */
public class Constants {
    public static final String ACTION_GET_MAC = "ACTION_GET_MAC";

    public static final HashMap<String, String> busMacVin;
    static{
        busMacVin  = new HashMap<>();
        busMacVin.put("Ericsson$100020", "00:13:95:13:49:f5");
        busMacVin.put("Ericsson$100021", "00:13:95:13:4b:be");
        busMacVin.put("Ericsson$100022", "00:13:95:14:3b:f0");
        busMacVin.put("Ericsson$171164", "00:13:95:14:69:8a");
        busMacVin.put("Ericsson$171234", "00:13:95:13:49:f7");
        busMacVin.put("Ericsson$171235", "00:13:95:0f:92:a4");
        busMacVin.put("Ericsson$171327", "00:13:95:13:62:96");
        busMacVin.put("Ericsson$171328", "00:13:95:13:4b:bc");
        busMacVin.put("Ericsson$171329", "00:13:95:14:3b:f2");
        busMacVin.put("Ericsson$171330", "00:13:95:13:5f:20");
    }

}
