package dat255.chalmers.stormystreet.model;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.bus.BusTrip;
import dat255.chalmers.stormystreet.model.bus.IBus;
import dat255.chalmers.stormystreet.model.bus.IBusTrip;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-29
 */
public class Statistics implements IStatistics{
    private List<IBusTrip> busTrips;
    private static final int DAY = 100*60*60*24;

    public Statistics(List<IBusTrip> busTrips){
        this.busTrips = new ArrayList<>(busTrips);
    }

    public Statistics(){
        this.busTrips = new ArrayList<>();
    }

    /**
     * Sums the total score of all bustrips and returns it
     * @return total score
     */
    public IScore getTotalScore() {
        long totalScore = 0;
        for(IBusTrip busTrip:busTrips){
            totalScore += busTrip.getDistance();
        }
        return new Score(totalScore, "km");
    }

    /**
     * Not yet used in the app but more of a proof of concept
     * This function also need refactoring
     * It checks all of the bustrips of a user and calculates the amount of score the user collects
     * in average during a week
     * @return
     */
    public long getWeeklyAverageScore() {

        List<IBusTrip> trips = getAllBusTrips();
        Collections.sort(trips, new BusTripComparator());

        Calendar cal = Calendar.getInstance();

        long firstTimeStamp = trips.get(0).getStartTime();
        cal.setTimeInMillis(firstTimeStamp);
        int mill = cal.get(Calendar.MILLISECOND);
        int sec = cal.get(Calendar.SECOND) * 1000;
        int min = cal.get(Calendar.MINUTE) * 1000 * 60;
        int hour = cal.get(Calendar.HOUR_OF_DAY) * 1000 * 60 * 60;
        int day = (cal.get(Calendar.DAY_OF_WEEK) - 2) * 1000 * 60 * 60 * 24;
        long startOfWeek = firstTimeStamp - mill - sec - min - hour - day + 1;

        Map<Long, List<IBusTrip>> weeklyTrips = new HashMap<>();

        long tempWeekStart = startOfWeek;

        //Creates a list with the timestamp of the first day in each week starting from the first
        do {
            weeklyTrips.put(tempWeekStart, new ArrayList<IBusTrip>());
            tempWeekStart = tempWeekStart + 7 * 24 * 60 * 60 * 1000;
        } while (tempWeekStart < System.currentTimeMillis());

        //Places each bustrip in the correct week
        for (IBusTrip trip : getAllBusTrips()) {
            List<Long> weekStarts = new ArrayList<>(weeklyTrips.keySet());
            Collections.sort(weekStarts, new WeekStartSetComparator());

            for (long weekStart : weekStarts) {
                if (trip.getStartTime() >= weekStart) {
                    weeklyTrips.get(weekStart).add(trip);
                    break;
                }
            }
        }

        //Calculates the sujm of each week
        List<Long> weekSum = new ArrayList<>();
        for (List<IBusTrip> tripList : weeklyTrips.values()) {
            long sum = 0;
            for (IBusTrip trip : tripList) {
                sum += trip.getDistance();
            }
            weekSum.add(sum);
        }

        //Calculates the total sum of all weeks
        long totalSum = 0;
        for (long sum : weekSum) {
            totalSum += sum;
        }

        //Returns the average score
        return Math.round((double)totalSum / weekSum.size());
    }

    /**
     * Get the total time spent on a bus
     * @return total time spent
     */
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

    private class BusTripComparator implements Comparator<IBusTrip> {
        @Override
        public int compare(IBusTrip lhs, IBusTrip rhs) {
            return (int) Math.signum(lhs.getStartTime() - rhs.getStartTime());
        }
    }

    private class WeekStartSetComparator implements Comparator<Long> {
        @Override
        public int compare(Long lhs, Long rhs) {
            return (int) Math.signum(rhs - lhs);
        }
    }
}
