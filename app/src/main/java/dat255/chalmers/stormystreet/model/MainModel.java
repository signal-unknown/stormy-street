package dat255.chalmers.stormystreet.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.model.bus.BusManager;
import dat255.chalmers.stormystreet.model.bus.BusNotFoundException;
import dat255.chalmers.stormystreet.model.bus.IBus;
import dat255.chalmers.stormystreet.model.bus.IBusTrip;

/**
 * The toplevel model of the app
 * @author Kevin Hoogendijk
 * @since 2015-09-24
 */
public class MainModel {
    private BusManager busManager;
    private CurrentTrip currentTrip;
    private IUser currentUser;
    private Set<IModelListener> listeners;
    private List<FacebookFriend> highscoreList;

    public MainModel(){
        this.busManager = new BusManager();
        this.currentUser = new User("Dummy namn");
        this.listeners = new HashSet<>();
        this.currentTrip = new CurrentTrip(0,0);
        highscoreList = new ArrayList<>();
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
        notifyListeners();
    }

    public void addBus(List<IBus> busses){
        busManager.addBus(busses);
        notifyListeners();
    }

    public List<IBus> getAllBusses(){
        return busManager.getAllBusses();
    }

    public IBus getBus(int dgwNumber) throws BusNotFoundException{
        return busManager.getBus(dgwNumber);
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

    public long getWeeklyAverageScore(){
        return currentUser.getStatistics().getWeeklyAverageScore();
    }

    public List<IBusTrip> getAllBusTrips(){
        return currentUser.getStatistics().getAllBusTrips();
    }

    public long getTimeSpentOnBus(){
        return currentUser.getStatistics().getTimeSpentOnBus();
    }

    //get only the score from the database
    public IScore getTotalScore(){
        return currentUser.getStatistics().getTotalScore();
    }

    //Get score from database combined with the score of the current trip
    public IScore getTotalPlusCurrentScore(){
        if(currentTrip == null){
            return currentUser.getStatistics().getTotalScore();
        }else {
            return new Score(currentUser.getStatistics().getTotalScore().getValue() + getCurrentTrip().getDistance(), "m");
        }
    }

    public boolean getIsOnBus(){
        return currentUser.getIsOnBus();
    }

    public void setIsOnBus(boolean isOnBus){
        currentUser.setIsOnBus(isOnBus);
        notifyListeners();
    }

    public void setCurrentTrip(CurrentTrip currentTrip) {
        this.currentTrip = currentTrip;
        notifyListeners();
    }

    public CurrentTrip getCurrentTrip() {
        return currentTrip;
    }

    public void setCurrentTripDistance(long distance){
        Log.d("CurrentTrip", "Changed current distance to " + distance);
        this.currentTrip.setDistance(distance);
        notifyListeners();
    }

    public IUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(IUser currentUser) {
        this.currentUser = currentUser;
        notifyListeners();
    }

    public List<FacebookFriend> getHighscoreList(){
        return this.highscoreList;
    }

    public void setHighscoreList(List<FacebookFriend> input){
        this.highscoreList = input;
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
