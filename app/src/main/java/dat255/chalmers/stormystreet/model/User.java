package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-29
 */
public class User implements IUser {
    private String name;
    private IStatistics statistics;
    private boolean isOnBus = false;
    private int currentBusNumber;

    public User(String name, IStatistics statistics, int currentBusNumber) {
        this.name = name;
        this.statistics = statistics;
        this.currentBusNumber = currentBusNumber;
    }

    public User(String name, IStatistics statistics) {
        this(name, statistics, 0);
    }

    public User(String name, int currentBusNumber) {
        this(name, new Statistics(), currentBusNumber);
    }

    public User(String name){
        this(name, new Statistics(), 0);
    }

    public String getName() {
        return this.name;
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
