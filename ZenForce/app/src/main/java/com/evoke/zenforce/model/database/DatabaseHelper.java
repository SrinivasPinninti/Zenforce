package com.evoke.zenforce.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by spinninti on 11/19/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ZenForce.db";


    private static DatabaseHelper mDbHelper;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static DatabaseHelper getInstance(Context context) {

        if (mDbHelper == null) {
            mDbHelper = new DatabaseHelper(context);
        }
        return mDbHelper;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, " creating zenforce db... current version : " + db.getVersion());
//        db.execSQL(DbConstants.PlaceTable.CREATE_PLACE_TABLE);
        db.execSQL(DbConstants.VisitTable.CREATE_VISIT_TABLE);
        db.execSQL(DbConstants.TimerTable.CREATE_TIMER_TABLE);
        db.execSQL(DbConstants.PhotoTable.CREATE_PHOTO_TABLE);
        db.execSQL(DbConstants.NoteTable.CREATE_NOTE_TABLE);
        db.execSQL(DbConstants.LocationTable.CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, " upgrade zenforce db... current version : " + db.getVersion());
//        db.execSQL(DbConstants.PlaceTable.DELETE_PLACE_TABLE);
        db.execSQL(DbConstants.VisitTable.DELETE_VISIT_TABLE);
        db.execSQL(DbConstants.TimerTable.DELETE_TIMER_TABLE);
        db.execSQL(DbConstants.PhotoTable.DELETE_PHOTO_TABLE);
        db.execSQL(DbConstants.NoteTable.DELETE_NOTE_TABLE);
        db.execSQL(DbConstants.LocationTable.DELETE_LOCATION_TABLE);
        onCreate(db);
    }
}
