package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-28
 */
public class GpsCoord implements IGpsCoord {
    private double latitude;
    private double longitude;
    private double speed;
    private int direction;

    public GpsCoord(double latitude, double longitude, double speed, int direction){
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.direction = direction;
    }

    public double getLat() {
        return this.latitude;
    }

    public double getLong() {
        return this.longitude;
    }

    public double getSpeed() {
        return this.speed;
    }

    //The direction in degrees with north being 0
    public int getDirection() {
        return this.direction;
    }

    public IGpsCoord clone() {
        return new GpsCoord(this.latitude, this.longitude, this.speed, this.direction);
    }
}