package com.evoke.zenforce.model.database.dao;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;

/**
 * Created by spinninti on 11/20/2016.
 */
public abstract class BaseDAO {


    private static final String TAG = "BaseDAO";
    private Context mContext;

    public BaseDAO(Context context) {
        mContext = context;
    }

    public abstract Uri getURI();

    public abstract BaseEntityBean populate(Cursor cursor);


    public long insert(ContentValues values) {
        Log.v(TAG, " insert record uri :  " + getURI());
        try {
            Uri uri = mContext.getContentResolver().insert(getURI(), values);
            Log.v(TAG, "  inserted row id : " + ContentUris.parseId(uri));
            return  uri == null ? -1 : ContentUris.parseId(uri);
        } catch (Exception e) {
            Log.e(TAG, "insertion failed..");
            e.printStackTrace();
            return -1;
        }
    }

    public int update(ContentValues values, String selection, String[] selectionArgs) {
        try {
            int id = mContext.getContentResolver().update(getURI(), values, selection, selectionArgs);
            Log.v(TAG, "  update row id : " + id);
            return  id;
        } catch (Exception e) {
            Log.e(TAG, "update failed..");
            e.printStackTrace();
            return -1;
        }
    }


    public Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
       return mContext.getContentResolver().query(getURI(), projection, selection, selectionArgs, sortOrder);
    }

}
