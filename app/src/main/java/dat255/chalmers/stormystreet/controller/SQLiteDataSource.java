package dat255.chalmers.stormystreet.controller;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.util.Log;

import dat255.chalmers.stormystreet.model.AppSQLiteWrapper;
import dat255.chalmers.stormystreet.model.DataValue;

/**
 * Created by DavidF on 2015-09-23.
 */
public class SQLiteDataSource {

    private AppSQLiteWrapper appSQLiteWrapper;
    private SQLiteDatabase database;

    public SQLiteDataSource(Context context) {
        appSQLiteWrapper = new AppSQLiteWrapper(context);
    }
    private void open()throws SQLException {

        database = appSQLiteWrapper.getWritableDatabase();
    }
    private void close() {

        appSQLiteWrapper.close();

    }
    private void saveData(String dataValue) {

        ContentValues values  = new ContentValues();
        values.put(appSQLiteWrapper.column_Data, dataValue);

        long addId = database.insert(appSQLiteWrapper.table, null, values);
       // Cursor cursor = database.query(appSQLiteWrapper.table, null, )





    }
    private void deleteData(DataValue dataValue) {

        long id = dataValue.getId();
        Log.i("Delete", "Bus data deleted with: " + id );
        database.delete(appSQLiteWrapper.table,appSQLiteWrapper.column_id + " = " + id, null);
    }


}



