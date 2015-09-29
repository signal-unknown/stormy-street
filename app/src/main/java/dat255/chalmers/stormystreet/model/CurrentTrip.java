package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-28
 */
public class CurrentTrip {
    private long timestamp;

    public CurrentTrip(long timestamp){
        this.timestamp = timestamp;
    }

    public long getTimestamp(){
        return this.timestamp;
    }
}
