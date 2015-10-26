package dat255.chalmers.stormystreet.model;

import java.util.List;

import dat255.chalmers.stormystreet.model.bus.IBusTrip;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-23
 * Interface representing statistics for a user
 */

public interface IStatistics{
    IScore getTotalScore();
    long getWeeklyAverageScore();
    long getTimeSpentOnBus();
    List<IBusTrip> getAllBusTrips();
    void addBusTrip(IBusTrip busTrip);
    void addBusTrip(List<IBusTrip> busTrips);
}
