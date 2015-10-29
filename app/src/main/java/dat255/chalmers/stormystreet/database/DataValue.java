package dat255.chalmers.stormystreet.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Fogelberg on 2015-09-25.
 * @author David Fogelberg
 *
 * This model class is used for the database SQLite and is initiated in the GlobalState class.
 * It contains data which will be saved in the database and showed in the user interface.
 * Data such as ID of specific data, start time, end time and distance of current bus trip.
 *
 */
public class DataValue {

    private int id;
    private List<String> dataValues;

    /**
     * A new list of dataValues which will contain bus data.
     */
    public DataValue(){
        dataValues = new ArrayList<>();
    }
    /**
     * @return The id of a specific row.
     */
    public long getId() {

        return id;
    }
    /**
     *
     * @param id The new id for the bus data.
     * Method sets a specific value id for the data when saved in the database.
     */
    public void setId(int id) {

        this.id = id;
    }
    /**
     *
     * @param values Names of data values.
     * Method adds every data value to the list "dataValues".
     */
    public void addValues(String... values){
        for(String value : values){
            dataValues.add(value);
        }
    }
    /**
     *
     * @param value Name of new value.
     * Method adds the new value to the list "dataValues".
     *
     */
    public void addValue(String value){
        dataValues.add(value);
    }
    /**
     *
     * @return The list of data values.
     */
    public List<String> getValues(){
        return this.dataValues;
    }
    /**
     *
     * @return The amount of datavalues saved in the database.
     */
    public int getNumberOfValues(){
        return dataValues.size();
    }
    /**
     *
     * @return The data converted to "String" type.
     * Method converts every data value to a "String" type.
     */
    @Override
    public String toString() {
        String toString = "";
        for(String value : dataValues){
            toString += value + " ";
        }

        return toString;
    }



}


