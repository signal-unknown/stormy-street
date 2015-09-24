package dat255.chalmers.stormystreet.model;

import java.util.List;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-23
 * Interface representing statistics for a user
 */

public interface IStatistics{
    IScore getTotalScore();
    IScore getWeeklyAverageScore();
    void setTotalScore(IScore score);
    long getTimeSpentOnBus();
    void setTimeSpentOnBus(long timeSpentOnBus);
    List<IBusTrip> getAllBusTrips();
    void addBusTrip(IBusTrip busTrip);
    void addBusTrip(List<IBusTrip> busTrips);
}
