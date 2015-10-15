package dat255.chalmers.stormystreet;

import android.test.ActivityTestCase;

import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.model.bus.BusTrip;

/**
 * @author Alexander Håkansson
 */
public class WeeklyScoreText extends ActivityTestCase {
    public void testWeeklyAverageCorrect() {

        MainModel model = new MainModel();
        model.addBusTrip(new BusTrip(System.currentTimeMillis(), System.currentTimeMillis() + 5000, 50));
        model.addBusTrip(new BusTrip(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000, System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000 + 5000, 100));
        model.addBusTrip(new BusTrip(System.currentTimeMillis() - 14 * 24 * 60 * 60 * 1000, System.currentTimeMillis() - 14 * 24 * 60 * 60 * 1000 + 5000, 250));
        long expectedAverage = (50 + 100 + 250) / 3;

        assertEquals("Average should be " + expectedAverage, expectedAverage, model.getWeeklyAverageScore());
    }
}
