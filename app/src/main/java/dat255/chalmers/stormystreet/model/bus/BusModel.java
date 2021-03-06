package dat255.chalmers.stormystreet.model.bus;

import dat255.chalmers.stormystreet.model.IGpsCoord;
import dat255.chalmers.stormystreet.model.IJourneyInfo;
import dat255.chalmers.stormystreet.model.NotYetImplementedException;

/**
 * This class represents a bus with all resources that are available through the ElectriCity API.
 * Since some of the resources from the API aren't properly sending these will throw a
 * NotYetImplemeted exception when trying to access it.
 *
 * @author Kevin Hoogendijk
 * @since 2015-09-24
 */
public class BusModel implements IBus{
    private int dgwNumber;
    private int acceleratorPedalPosition;
    private double ambientTemperature;
    private boolean isAtStop;
    private int coolingAirConditioning;
    private double driverCabinTemperature;
    private IGpsCoord gpsPosition;
    private int fmsVersionSupported;
    private String mobileNetworkCellInfo;
    private String mobileNetworkSignalStrength;
    private String nextStop;
    private boolean isOffroute;
    private int onlineUsers;
    private int authenticatedUsers;
    private boolean isDoorOpen;
    private int doorsPosition;
    private int pramRequest;
    private int rampWheelChairLift;
    private boolean isStopPressed;
    private int stopRequest;
    private long totalDistanceDriven;
    private int turnSignals;
    private int wlanRsslValue;
    private int wlanRssl2Value;
    private int wlanCellIdValue;
    private int wlanCellId2Value;
    private String destination;

    //#######################################
    //          GETTERS
    //#######################################

    public int getDgwNumber() {
        return this.dgwNumber;
    }

    public int getAcceleratorPedalPosition() {
        return this.acceleratorPedalPosition;
    }

    public double getAmbientTemperature() {
        return this.ambientTemperature;
    }

    public boolean isAtStop() {
        return isAtStop;
    }

    public int getCoolingAirConditioning() {
        return this.coolingAirConditioning;
    }

    public double getDriverCabinTemperature() {
        return this.driverCabinTemperature;
    }

    public int getFmsVersionSupported() {
        throw new NotYetImplementedException("No signal from the api");
        //return this.fmsVersionSupported;
    }

    public IGpsCoord getGPSPosition() {
        if (gpsPosition != null) {
            return this.gpsPosition.clone();
        } else {
            return null;
        }
    }

    public String getDestination() {
        return this.destination;
    }

    public String getMobileNetworkCellInfo() {
        throw new NotYetImplementedException("No signal from the api");
        //return this.mobileNetworkCellInfo;
    }

    public String getMobileNetworkSignalStrength() {
        throw new NotYetImplementedException("No signal from the api");
        //return this.mobileNetworkSignalStrength;
    }

    public String getNextStop() {
        return this.nextStop;
    }

    public boolean isOffroute() {
        return this.isOffroute;
    }

    public int getOnlineUsers() {
        return this.onlineUsers;
    }

    public int getAuthenticatedUsers() {
        return this.authenticatedUsers;
    }

    public boolean isDoorOpen() {
        return this.isDoorOpen;
    }

    public int getDoorsPosition() {
        return this.doorsPosition;
    }

    public int getPramRequest() {
        return this.pramRequest;
    }

    public int getRampWheelChairLift() {
        throw new NotYetImplementedException("No signal from the api");
        //return this.rampWheelChairLift;
    }

    public boolean isStopPressed() {
        return this.isStopPressed;
    }

    public int getStopRequest() {
        return this.stopRequest;
    }

    public long getTotalDistanceDriven() {
        return this.totalDistanceDriven;
    }

    public int getTurnSignals() {
        return this.turnSignals;
    }

    public int getWlanRsslValue() {
        return this.wlanRsslValue;
    }

    public int getWlanRssl2Value() {
        return this.wlanRssl2Value;
    }

    public int getWlanCellIdValue() {
        return this.wlanCellIdValue;
    }

