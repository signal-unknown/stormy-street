package dat255.chalmers.stormystreet.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author David Fogelberg
 * Revised by Kevin Hoogendijk
 */
public class AppSQLiteWrapper extends SQLiteOpenHelper {

    public static final String TABLE = "_value";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";
    public static final String COLUMN_DISTANCE = "distance";

    private static final String DATABASE_NAME = "BusTrips.db";
    private static final int VERSION = 2;
    private static final String CREATE_DATABASEe = "create TABLE " + TABLE
                                                + "(" + COLUMN_ID + " integer primary key autoincrement, "
                                                + COLUMN_START_TIME + " integer not null, "
                                                + COLUMN_END_TIME + " integer not null, "
                                                + COLUMN_DISTANCE + " integer not null);";

    public AppSQLiteWrapper(Context context) {
        // Setting up SQLite_Database, with specifying name and version....
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate (SQLiteDatabase database) {
        Log.i("DB", "Database creating");
        database.execSQL(CREATE_DATABASEe);
        Log.i("DB", "Database created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2) {
            db.execSQL("ALTER TABLE _value DROP COLUMN data");
        }
        //TODO: change method to keep values from previous database
    }

}
