package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-23
 * Interface representing a journey
 */
public interface IJourneyInfo {
    String getBusName();
    String getDestination();
    IJourneyInfo clone();
}
