package dat255.chalmers.stormystreet.model;

import java.util.List;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-23
 * Interface representing statistics for a user
 */

public interface IStatistics{
    IScore getTotalScore();
    long getTimeSpentOnBus();
    List<IBusTrip> getAllBusTrips();
    IScore getWeeklyAverageScore();
}
