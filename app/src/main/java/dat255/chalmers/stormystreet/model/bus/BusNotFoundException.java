package dat255.chalmers.stormystreet.model.bus;

/**
 * Exception that is thrown if there is no bus with the specified dgwnumber
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
