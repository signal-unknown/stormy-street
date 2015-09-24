package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-23
 * Interface representing a gps position
 */
public interface IGpsCoord {
    double getLat();
    double getLong();
    double getSpeed();
    int getDirection();
}