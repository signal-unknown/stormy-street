package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-24
 */
public class NotYetImplementedException extends RuntimeException{
    public NotYetImplementedException(String message) {
        super(message);
    }

    public NotYetImplementedException(){
        super();
    }
}
