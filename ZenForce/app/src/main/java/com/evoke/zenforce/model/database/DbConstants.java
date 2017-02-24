package com.evoke.zenforce.model.database;

import android.net.Uri;
import android.provider.BaseColumns;


public final class DbConstants {


    public static final String AUTHORITY = "com.evoke.zenforce";





    public interface PlaceTable {


        String TABLE_NAME = "place";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.locationprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);


        String COLUMN_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_LOCATION_ID = "place_id";
        int ID_LOCATION_ID = 1;
        String COLUMN_NAME= "name";
        int ID_NAME = 2;
        String COLUMN_ADDRESS = "address";
        int ID_ADDRESS = 3;
        String COLUMN_PHONE = "phone";
        int ID_PHONE = 4;
        String COLUMN_WEBSITE = "website";
        int ID_WEBSITE = 5;

        String COLUMN_LAT = "lat";
        int ID_LAT = 6;
        String COLUMN_LNG = "lng";
        int ID_LNG = 7;

        String COLUMN_TIMESTAMP = "timestamp";
        int ID_TIMESTAMP = 8;

        String CREATE_PLACE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LOCATION_ID + " TEXT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_PHONE + " TEXT, "
                + COLUMN_WEBSITE + " TEXT, "
                + COLUMN_LAT + " TEXT, "
                + COLUMN_LNG + " TEXT, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
                + ")";

        String DELETE_PLACE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
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

        String COLUMN_PLACE_ID= "place_id";
        int ID_PLACE_ID = 1;

        String COLUMN_NAME= "name";
        int ID_NAME = 2;

        String COLUMN_ADDRESS = "address";
        int ID_ADDRESS = 3;

        String COLUMN_START_TIME= "start_time";
        int ID_START_TIME = 4;

        String COLUMN_END_TIME= "end_time";
        int ID_END_TIME = 5;

        String COLUMN_IMG_PATH= "image_path";
        int ID_IMG_PATH = 6;

        String COLUMN_IMG_COUNT= "image_count";
        int ID_IMG_COUNT = 7;

        String COLUMN_NOTE= "note";
        int ID_NOTE = 8;

        String COLUMN_NOTE_COUNT = "note_count";
        int ID_NOTE_COUNT = 9;

        String COLUMN_TIMESTAMP = "timestamp";
        int ID_TIMESTAMP = 10;

        String COLUMN_VISIT_PHOTO_ID= "photo_id";
        int ID_PHOTO_ID = 11;



        String CREATE_VISIT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PLACE_ID + " TEXT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_START_TIME + " INTEGER, "
                + COLUMN_END_TIME + " INTEGER, "
                + COLUMN_IMG_PATH + " TEXT, "
                + COLUMN_IMG_COUNT + " INTEGER, "
                + COLUMN_NOTE + " INTEGER, "
                + COLUMN_NOTE_COUNT + " INTEGER, "
                + COLUMN_TIMESTAMP + " INTEGER, "
                + COLUMN_VISIT_PHOTO_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_VISIT_PHOTO_ID + ") REFERENCES "
                + PhotoTable.TABLE_NAME + "(" + PhotoTable.COLUMN_ID + ") "
                + ")";

