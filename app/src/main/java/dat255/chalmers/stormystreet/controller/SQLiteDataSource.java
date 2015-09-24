package dat255.chalmers.stormystreet.controller;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

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
    private void saveData() {


    }
    private void deleteData(DataValue dataValue) {

        long id = dataValue.getId();
        Log.i("Delete", "The value deleted is: " + id );
        database.delete(appSQLiteWrapper.table,appSQLiteWrapper.column_id, null);

    }


}
