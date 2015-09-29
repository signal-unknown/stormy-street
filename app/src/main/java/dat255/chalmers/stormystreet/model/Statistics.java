package dat255.chalmers.stormystreet.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dat255.chalmers.stormystreet.model.bus.BusTrip;
import dat255.chalmers.stormystreet.model.bus.IBusTrip;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-29
 */
public class Statistics implements IStatistics{
    List<IBusTrip> busTrips;
    private static final int DAY = 100*60*60*24;

    public Statistics(List<IBusTrip> busTrips){
        this.busTrips = new ArrayList<>(busTrips);
    }

    public Statistics(){
        this.busTrips = new ArrayList<>();
    }

    public IScore getTotalScore() {
        long totalScore = 0;
        for(IBusTrip busTrip:busTrips){
            totalScore += busTrip.getDistance();
        }
        return new Score(totalScore, "km");
    }

    public IScore getWeeklyAverageScore() {
        /*List<Long> weeklyScores = new ArrayList<>();
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        long firstDayOfWeek;
        for(IBusTrip busTrip:busTrips){
            firstDayOfWeek = cal.getTime().getTime() - cal.get(Calendar.DAY_OF_WEEK)*100*60*60*24;
            for(long i = 0; firstDayOfWeek - i*DAY*7 > busTrip.getStartTime(); i++){

            }
        }
        return null;
        */
        throw new NotYetImplementedException("This feature is not yet implemented");
    }

    public long getTimeSpentOnBus() {
        long totaltimeSpent = 0;
        for(IBusTrip busTrip:busTrips){
            totaltimeSpent += (busTrip.getEndTime()-busTrip.getStartTime());
        }
        return totaltimeSpent;
    }

    public List<IBusTrip> getAllBusTrips() {
        return new ArrayList<>(this.busTrips);
    }

    public void addBusTrip(IBusTrip busTrip) {
        this.busTrips.add(busTrip);
    }

    public void addBusTrip(List<IBusTrip> busTrips) {
        for(IBusTrip busTrip:busTrips){
            this.busTrips.add(busTrip);
        }
    }
}
