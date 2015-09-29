package dat255.chalmers.stormystreet.model;

import dat255.chalmers.stormystreet.model.bus.BusManager;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-24
 * The toplevel model of the app
 */
public class MainModel {
    private BusManager busManager;

    public MainModel(){
        this.busManager = new BusManager();
    }
}
