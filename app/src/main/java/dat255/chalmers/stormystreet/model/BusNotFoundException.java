package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-24
 */
public class BusNotFoundException extends Exception {
    public BusNotFoundException(String message){
        super(message);
    }

    public BusNotFoundException(){
        super();
    }
}
