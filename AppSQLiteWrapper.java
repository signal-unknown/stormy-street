package com.dat55.chalmers.stormy_street;

/**
 * Created by DavidF on 2015-09-23.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;


public class AppSQLiteWrapper extends SQLiteOpenHelper {

    private static final String Columns = "value";
    private static final String Table = "Values";
    private static final String Database_Name = "ValueStorage.db";
    private static final int Version = 1;
    private static final String Create_Database = "Create table " + Table + " "  + Columns;


    public AppSQLiteWrapper(Context context) {

        // Setting up SQLite_Database, with specifying name and version....
        super(context, Database_Name, null, Version);
    }
    @Override
    public void onCreate (SQLiteDatabase database) {


        database.execSQL(Create_Database);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(Table);
        onCreate(db);


    }

}
