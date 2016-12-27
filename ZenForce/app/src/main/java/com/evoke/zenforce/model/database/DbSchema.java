/*
package com.evoke.zenforce.model.database;

import android.provider.BaseColumns;

*/
/**
 * Created by spinninti on 11/20/2016.
 *//*

public interface DbSchema {

    String DB_NAME = "zenforce.db";

    String TABLE_PLACE = "place";

     String _ID = BaseColumns._ID;
     int ID_ID = 0;
     String COLUMN_NAME = "name";
    int ID_NAME = 1;
     String COLUMN_ADDRESS = "address";
    int ID_ADDRESS = 2;
     String COLUMN_PLACE_ID = "placeId";
    int ID_PLACE_ID = 3;
     String COLUMN_PHONE = "phone";
    int ID_PHONE = 4;
     String COLUMN_WEBSITE = "website";
    int ID_WEBSITE = 5;

    String CREATE_PLACE_TABLE = "CREATE TABLE " + TABLE_PLACE + "("
            + DbSchema._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbSchema.COLUMN_NAME + " TEXT NOT NULL, "
            + DbSchema.COLUMN_ADDRESS + " TEXT, "
            + DbSchema.COLUMN_PLACE_ID + " TEXT NOT NULL, "
            + DbSchema.COLUMN_PHONE + " TEXT, "
            + DbSchema.COLUMN_WEBSITE + " TEXT"
            + ")";

    String DELETE_PLACE_TABLE = "DROP TABLE IF EXISTS " + TABLE_PLACE;

    String DML_WHERE_ID_CLAUSE = "_id = ?";

    String DEFAULT_TBL_ITEMS_SORT_ORDER = "name ASC";


    String TABLE_VISIT = "visit";

    String _ID = BaseColumns._ID;
    int ID_VISIT_ID = 0;
    String COLUMN_VISIT_PLACE_ID = "placeId";
    int ID_VISIT_PLACE_ID = 1;
    String COLUMN_VISIT_START_TIME = "start_time";
    int ID_VISIT_START_TIME = 2;
    String COLUMN_VISIT_END_TIME= "end_time";
    int ID_VISIT_END_TIME = 3;
    String COLUMN_VISIT_DURATION= "duration";
    int ID_VISIT_DURATION= 4;
    String COLUMN_VISIT_IMAGE = "image";
    int ID_VISIT_IMAGE = 5;
    String COLUMN_VISIT_NOTES = "notes";
    int ID_VISIT_NOTES = 6;
    String COLUMN_VISIT_TAG= "tag";
    int ID_VISIT_TAG = 7;

    String CREATE_VISIT_TABLE = "CREATE TABLE " + TABLE_VISIT + "("
            + DbSchema._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbSchema.COLUMN_VISIT_PLACE_ID + " TEXT NOT NULL, "
            + DbSchema.COLUMN_VISIT_START_TIME + " TEXT, "
            + DbSchema.COLUMN_VISIT_END_TIME + " TEXT, "
            + DbSchema.COLUMN_VISIT_DURATION + " TEXT, "
            + DbSchema.COLUMN_VISIT_IMAGE + " TEXT, "
            + DbSchema.COLUMN_VISIT_NOTES + " TEXT, "
            + DbSchema.COLUMN_VISIT_TAG + " TEXT"
            + ")";

    String DELETE_VISIT_TABLE = "DROP TABLE IF EXISTS " + TABLE_VISIT;

}
*/
