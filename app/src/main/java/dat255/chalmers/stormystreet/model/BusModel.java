package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-24
 */
public class BusModel implements IBus{
    private int dgwNumber;
    private int acceleratorPedalPosition;
    private int ambientTemperature;
    private boolean isAtStop;
    private int coolingAirConditioning;
    private int driverCabinTemperature;
    private IGpsCoord gpsPosition;
    private int fmsVersionSupported;
    private IJourneyInfo journeyInfo;
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

    public BusModel(){

    }

    //#######################################
    //          GETTERS
    //#######################################

    public int getDgwNumber() {
        return this.dgwNumber;
    }

    public int getAcceleratorPedalPosition() {
        return this.acceleratorPedalPosition;
    }

    public int getAmbientTemperature() {
        return this.ambientTemperature;
    }

    public boolean isAtStop() {
        return isAtStop;
    }

    public int getCoolingAirConditioning() {
        return this.coolingAirConditioning;
    }

    public int getDriverCabinTemperature() {
        return this.driverCabinTemperature;
    }

    public int getFmsVersionSupported() {
        return this.fmsVersionSupported;
    }

    public IGpsCoord getGPSPosition() {
        return this.gpsPosition;
    }

    public IJourneyInfo getJourneyInfo() {
        return this.journeyInfo;
    }

    public String getMobileNetworkCellInfo() {
        return this.mobileNetworkCellInfo;
    }

    public String getMobileNetworkSignalStrength() {
        return this.mobileNetworkSignalStrength;
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
        return this.rampWheelChairLift;
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

    public void setDgwNumber(int dgwNumber) {
        this.dgwNumber = dgwNumber;
    }

    public void setAcceleratorPedalPosition(int acceleratorPedalPosition) {
        this.acceleratorPedalPosition = acceleratorPedalPosition;
    }

    public void setAmbientTemperature(int ambientTemperature) {
        this.ambientTemperature = ambientTemperature;
    }

    public void setCoolingAirConditioning(int coolingAirConditioning) {
        this.coolingAirConditioning = coolingAirConditioning;
    }

    public void setDriverCabinTemperature(int driverCabinTemperature) {
        this.driverCabinTemperature = driverCabinTemperature;
    }

    public void setGPSPosition(IGpsCoord gpsPosition) {
        this.gpsPosition = gpsPosition;
    }

    public void setFmsVersionSupported(int fmsVersionSupported) {
        this.fmsVersionSupported = fmsVersionSupported;
    }

    public void setJourneyInfo(IJourneyInfo journeyInfo) {
        this.journeyInfo = journeyInfo;
    }

    public void setMobileNetworkCellInfo(String mobileNetworkCellInfo) {
        this.mobileNetworkCellInfo = mobileNetworkCellInfo;
    }

    public void setMobileNetworkSignalStrength(String mobileNetworkSignalStrength) {
        this.mobileNetworkSignalStrength = mobileNetworkSignalStrength;
    }

    public void setNextStop(String nextStop) {
        this.nextStop = nextStop;
    }

    public void setIsOffroute(boolean isOffroute) {
        this.isOffroute = isOffroute;
    }

    public void setOnlineUsers(int onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public void setAuthenticatedUsers(int authenticatedUsers) {
        this.authenticatedUsers = authenticatedUsers;
    }

    public void setIsDoorOpen(boolean isDoorOpen) {
        this.isDoorOpen = isDoorOpen;
    }

    public void setDoorsPosition(int doorsPosition) {
        this.doorsPosition = doorsPosition;
    }

    public void setPramRequest(int pramRequest) {
        this.pramRequest = pramRequest;
    }

    public void setRampWheelChairLift(int rampWheelChairLift) {
        this.rampWheelChairLift = rampWheelChairLift;
    }

    public void setIsStopPressed(boolean isStopPressed) {
        this.isStopPressed = isStopPressed;
    }

    public void setStopRequest(int stopRequest) {
        this.stopRequest = stopRequest;
    }

    public void setTotalDistanceDriven(long totalDistanceDriven) {
        this.totalDistanceDriven = totalDistanceDriven;
    }

    public void setTurnSignals(int turnSignals) {
        this.turnSignals = turnSignals;
    }

    public void setWlanRsslValue(int wlanRsslValue) {
        this.wlanRsslValue = wlanRsslValue;
    }

    public void setWlanRssl2Value(int wlanRssl2Value) {
        this.wlanRssl2Value = wlanRssl2Value;
    }

    public void setWlanCellIdValue(int wlanCellIdValue) {
        this.wlanCellIdValue = wlanCellIdValue;
    }

    public void setWlanCellId2Value(int wlanCellId2Value) {
        this.wlanCellId2Value = wlanCellId2Value;
    }

    public void setIsAtStop(boolean isAtStop) {
        isAtStop = isAtStop;
    }
}