    public int getWlanCellId2Value() {
        return this.wlanCellId2Value;
    }


    //#######################################
    //          SETTERS
    //#######################################

    public synchronized void setDgwNumber(int dgwNumber) {
        this.dgwNumber = dgwNumber;
    }

    public synchronized void setAcceleratorPedalPosition(int acceleratorPedalPosition) {
        this.acceleratorPedalPosition = acceleratorPedalPosition;
    }

    public synchronized void setAmbientTemperature(double ambientTemperature) {
        this.ambientTemperature = ambientTemperature;
    }

    public synchronized void setCoolingAirConditioning(int coolingAirConditioning) {
        this.coolingAirConditioning = coolingAirConditioning;
    }

    public synchronized void setDriverCabinTemperature(double driverCabinTemperature) {
        this.driverCabinTemperature = driverCabinTemperature;
    }

    public synchronized void setGPSPosition(IGpsCoord gpsPosition) {
        if (gpsPosition != null) {
            this.gpsPosition = gpsPosition;
        }
    }

    public synchronized void setFmsVersionSupported(int fmsVersionSupported) {
        this.fmsVersionSupported = fmsVersionSupported;
    }

    public synchronized void setMobileNetworkCellInfo(String mobileNetworkCellInfo) {
        if (mobileNetworkCellInfo != null) {
            this.mobileNetworkCellInfo = mobileNetworkCellInfo;
        }
    }

    public synchronized void setMobileNetworkSignalStrength(String mobileNetworkSignalStrength) {
        if (mobileNetworkSignalStrength != null) {
            this.mobileNetworkSignalStrength = mobileNetworkSignalStrength;
        }
    }

    public synchronized void setNextStop(String nextStop) {
        if (nextStop != null) {
            if (Character.isUpperCase(nextStop.charAt(nextStop.length() - 1))) {
                nextStop = nextStop.substring(0, nextStop.length() - 1);
            }
            this.nextStop = nextStop;
        }
    }

    public synchronized void setIsOffroute(boolean isOffroute) {
        this.isOffroute = isOffroute;
    }

    public synchronized void setOnlineUsers(int onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public synchronized void setAuthenticatedUsers(int authenticatedUsers) {
        this.authenticatedUsers = authenticatedUsers;
    }

    public synchronized void setIsDoorOpen(boolean isDoorOpen) {
        this.isDoorOpen = isDoorOpen;
    }

    public synchronized void setDoorsPosition(int doorsPosition) {
        this.doorsPosition = doorsPosition;
    }

    public synchronized void setPramRequest(int pramRequest) {
        this.pramRequest = pramRequest;
    }

    public synchronized void setRampWheelChairLift(int rampWheelChairLift) {
        this.rampWheelChairLift = rampWheelChairLift;
    }

    public synchronized void setIsStopPressed(boolean isStopPressed) {
        this.isStopPressed = isStopPressed;
    }

    public synchronized void setStopRequest(int stopRequest) {
        this.stopRequest = stopRequest;
    }

    public synchronized void setTotalDistanceDriven(long totalDistanceDriven) {
        this.totalDistanceDriven = totalDistanceDriven;
    }

    public synchronized void setTurnSignals(int turnSignals) {
        this.turnSignals = turnSignals;
    }

    public synchronized void setWlanRsslValue(int wlanRsslValue) {
        this.wlanRsslValue = wlanRsslValue;
    }

    public synchronized void setWlanRssl2Value(int wlanRssl2Value) {
        this.wlanRssl2Value = wlanRssl2Value;
    }

    public synchronized void setWlanCellIdValue(int wlanCellIdValue) {
        this.wlanCellIdValue = wlanCellIdValue;
    }

    public synchronized void setWlanCellId2Value(int wlanCellId2Value) {
        this.wlanCellId2Value = wlanCellId2Value;
    }

    public synchronized void setDestination(String destination) {
        if (destination != null) {
            this.destination = destination;
        }
    }

    public synchronized void setIsAtStop(boolean isAtStop) {
        this.isAtStop = isAtStop;
    }
}