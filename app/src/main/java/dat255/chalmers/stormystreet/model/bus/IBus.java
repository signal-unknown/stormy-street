package dat255.chalmers.stormystreet.model.bus;

import dat255.chalmers.stormystreet.model.IGpsCoord;
import dat255.chalmers.stormystreet.model.IJourneyInfo;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-23
 * Interface representing a bus
 */

public interface IBus {
    int getDgwNumber();
    int getAcceleratorPedalPosition();
    int getAmbientTemperature();
    boolean isAtStop();
    int getCoolingAirConditioning();
    int getDriverCabinTemperature();
    int getFmsVersionSupported(); //Not yet active in the API as of 2015-09-23
    IGpsCoord getGPSPosition();
    String getDestination();
    String getMobileNetworkCellInfo(); //Not yet active in the API as of 2015-09-23
    String getMobileNetworkSignalStrength(); //Not yet active in the API as of 2015-09-23
    String getNextStop();
    boolean isOffroute();
    int getOnlineUsers();
    int getAuthenticatedUsers();
    boolean isDoorOpen();
    int getDoorsPosition();
    int getPramRequest();
    int getRampWheelChairLift(); //Not yet active in the API as of 2015-09-23
    boolean isStopPressed();
    int getStopRequest();
    long getTotalDistanceDriven();
    int getTurnSignals();
    int getWlanRsslValue();
    int getWlanRssl2Value();
    int getWlanCellIdValue();
    int getWlanCellId2Value();

    void setDgwNumber(int dgwNumber);
    void setAcceleratorPedalPosition(int acceleratorPedalPosition);
    void setAmbientTemperature(int ambientTemperature);
    void setIsAtStop(boolean isAtStop);
    void setCoolingAirConditioning(int coolingAirConditioning);
    void setDriverCabinTemperature(int driverCabinTemperature);
    void setFmsVersionSupported(int fmsVersionSupported); //Not yet active in the API as of 2015-09-23
    void setGPSPosition(IGpsCoord gpsPosition);
    void setDestination(String destination);
    void setMobileNetworkCellInfo(String mobileNetworkCellInfo); //Not yet active in the API as of 2015-09-23
    void setMobileNetworkSignalStrength(String mobileNetworkSignalStrength); //Not yet active in the API as of 2015-09-23
    void setNextStop(String nextStop);
    void setIsOffroute(boolean isOffroute);
    void setOnlineUsers(int onlineUsers);
    void setAuthenticatedUsers(int authenticatedUsers);
    void setIsDoorOpen(boolean isDoorOpen);
    void setDoorsPosition(int doorsPosition);
    void setPramRequest(int pramRequest);
    void setRampWheelChairLift(int rampWheelChairLift); //Not yet active in the API as of 2015-09-23
    void setIsStopPressed(boolean isStopPressed);
    void setStopRequest(int stopRequest);
    void setTotalDistanceDriven(long totalDistanceDriven);
    void setTurnSignals(int turnSignals);
    void setWlanRsslValue(int wlanRsslValue);
    void setWlanRssl2Value(int wlanRssl2Value);
    void setWlanCellIdValue(int wlanCellIdValue);
    void setWlanCellId2Value(int wlanCellId2Value);
}
