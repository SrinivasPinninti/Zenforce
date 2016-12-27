package com.evoke.zenforce.model.database.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.evoke.zenforce.model.database.DatabaseHelper;

/**
 * Created by spinninti on 11/21/2016.
 */
public abstract class BaseProvider extends ContentProvider{

    private static final String TAG = "BaseProvider";
    private String tableName = getTableName();

    protected abstract String getTableName();


    private DatabaseHelper mDbHelper;

    @Override
    public boolean onCreate() {
        Log.v(TAG, "onCreate()...." + getTableName());
        mDbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.v(TAG, "query...." + uri.toString());
        Log.v(TAG, "selection...." + selection);
        Log.v(TAG, "selectionArgs...." + selectionArgs);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(tableName);
        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.v(TAG, "query completed....");
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v(TAG, "  insert into tableName...." + getTableName());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
            long id = db.insert(tableName, null, values);
        if (id > 0) {
            Log.v(TAG, " notify...");
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(uri, id);
        }
        throw new SQLException("Problem while inserting into uri: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.v(TAG, "Update : "+uri.toString()+" Selection: "+selection);
        SQLiteDatabase sqlDB = mDbHelper.getWritableDatabase();
        int rowsUpdated = 0;
        rowsUpdated = sqlDB.update(tableName, values, selection, selectionArgs);
        if(rowsUpdated <= 0){
            return rowsUpdated;
        }

        Log.d(TAG, "Update Complete : " + rowsUpdated);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
