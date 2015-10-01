package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-28
 */
public class CurrentTrip {
    private long timestamp;
    private long distance;

    public CurrentTrip(long timestamp, long distance){
        this.timestamp = timestamp;
        this.distance = distance;
    }

    public long getTimestamp(){
        return this.timestamp;
    }

    public long getTime(){
        return this.timestamp > 0 ? System.currentTimeMillis() - this.timestamp : 0;
    }

    public long getDistance(){
        return this.distance;
    }
}
