package dat255.chalmers.stormystreet.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
 */
public class SQLiteDataSource {

    private AppSQLiteWrapper appSQLiteWrapper;
    private SQLiteDatabase database;
    private String [] data_Table_Columns = {appSQLiteWrapper.COLUMN_ID, appSQLiteWrapper.COLUMN_START_TIME,
                                            appSQLiteWrapper.COLUMN_END_TIME, appSQLiteWrapper.COLUMN_DISTANCE};

    public SQLiteDataSource(Context context) {
        appSQLiteWrapper = new AppSQLiteWrapper(context);
    }
    public void open()throws SQLException {
        database = appSQLiteWrapper.getWritableDatabase();
    }
    public void close() {
        appSQLiteWrapper.close();
    }
    public DataValue saveData(DataValue dataValue) {

        //Adds all the datavalues to a contentvalues object, if too many values are provided it logs an error
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

        DataValue newDataValue = parseDatavalue(cursor);        //parses the data back to a datavalue object
        cursor.close();

        return newDataValue;
    }

    private DataValue parseDatavalue(Cursor cursor) {
        DataValue dataValue = new DataValue();
        dataValue.setId((cursor.getInt(0)));
        int numberColumns = getNumberOfColumns();
        for(int i = 1; i < numberColumns; i++){
            dataValue.addValue(Integer.toString(cursor.getInt(i)));
        }
        return dataValue;
    }

    public void deleteData(DataValue dataValue) {
        long id = dataValue.getId();
        Log.d("Delete", "Bus data deleted with: " + id);
        database.delete(appSQLiteWrapper.TABLE, appSQLiteWrapper.COLUMN_ID + " = " + id, null);
    }

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

    private int getNumberOfColumns(){
        Cursor ti = database.rawQuery("PRAGMA table_info(" + appSQLiteWrapper.TABLE + ")", null);
        int i = 0;
        if ( ti.moveToFirst() ) {
            do {
                i++;
            } while (ti.moveToNext());
        }
        return i;
    }
}



