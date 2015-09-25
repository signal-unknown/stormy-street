package dat255.chalmers.stormystreet.model;

import java.util.List;

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
