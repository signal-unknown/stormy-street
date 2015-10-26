package dat255.chalmers.stormystreet.model.bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of all the busses, has methods for getting and setting and so on
 * @author Kevin Hoogendijk
 * @since 2015-09-24
 */
public class BusManager {
    private List<IBus> busList;

    public BusManager(){
        busList = new ArrayList<>();
    }

    public synchronized void addBus(IBus bus){
        busList.add(bus);
    }

    public synchronized void addBus(List<IBus> busList){
        for(IBus bus : busList){
            this.busList.add(bus);
        }
    }

    public synchronized void removeBus(IBus bus){
        busList.remove(bus);
    }

    /**
     * Gets a bus based on its VIN number.
     *
     * @param dgwNumber The id of the bus to get
     * @return A bus object representing the bus with the specified ID
     * @throws BusNotFoundException Thrown if no bus with the specified id could be found.
     */
    public IBus getBus(int dgwNumber) throws BusNotFoundException{
        for(IBus bus : busList){
            if(bus.getDgwNumber() == dgwNumber){
                return bus;
            }
        }
        throw new BusNotFoundException("The bus with the dgwnumber " + dgwNumber + " does not exist");
    }

    public List<IBus> getAllBusses(){
        return this.busList;
    }
}
