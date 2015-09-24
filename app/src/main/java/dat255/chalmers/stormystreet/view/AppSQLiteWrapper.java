package dat255.chalmers.stormystreet.view;

/**
 * Created by DavidF on 2015-09-23.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;


public class AppSQLiteWrapper extends SQLiteOpenHelper {

    private static final String columns = "value";
    private static final String table = "Values";
    private static final String database_Name = "ValueStorage.db";
    private static final int version = 1;
    private static final String create_Database = "Create table " + table + " "  + columns;


    public AppSQLiteWrapper(Context context) {

        // Setting up SQLite_Database, with specifying name and version....
        super(context, database_Name, null, version);
    }
    @Override
    public void onCreate (SQLiteDatabase database) {

        //create database

        database.execSQL(create_Database);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(AppSQLiteWrapper.class.getName(), "upgrading database to " + newVersion + " from " + oldVersion);
        db.execSQL(table);
        onCreate(db);


    }

}
