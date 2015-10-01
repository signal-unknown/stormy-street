package dat255.chalmers.stormystreet.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dat255.chalmers.stormystreet.model.bus.BusManager;
import dat255.chalmers.stormystreet.model.bus.BusNotFoundException;
import dat255.chalmers.stormystreet.model.bus.IBus;
import dat255.chalmers.stormystreet.model.bus.IBusTrip;

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
        this.currentTrip = new CurrentTrip(0,0);
    }

    public BusManager getBusManager() {
        return busManager;
    }

    public void setBusManager(BusManager busManager) {
        this.busManager = busManager;
        notifyListeners();
    }

    public void addBus(IBus bus){
        busManager.addBus(bus);
    }

    public void addBus(List<IBus> busses){
        busManager.addBus(busses);
    }

    public List<IBus> getAllBusses(){
        return busManager.getAllBusses();
    }

    public IBus getBus(int dgwNumber) throws BusNotFoundException{
        return busManager.getBus(dgwNumber);
    }

    public CurrentTrip getCurrentTrip() {
        return currentTrip;
    }

    public IStatistics getUserStatistics(){
        return currentUser.getStatistics();
    }

    public void addBusTrip(IBusTrip busTrip){
        currentUser.getStatistics().addBusTrip(busTrip);
        notifyListeners();
    }

    public void addBusTrip(List<IBusTrip> busTrips){
        currentUser.getStatistics().addBusTrip(busTrips);
        notifyListeners();
    }

    public IScore getWeeklyAverageScore(){
        return currentUser.getStatistics().getWeeklyAverageScore();
    }

    public List<IBusTrip> getAllBusTrips(){
        return currentUser.getStatistics().getAllBusTrips();
    }

    public long getTimeSpentOnBus(){
        return currentUser.getStatistics().getTimeSpentOnBus();
    }

    public IScore getTotalScore(){
        return currentUser.getStatistics().getTotalScore();
    }

    public String getCurrentUsername(){
        return currentUser.getName();
    }

    public int getCurrentBusNumber(){
        return currentUser.getCurrentBusNumber();
    }

    public boolean getIsOnBus(){
        return currentUser.getIsOnBus();
    }

    public void setCurrentBusNumber(int currentBusNumber){
        currentUser.setCurrentBusNumber(currentBusNumber);
        notifyListeners();
    }

    public void setIsOnBus(boolean isOnBus){
        currentUser.setIsOnBus(isOnBus);
        notifyListeners();
    }

    public void setCurrentTrip(CurrentTrip currentTrip) {
        this.currentTrip = currentTrip;
        notifyListeners();
    }

    public IUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(IUser currentUser) {
        this.currentUser = currentUser;
        notifyListeners();
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
