package dat255.chalmers.stormystreet.utilities;

import com.google.android.gms.maps.model.LatLng;

/**
 * Class for handling GPS positions with timestamps and angles
 */
public class TimedAndAngledPosition {

    private final LatLng position;
    private final long timestamp;
    private final double angle;

    public TimedAndAngledPosition(LatLng position, long timestamp, double angle){
        this.position = position;
        this.timestamp = timestamp;
        this.angle = angle;
    }

    public LatLng getPosition() {
        return position;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getAngle(){
        return angle;
    }

    /**
     * Checks if the current position is older than another timestamp
     * @param timestamp The time to compare
     * @return True if the object is older than the compared time
     */
    public boolean isOlder(long timestamp){
        return this.timestamp < timestamp;
    }


}
