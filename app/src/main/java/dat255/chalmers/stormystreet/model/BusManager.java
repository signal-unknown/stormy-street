package dat255.chalmers.stormystreet.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-24
 */
public class BusManager {
    List<IBus> busList;

    public BusManager(){
        busList = new ArrayList<>();
    }

    public void addBus(IBus bus){
        busList.add(bus);
    }

    public void addBus(List<IBus> busList){
        for(IBus bus : busList){
            this.busList.add(bus);
        }
    }

    public void removeBus(IBus bus){
        busList.remove(bus);
    }

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
