package dat255.chalmers.stormystreet.model;

/**
 * @author Kevin Hoogendijk
 * @since 2015-09-28
 */
public class Score implements IScore {
    private long value;
    private String suffix;

    public Score(long value, String suffix){
        this.value = value;
        this.suffix = suffix;
    }

    public long getValue() {
        return this.value;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String toString(){
        return Long.toString(this.value);
    }
}
