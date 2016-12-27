package com.evoke.zenforce.model.database;

import android.net.Uri;
import android.provider.BaseColumns;


public final class DbConstants {


    public static final String AUTHORITY = "com.evoke.zenforce";

    public interface VisitTable1 {


        String TABLE_NAME = "visit";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.visitprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        String COLUMN_ID = BaseColumns._ID;
        int ID_VISIT_ID = 0;
        String COLUMN_VISIT_NAME = "name";
        int ID_VISIT_NAME = 1;
        String COLUMN_VISIT_ADDRESS = "address";
        int ID_VISIT_ADDRESS = 2;
        String COLUMN_VISIT_LOCATION_ID = "location_id";
        int ID_VISIT_LOCATION_ID = 3;
        String COLUMN_VISIT_PHONE = "phone";
        int ID_VISIT_PHONE = 4;
        String COLUMN_VISIT_WEBSITE = "website";
        int ID_VISIT_WEBSITE = 5;
        String COLUMN_VISIT_START_TIME = "start_time";
        int ID_VISIT_START_TIME = 6;
        String COLUMN_VISIT_END_TIME= "end_time";
        int ID_VISIT_END_TIME = 7;
        String COLUMN_VISIT_CREATED_AT = "created_at";
        int ID_VISIT_CREATED_AT = 8;

        String CREATE_VISIT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_VISIT_NAME + " TEXT NOT NULL, "
                + COLUMN_VISIT_ADDRESS + " TEXT, "
                + COLUMN_VISIT_LOCATION_ID + " TEXT, "
                + COLUMN_VISIT_PHONE + " TEXT, "
                + COLUMN_VISIT_WEBSITE + " TEXT, "
                + COLUMN_VISIT_START_TIME + " TEXT, "
                + COLUMN_VISIT_END_TIME + " TEXT, "
                + COLUMN_VISIT_CREATED_AT + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
                + ")";

        String DELETE_VISIT_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        String DML_WHERE_ID_CLAUSE = "_id = ?";

        String DEFAULT_TBL_ITEMS_SORT_ORDER = "name ASC";

    }




    public interface LocationTable {


        String TABLE_NAME = "location";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.locationprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);


        String COLUMN_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_LOCATION_ID = "location_id";
        int ID_LOCATION_ID = 1;
        String COLUMN_NAME= "name";
        int ID_NAME = 2;
        String COLUMN_ADDRESS = "address";
        int ID_ADDRESS = 3;
        String COLUMN_PHONE = "phone";
        int ID_PHONE = 4;
        String COLUMN_WEBSITE = "website";
        int ID_WEBSITE = 5;
        String COLUMN_TIMESTAMP = "timestamp";
        int ID_TIMESTAMP = 6;

        String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LOCATION_ID + " TEXT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_PHONE + " TEXT, "
                + COLUMN_WEBSITE + " TEXT, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
                + ")";

        String DELETE_LOCATION_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }




   /* public interface VisitTable {

        String TABLE_NAME = "visit";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.visitprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        String COLUMN_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_LOCATION_ID = "location_id";
        int ID_LOCATION_ID = 1;
        String COLUMN_START_TIME= "start";
        int ID_START_TIME = 2;
        String COLUMN_END_TIME= "end";
        int ID_END_TIME = 3;
        String COLUMN_DURATION= "duration";
        int ID_DURATION = 4;
        String COLUMN_PHOTO= "photo";
        int ID_PHOTO = 5;
        String COLUMN_NOTE= "note";
        int ID_NOTE = 6;
        String COLUMN_TIMESTAMP = "timestamp";
        int ID_TIMESTAMP = 7;


        String CREATE_VISIT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LOCATION_ID + " TEXT, "
                + COLUMN_START_TIME + " INTEGER, "
                + COLUMN_END_TIME + " INTEGER, "
                + COLUMN_DURATION + " INTEGER, "
                + COLUMN_PHOTO + " TEXT, "
                + COLUMN_NOTE + " TEXT, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
                + ")";

        String DELETE_VISIT_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }*/

    public interface VisitTable {

        String TABLE_NAME = "visit";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.visitprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        String COLUMN_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_NAME= "name";
        int ID_NAME = 1;
        String COLUMN_ADDRESS= "address";
        int ID_ADDRESS = 2;
        String COLUMN_PHONE= "phone";
        int ID_PHONE = 3;
        String COLUMN_WEBSITE= "website";
        int ID_WEBSITE = 4;
        String COLUMN_LOCATION_ID = "location_id";
        int ID_LOCATION_ID = 5;
        String COLUMN_LOCATION_LAT = "lat";
        int ID_LOCATION_LAT = 6;
        String COLUMN_LOCATION_LNG = "lng";
        int ID_LOCATION_LNG = 7;
        String COLUMN_TIMESTAMP = "timestamp";
        int ID_TIMESTAMP = 8;


        String CREATE_VISIT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_PHONE + " TEXT, "
                + COLUMN_WEBSITE + " TEXT, "
                + COLUMN_LOCATION_ID + " TEXT, "
                + COLUMN_LOCATION_LAT + " TEXT, "
                + COLUMN_LOCATION_LNG + " TEXT, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
                + ")";

        String DELETE_VISIT_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }


    public interface TimerTable {

        String TABLE_NAME = "timer";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.timerprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        String COLUMN_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_VISIT_ID = "visit_id";
        int ID_VISIT_ID = 1;
        String COLUMN_START_TIME = "start_time";
        int ID_START = 2;
        String COLUMN_END_TIME = "end_time";
        int ID_END = 3;
        String COLUMN_DURATION = "duration";
        int ID_DURATION = 4;
        String COLUMN_TIMESTAMP = "timestamp";
        int ID_TIMESTAMP = 5;


        String CREATE_TIMER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_VISIT_ID + " INTEGER, "
                + COLUMN_START_TIME + " INTEGER, "
                + COLUMN_END_TIME + " INTEGER, "
                + COLUMN_DURATION + " INTEGER, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
                + ")";

        String DELETE_TIMER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public interface PhotoTable {

        String TABLE_NAME = "photo";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.photoprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        String COLUMN_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_VISIT_ID = "visit_id";
        int ID_VISIT_ID = 1;
        String COLUMN_PHOTO_PATH = "path";
        int ID_PHOTO_PATH = 2;
        String COLUMN_PHOTO_TAG = "tag";
        int ID_PHOTO_TAG = 3;
        String COLUMN_TIMESTAMP = "timestamp";
        int ID_TIMESTAMP = 4;


        String CREATE_PHOTO_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_VISIT_ID + " INTEGER, "
                + COLUMN_PHOTO_PATH + " TEXT, "
                + COLUMN_PHOTO_TAG + " TEXT, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
                + ")";

        String DELETE_PHOTO_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }


    public interface NoteTable{

        String TABLE_NAME = "note";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.noteprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        String COLUMN_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_VISIT_ID = "visit_id";
        int ID_VISIT_ID = 1;
        String COLUMN_NOTE = "note";
        int ID_NOTE = 2;
        String COLUMN_TIMESTAMP = "timestamp";
        int ID_TIMESTAMP = 3;

        String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_VISIT_ID + " INTEGER, "
                + COLUMN_NOTE     + " TEXT, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
                + ")";

        String DELETE_NOTE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
