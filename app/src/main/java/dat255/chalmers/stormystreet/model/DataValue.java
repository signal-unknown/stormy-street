package dat255.chalmers.stormystreet.model;

import java.util.List;

/**
 * Created by David Fogelberg on 2015-09-25.
 * Data class
 */
public class DataValue {

    private int id;
    private List<String> dataValues;


    public long getId() {
        // returns the id of specific row
        return id;
    }
    public void setId(int id) {

        this.id = id;
    }

    public void addValues(String... values){
        for(String value : values){
            dataValues.add(value);
        }
    }

    public void addValue(String value){
        dataValues.add(value);
    }

    @Override
    public String toString() {
        String toString = ":";
        for(String value : dataValues){
            toString += value + ":";
        }

        return toString;
    }



}


