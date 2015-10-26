package dat255.chalmers.stormystreet.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.IGpsCoord;
import dat255.chalmers.stormystreet.model.bus.IBus;
import dat255.chalmers.stormystreet.view.StatCardData;

/**
 * A utility class for turning bus data into view cards that can be shown in a recycler view. This
 * is needed in multiple points in the app, which is why it's in a separate class.
 *
 * @author Alexander Håkansson
 */
public class BusStatsUtil {

    private static Bitmap celsiusIcon;
    private static Bitmap speedoIcon;
    private static Bitmap personIcon;

    private static final String NOT_IN_TRAFFIC = "ej i trafik";

    private BusStatsUtil() {
        // Private
    }

    public static synchronized List<StatCardData> getBusStatCards(Context context, IBus bus) {

        celsiusIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_temperature_celsius_black_24dp);
        speedoIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_speedometer_black_24dp);
        personIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_person_black_24dp);

        List<StatCardData> stats = new ArrayList<>();

        String towards;
        if (bus.getDestination() != null && bus.getDestination().toLowerCase().equals(NOT_IN_TRAFFIC)) {
            towards = bus.getDestination();
        } else {
            towards = context.getString(R.string.towards) + " " + bus.getDestination();
        }

        stats.add(new StatCardData(bus.getNextStop(), towards, null));
        if (bus.isStopPressed()) {
            stats.add(new StatCardData(context.getString(R.string.stopping).toUpperCase(),null, null));
        }
        stats.add(new StatCardData(Double.toString(bus.getDriverCabinTemperature()), null, celsiusIcon));

        stats.add(new StatCardData(bus.getAcceleratorPedalPosition() + "% ", null, speedoIcon));

        IGpsCoord busPos = bus.getGPSPosition();
        if (busPos != null) {
            stats.add(new StatCardData(Double.toString(Math.round(bus.getGPSPosition().getSpeed())), context.getString(R.string.kmh), null));
        }

        stats.add(new StatCardData(Integer.toString(bus.getOnlineUsers()), null, personIcon));

        return stats;
    }
}
