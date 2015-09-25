package dat255.chalmers.stormystreet.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dat255.chalmers.stormystreet.model.AppSQLiteWrapper;
import dat255.chalmers.stormystreet.model.DataValue;

/**
 * Created by DavidF on 2015-09-23.
 */
public class SQLiteDataSource {

    private AppSQLiteWrapper appSQLiteWrapper;
    private SQLiteDatabase database;
    private String [] data_Table_Columns = {appSQLiteWrapper.column_id, appSQLiteWrapper.database_Name};

    public SQLiteDataSource(Context context) {
        appSQLiteWrapper = new AppSQLiteWrapper(context);
    }
    private void open()throws SQLException {

        database = appSQLiteWrapper.getWritableDatabase();
    }
    private void close() {

        appSQLiteWrapper.close();

    }
    private DataValue saveData(String dataValue) {

        ContentValues values  = new ContentValues();
        values.put(appSQLiteWrapper.column_Data, dataValue);

        long addId = database.insert(appSQLiteWrapper.table, null, values);
        Cursor cursor = database.query(appSQLiteWrapper.table, data_Table_Columns, appSQLiteWrapper.column_id + " = " + addId,
                null, null, null, null);
        cursor.moveToFirst();

        DataValue newDataValue = parseDatavalue(cursor);
        cursor.close();

        return newDataValue;
    }
    private DataValue parseDatavalue(Cursor cursor) {

        DataValue dataValue = new DataValue();
        dataValue.setId(cursor.getInt(0));
        dataValue.setName(cursor.getString(1));
        return dataValue;
    }
    private void deleteData(DataValue dataValue) {

        long id = dataValue.getId();
        Log.i("Delete", "Bus data deleted with: " + id);
        database.delete(appSQLiteWrapper.table, appSQLiteWrapper.column_id + " = " + id, null);
    }
    private List getAllDataValues() {
        List dataList = new ArrayList();

        Cursor cursor = database.query(appSQLiteWrapper.table,data_Table_Columns,null,null,null,null,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {

        DataValue dataValue = parseDatavalue(cursor);
        dataList.add(dataValue);
        cursor.moveToNext();

        }

        cursor.close();

        return dataList;
    }
}



