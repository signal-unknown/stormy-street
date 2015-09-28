package dat255.chalmers.stormystreet.model;

/**
 * Created by David Fogelberg on 2015-09-25.
 * Data class
 */
public class DataValue {

    private long id;
    private String dataValue;


    public long getId() {
        // returns the id of specific column
        return id;
    }
    public void setId(long id) {

        this.id = id;
    }
    public void setName(String dataValue) {

        this.dataValue = dataValue;

    }
    public String getDataValue() {

        return dataValue;
    }



}


