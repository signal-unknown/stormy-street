package dat255.chalmers.stormystreet.model;

/**
 * Representing a gps coordinate with long, lat, speed and direction. Useful for storing coordinates
 * after parsing
 *
 * @author Kevin Hoogendijk
 * @since 2015-09-28
 */
public class GpsCoord implements IGpsCoord {
    private double latitude;
    private double longitude;
    private double speed;
    private double direction;
    private final long timestamp;

    public GpsCoord(double latitude, double longitude, double speed, double direction){
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.direction = direction;
        this.timestamp = System.currentTimeMillis();
    }

    public GpsCoord(double latitude, double longitude, double speed, double direction, long timestamp){
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.direction = direction;
        this.timestamp = timestamp;
    }

    public GpsCoord(double latitude, double longitude, double direction, long timestamp){
        this.latitude = latitude;
        this.longitude = longitude;
        this.direction = direction;
        this.timestamp = timestamp;
    }

    /**
     * Gets this objects latitude value
     * @return This objects latitude value
     */
    public double getLat() {
        return this.latitude;
    }

    /**
     * Gets this objects longitude value
     * @return This object longitude value
     */
    public double getLong() {
        return this.longitude;
    }

    /**
     * Returns the current speed of the object
     * @return The current speed of the object
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Returns the timestamp
     * @return The timestamp
     */
    public long getTimestamp(){
        return timestamp;
    }

    /**
     * Returns the direction of the object in degrees differing clockwise from north
     * @return The direction of the object
     */
    public double getAngle() {
        return this.direction;
    }

    /**
     * Checks if the current position is older than another timestamp
     * @param timestamp The time to compare
     * @return True if the object is older than the compared time
     */
    public boolean isOlder(long timestamp){
        return this.timestamp < timestamp;
    }

    /**
     * Returns a clone of the object
     * @return A clone of the object
     */
    public IGpsCoord clone() {
        return new GpsCoord(this.latitude, this.longitude, this.speed, this.direction, this.timestamp);
    }
}
