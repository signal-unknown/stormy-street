package dat255.chalmers.stormystreet.model;

/**
 * Created by DavidF on 2015-09-23.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;


public class AppSQLiteWrapper extends SQLiteOpenHelper {

    public static final String column_id = "value";
    public static final String table = "Values";
    public static final String column_Data = "data";

    public static final String database_Name = "ValueStorage.db";
    private static final int version = 1;
    private static final String create_Database = "Create table " + table + " "  + column_id;


    public AppSQLiteWrapper(Context context) {

        // Setting up SQLite_Database, with specifying name and version....
        super(context, database_Name, null, version);
    }
    @Override
    public void onCreate (SQLiteDatabase database) {


        database.execSQL(create_Database);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(table);
        onCreate(db);


    }

}
