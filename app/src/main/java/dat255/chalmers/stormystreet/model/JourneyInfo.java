package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-28
 */
public class JourneyInfo implements IJourneyInfo {
    private String busName;
    private String destination;

    public JourneyInfo(String busName, String destination){
        this.busName = busName;
        this.destination = destination;
    }

    public String getBusName() {
        return this.busName;
    }

    public String getDestination() {
        return this.destination;
    }

    public IJourneyInfo clone() {
        return new JourneyInfo(this.busName, this.destination);
    }
}
