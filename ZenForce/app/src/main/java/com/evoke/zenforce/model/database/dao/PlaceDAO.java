/*
package com.evoke.zenforce.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;
import com.evoke.zenforce.model.database.beanentity.PlaceEntityBean;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by spinninti on 11/20/2016.
 *//*

public class PlaceDAO extends BaseDAO {


    private static final String TAG = "PlaceDAO";
    private static PlaceDAO placeDAO = null;

    private static Context mContext;

    public PlaceDAO(Context context) {
        super(context);
    }


    public static PlaceDAO getSingletonInstance(Context context) {
        mContext = context;
        if (placeDAO == null) {
            placeDAO = new PlaceDAO(context);
        }
        return placeDAO;
    }

    @Override
    public Uri getURI() {
        return DbConstants.PlaceTable.CONTENT_URI;
    }

    public long insert(PlaceEntityBean bean) {
       return  insert(bean.getValues());
    }

    public List<BaseEntityBean> getAllPlaces(String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;
        ArrayList<BaseEntityBean> arrayList = new ArrayList<BaseEntityBean>();

        try {
            cursor = query(projection, selection, selectionArgs, sortOrder);
            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    do {
                        arrayList.add(populate(cursor));
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        Log.d("BaseDAO", "readAll() arrayList count " + arrayList.size());
        return arrayList;
    }

    @Override
    public BaseEntityBean populate(Cursor cursor) {
        PlaceEntityBean bean = new PlaceEntityBean();
        bean.set_ID(cursor.getLong(DbConstants.PlaceTable.ID_ID));
        bean.setName(cursor.getString(DbConstants.PlaceTable.ID_NAME));
        bean.setAddress(cursor.getString(DbConstants.PlaceTable.ID_ADDRESS));
        bean.setPlaceId(cursor.getString(DbConstants.PlaceTable.ID_PLACE_ID));
        bean.setPhone(cursor.getString(DbConstants.PlaceTable.ID_PHONE));
        bean.setUrl(cursor.getString(DbConstants.PlaceTable.ID_WEBSITE));
        bean.setCreated_at(cursor.getString(DbConstants.PlaceTable.ID_CREATED_AT));

        return bean;
    }
}
*/
