package dat255.chalmers.stormystreet.model;

/**
 * Representing a user and all that is related to it for example which bus he/she is on
 * Also holds statistics for the user
 * @author Kevin Hoogendijk
 * @since 2015-09-29
 */
public class User implements IUser {
    private IStatistics statistics;
    private boolean isOnBus = false;
    private int currentBusNumber;

    public User(IStatistics statistics, int currentBusNumber) {
        this.statistics = statistics;
        this.currentBusNumber = currentBusNumber;
    }

    public User(IStatistics statistics) {
        this(statistics, 0);
    }

    public User(int currentBusNumber) {
        this(new Statistics(), currentBusNumber);
    }

    public User(){
        this(new Statistics(), 0);
    }

    public IStatistics getStatistics() {
        return this.statistics;
    }

    public boolean getIsOnBus() {
        return this.isOnBus;
    }

    public void setIsOnBus(boolean isOnBus) {
        this.isOnBus = isOnBus;
    }

    public int getCurrentBusNumber() {
        return this.currentBusNumber;
    }

    public void setCurrentBusNumber(int currentBusNumber) {
        this.currentBusNumber = currentBusNumber;
    }
}
