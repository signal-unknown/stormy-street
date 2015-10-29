package dat255.chalmers.stormystreet.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author David Fogelberg
 * Revised by Kevin Hoogendijk
 *
 * This class is a subclass of the SQLiteOpenHelper class.
 * It is responsible for creating the database, opening the database if it exists
 * and updating the database if a new version is available.
 *
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
    /**
     *
     * @param context used to open or create the database.
     * Method creates a helper object which can be used to create, open or manage the database.
     */
    public AppSQLiteWrapper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * @param database the database object.
     *
     * Method creates a database for the first time with specific tables.
     */
    @Override
    public void onCreate (SQLiteDatabase database) {
        Log.i("DB", "Database creating");
        database.execSQL(CREATE_DATABASEe);
        Log.i("DB", "Database created");
    }

    /**
     * @param db the database object.
     * @param oldVersion database version number.
     * @param newVersion the new database version number.
     *
     *  Method upgrades the current database by droping tables and adding tables.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2) {
            db.execSQL("ALTER TABLE _value DROP COLUMN data");
        }
    }

}
