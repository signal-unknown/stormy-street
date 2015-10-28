package dat255.chalmers.stormystreet.model;

/**
 * Exception thrown wherever there is a function that returns a value that the buses does not
 * yet send out
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
