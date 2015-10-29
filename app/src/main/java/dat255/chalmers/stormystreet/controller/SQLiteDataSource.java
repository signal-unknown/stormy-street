package dat255.chalmers.stormystreet.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dat255.chalmers.stormystreet.model.AppSQLiteWrapper;
import dat255.chalmers.stormystreet.model.DataValue;

/**
 * Created by David Fogelberg on 2015-09-23.
 * revised by Kevin Hoogendijk
 *
 * This class is responsible for maintaining the database connection, saving data to the database, deleting data form the database,
 * fetching all data from the database and to fetch timestamps.
 *
 */
public class SQLiteDataSource {

    private AppSQLiteWrapper appSQLiteWrapper;
    private SQLiteDatabase database;
    private String [] data_Table_Columns = {appSQLiteWrapper.COLUMN_ID, appSQLiteWrapper.COLUMN_START_TIME,
                                            appSQLiteWrapper.COLUMN_END_TIME, appSQLiteWrapper.COLUMN_DISTANCE};
    /**
     *
     * @param context is used to create a new database.
     * Constructor creates a new AppSQLiteWrapper, which is a helper object for creating, opening and managing the database.
     */
    public SQLiteDataSource(Context context) {
        appSQLiteWrapper = new AppSQLiteWrapper(context);
    }
    /**
     *
     * Method creates a database or opens a already created database.
     * When this method is called for the first time the database will be opened
     * and onCreate or onUpgrade from AppSQLiteWrapper will be called depending on the situation.
     *
     */
    public void open()throws SQLException {
        database = appSQLiteWrapper.getWritableDatabase();
    }
    /**
     * Method closes the database.
     */
    public void close() {
        appSQLiteWrapper.close();
    }
    /**
     *
     * @param dataValue Contains the data from the buses.
     * @return a new datavalue with parsed data.
     * Method  Adds all the datavalues to a contentvalues object, if too many values are provided it logs an error.
     */
    public DataValue saveData(DataValue dataValue) {

        ContentValues values  = new ContentValues();
        for(int i = 0; i < dataValue.getNumberOfValues(); i++){
            if(data_Table_Columns.length > i) {
                values.put(data_Table_Columns[i+1], dataValue.getValues().get(i));
            }else{
                Log.e("Database::saveData", "Too many values provided");
            }
        }

        long addId = database.insert(appSQLiteWrapper.TABLE, null, values);         //inserts the row

        Cursor cursor = database.query(appSQLiteWrapper.TABLE, data_Table_Columns, appSQLiteWrapper.COLUMN_ID + " = " + addId, //gets the row
                null, null, null, null);
        cursor.moveToFirst();

        DataValue newDataValue = parseDatavalue(cursor);
        cursor.close();

        return newDataValue;
    }
    /**
     *
     * @param cursor points to the current data in the database.
     * @return a new dataValue containing the bus data.
     * Method parses the data back to a datavalue object
     */
    private DataValue parseDatavalue(Cursor cursor) {
        DataValue dataValue = new DataValue();
        dataValue.setId((cursor.getInt(0)));
        int numberColumns = getNumberOfColumns();
        for(int i = 1; i < numberColumns; i++){
            dataValue.addValue(Integer.toString(cursor.getInt(i)));
        }
        return dataValue;
    }
    /**
     *
     * @param dataValue object containing bus data.
     * Method deletes the specifc dataValue in the database.
     */
    public void deleteData(DataValue dataValue) {
        long id = dataValue.getId();
        Log.d("Delete", "Bus data deleted with: " + id);
        database.delete(appSQLiteWrapper.TABLE, appSQLiteWrapper.COLUMN_ID + " = " + id, null);
    }
    /**
     *
     * @return a list containing all dataValues.
     * Method adds all dataValues to an list.
     */
    public List<DataValue> getAllDataValues() {
        List dataList = new ArrayList();

        Cursor cursor = database.query(appSQLiteWrapper.TABLE, data_Table_Columns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            DataValue dataValue = parseDatavalue(cursor);
            dataList.add(dataValue);
            cursor.moveToNext();
        }

        cursor.close();

        return dataList;
    }
    /**
     *
     * @return the last timestamp that was recorded before the current timestamp.
     * Method fetches the last timestamp.
     *
     */
    public long getLastTimeStamp() throws CursorIndexOutOfBoundsException{
        Cursor cursor = database.rawQuery("SELECT * FROM _value ORDER BY end_time DESC LIMIT 1", null);
        cursor.moveToFirst();
        long timestamp = cursor.getLong(2);
        cursor.close();
        return timestamp;
    }
    /**
     *
     * @return number of columns
     * Method counts how many columns there are in the database.
     */
    private int getNumberOfColumns(){
        Cursor ti = database.rawQuery("PRAGMA table_info(" + appSQLiteWrapper.TABLE + ")", null);
        int i = 0;
        if ( ti.moveToFirst() ) {
            do {
                i++;
            } while (ti.moveToNext());
        }
        ti.close();
        return i;
    }
}



