package dat255.chalmers.stormystreet.model;

/**
 * Created by David Fogelberg on 2015-09-23.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;


public class AppSQLiteWrapper extends SQLiteOpenHelper {

    public static final String column = "_value";
    public static final String column_id = "_id";
    public static final String column_name = "data";

    private static final String database_Name = "ValueStorage.db";
    private static final int version = 1;
    private static final String create_Database = "create table " + column

            + "(" + column_id + " integer primary key autoincrement, "

            + column_name + " data not null);";



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

        db.execSQL(column);
        onCreate(db);


    }

}
