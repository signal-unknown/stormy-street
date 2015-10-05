package dat255.chalmers.stormystreet.model.bus;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-29
 */
public class BusTrip implements IBusTrip {
    private long startTime, endTime, distance;

    public BusTrip(long startTime, long endTime, long distance){
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public long getDistance() {
        return this.distance;
    }

    public String toString(){
        return this.startTime + " " + this.endTime + " " + this.distance;
    }
}