        String DELETE_VISIT_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    }


    /*public interface TimerTable {

        String TABLE_NAME = "timer";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.timerprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        String COLUMN_PHOTO_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_START_TIME = "start_time";
        int ID_START = 1;
        String COLUMN_END_TIME = "end_time";
        int ID_END = 2;
        String COLUMN_DURATION = "duration";
        int ID_DURATION = 3;
        String COLUMN_TIMESTAMP = "timestamp";
        int ID_TIMESTAMP = 4;
        String COLUMN_VISIT_ID = "visit_id";
        int ID_VISIT_ID = 5;


        String CREATE_TIMER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_PHOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_START_TIME + " INTEGER, "
                + COLUMN_END_TIME + " INTEGER, "
                + COLUMN_DURATION + " INTEGER, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))";


        String DELETE_TIMER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }*/

    public interface PhotoTable {

        String TABLE_NAME = "photo";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.photoprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        String COLUMN_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_PATH = "path";
        int ID_PATH = 1;
        String COLUMN_TAG = "tag";
        int ID_TAG = 2;
        String COLUMN_COMMENT = "comment";
        int ID_COMMENT = 3;
        String COLUMN_VISIT_ID = "visit_id";
        int ID_VISIT_ID = 4;
        String COLUMN_TIMESTAMP = "timestamp";
        int ID_TIMESTAMP = 5;



        String CREATE_PHOTO_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PATH + " TEXT, "
                + COLUMN_TAG + " TEXT, "
                + COLUMN_COMMENT + " TEXT, "
                + COLUMN_VISIT_ID + " TEXT, "
                + COLUMN_TIMESTAMP + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
                +")";

        String DELETE_PHOTO_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }


    public interface NoteTable{

        String TABLE_NAME = "note";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.noteprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        String COLUMN_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_NOTE = "note";
        int ID_NOTE = 1;
        String COLUMN_VISIT_ID = "visit_id";
        int ID_VISIT_ID = 2;
        String COLUMN_TIMESTAMP = "timestamp";
        int ID_TIMESTAMP = 3;


        String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOTE       + " TEXT, "
                + COLUMN_VISIT_ID   + " TEXT, "
                + COLUMN_TIMESTAMP  + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
                + ")";

        String DELETE_NOTE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


     public interface BarCodeTable {

        String TABLE_NAME = "barcode";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.barcodeprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        String COLUMN_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_NUMBER = "number";
        int ID_NUMBER = 1;
        String COLUMN_NAME = "name";
        int ID_NAME = 2;
        String COLUMN_EXP_DATE = "exp_date";
        int ID_EXP_DATE = 3;
        String COLUMN_PRICE = "price";
        int ID_PRICE = 4;
        String COLUMN_TYPE = "type";
        int ID_TYPE = 5;
         String COLUMN_WEIGHT = "weight";
         int ID_WEIGHT = 6;
         String COLUMN_TEMP = "temp";
         int ID_TEMP = 7;
         String COLUMN_TIMESTAMP = "timestamp";
         int ID_TIMESTAMP = 8;


        String CREATE_BARCODE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NUMBER     + " TEXT, "
                + COLUMN_NAME       + " TEXT, "
                + COLUMN_EXP_DATE   + " TEXT, "
                + COLUMN_PRICE      + " TEXT, "
                + COLUMN_TYPE       + " TEXT, "
                + COLUMN_WEIGHT     + " TEXT, "
                + COLUMN_TEMP       + " TEXT, "
                + COLUMN_TIMESTAMP  + " INTEGER NOT NULL DEFAULT (strftime('%s','now'))"
                + ")";


        String DELETE_BARCODE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public interface MessageTable {

        String TABLE_NAME = "Message";

        String CONTENT_AUTHORITY = "com.evoke.zenforce.model.database.provider.messageprovider";
        Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

        String COLUMN_ID = BaseColumns._ID;
        int ID_ID = 0;
        String COLUMN_USER = "user";
        int ID_USER = 1;
        String COLUMN_MSG = "message";
        int ID_MSG = 2;
        String COLUMN_IS_SENT = "isSent";
        int ID_IS_SENT = 3;
        String COLUMN_ITEM_ID = "item_id";
        int ID_ITEM_ID = 4;
        String COLUMN_CHANNEL = "channel";
        int ID_CHANNEL = 5;
        String COLUMN_TIME = "time";
        int ID_TIME = 6;


        String CREATE_MESSAGE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID             + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER           + " TEXT, "
                + COLUMN_MSG            + " TEXT, "
                + COLUMN_IS_SENT        + " INTEGER, "
                + COLUMN_ITEM_ID        + " INTEGER, "
                + COLUMN_CHANNEL        + " TEXT, "
                + COLUMN_TIME           + " TEXT"
                + ")";

        String DELETE_MESSAGE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
