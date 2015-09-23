package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-23
 * Interface representing a bus
 */

public interface IBus {
    int getAcceleratorPedalPosition();
    int getAmbientTemperature();
    boolean getIsAtStop();
    int getCoolingAirConditioning();
    int getDriverCabinTemperature();
    int getFmsVersionSupported(); //Not yet active in the API as of 2015-09-23
    IGpsCoord getGPSPosition();
    IJourneyInfo getJourneyInfo();
    String getMobileNetworkCellInfo(); //Not yet active in the API as of 2015-09-23
    String getMobileNetworkSignalStrength(); //Not yet active in the API as of 2015-09-23
    String getNextStop();
    boolean getIsOffroute();
    int getOnlineUsers();
    int getAuthenticatedUsers();
    boolean getIsDoorOpen();
    int getDoorsPosition();
    int getPramRequest();
    int getRampWheelChairLift(); //Not yet active in the API as of 2015-09-23
    boolean getIsStopPressed();
    int getStopRequest();
    long getTotalDistanceDriven();
    int getTurnSignals();
    int getWlanRsslValue();
    int getWlanRssl2Value();
    int getWlanCellIdValue();
    int getWlanCellId2Value();
}
