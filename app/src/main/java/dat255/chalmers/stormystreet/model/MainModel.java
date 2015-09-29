package dat255.chalmers.stormystreet.model;

import dat255.chalmers.stormystreet.model.bus.BusManager;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-24
 * The toplevel model of the app
 */
public class MainModel {
    private BusManager busManager;
    private CurrentTrip currentTrip;
    private IUser currentUser;

    public MainModel(){
        this.busManager = new BusManager();
        this.currentUser = new User("Dummy namn");
    }

    public BusManager getBusManager() {
        return busManager;
    }

    public void setBusManager(BusManager busManager) {
        this.busManager = busManager;
    }

    public CurrentTrip getCurrentTrip() {
        return currentTrip;
    }

    public void setCurrentTrip(CurrentTrip currentTrip) {
        this.currentTrip = currentTrip;
    }

    public IUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(IUser currentUser) {
        this.currentUser = currentUser;
    }
}
