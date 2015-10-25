package dat255.chalmers.stormystreet.model;

/**
 * Representing the current trip that you are on right now
 * This trip only requires a start timestamp and vin number
 * @author Kevin Hoogendijk
 * @since 2015-09-28
 */
public class CurrentTrip {
    private long timestamp;
    private long distance;
    private int currentVinNumber;

    public CurrentTrip(long timestamp, int currentVinNumber){
        this.timestamp = timestamp;
        this.currentVinNumber = currentVinNumber;
    }

    public int getCurrentVinNumber(){
        return this.currentVinNumber;
    }

    public long getTimestamp(){
        return this.timestamp;
    }

    public long getTime(){
        return this.timestamp > 0 ? System.currentTimeMillis() - this.timestamp : 0;
    }

    public void setDistance(long distance){
        this.distance = distance;
    }

    public long getDistance(){
        return this.distance;
    }
}
