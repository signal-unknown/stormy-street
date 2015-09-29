package dat255.chalmers.stormystreet.utilities;

import com.google.android.gms.maps.model.LatLng;

/**
 * Class for handling GPS positions with timestamps
 */
public class TimedPosition {

    private final LatLng position;
    private final long timestamp;

    public TimedPosition(LatLng position, long timestamp){
        this.position = position;
        this.timestamp = timestamp;
    }

    public LatLng getPosition() {
        return position;
    }

    public long getTimestamp() {
        return timestamp;
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
