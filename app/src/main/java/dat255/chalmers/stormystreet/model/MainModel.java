package dat255.chalmers.stormystreet.model;

import java.util.HashSet;
import java.util.Set;

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
    private Set<IModelListener> listeners;

    public MainModel(){
        this.busManager = new BusManager();
        this.currentUser = new User("Dummy namn");
        this.listeners = new HashSet<>();
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

    public void addListener(IModelListener listener){
        if(listener != null){
            this.listeners.add(listener);
        }
    }

    public void removeListener(IModelListener listener){
        this.listeners.remove(listener);
    }

    public void notifyListeners(){
        for(IModelListener listener:listeners){
            listener.modelUpdated();
        }
    }
}
