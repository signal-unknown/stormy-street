package dat255.chalmers.stormystreet.model.bus;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-23
 * Interface representing a bustrip
 */

public interface IBusTrip {
    long getStartTime();
    long getEndTime();
    long getDistance();
}
