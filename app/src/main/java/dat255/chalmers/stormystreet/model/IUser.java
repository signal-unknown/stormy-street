package dat255.chalmers.stormystreet.model;

import java.util.List;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-23
 * Interface for a users profile
 */
public interface IUser {
    String getName();
    IStatistics getStatistics();
    boolean getIsOnBus();
    void setIsOnBus(boolean isOnBus);
    int getCurrentBusNumber();
    void setCurrentBusNumber(int currentBusNumber);
    void setName(String name);
}
