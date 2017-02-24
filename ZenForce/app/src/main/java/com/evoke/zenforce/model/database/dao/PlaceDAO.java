package com.evoke.zenforce.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;
import com.evoke.zenforce.model.database.beanentity.PlaceBean;

/**
 * Created by spinninti on 11/23/2016.
 */
public class PlaceDAO extends BaseDAO {

    private static final String TAG = "PlaceDAO";
    private static Context mContext;

    private static PlaceDAO placeDAO;

    public PlaceDAO(Context context) {
        super(context);
    }

    @Override
    public Uri getURI() {
        return DbConstants.PlaceTable.CONTENT_URI;
    }


    public static PlaceDAO getSingletonInstance(Context context) {
        mContext = context;
        if (placeDAO == null) {
            placeDAO = new PlaceDAO(context);
        }
        return placeDAO;
    }

    @Override
    public BaseEntityBean populate(Cursor cursor) {
        PlaceBean bean = new PlaceBean();
        bean.set_ID(cursor.getLong(DbConstants.PlaceTable.ID_ID));
        bean.setPlaceId(cursor.getString(DbConstants.PlaceTable.ID_LOCATION_ID));
        bean.setName(cursor.getString(DbConstants.PlaceTable.ID_NAME));
        bean.setAddress(cursor.getString(DbConstants.PlaceTable.ID_ADDRESS));
        bean.setPhone(cursor.getString(DbConstants.PlaceTable.ID_PHONE));
        bean.setWebsite(cursor.getString(DbConstants.PlaceTable.ID_WEBSITE));
        bean.setLat(cursor.getString(DbConstants.PlaceTable.ID_LAT));
        bean.setLng(cursor.getString(DbConstants.PlaceTable.ID_LNG));
        bean.setTimeStamp(cursor.getInt(DbConstants.PlaceTable.ID_TIMESTAMP));

        return bean;
    }

    public long insert(PlaceBean bean) {
        Log.v(TAG, " insert place....");
        return  insert(bean.getValues());
    }

    /*public List<BaseEntityBean> getAllPlaces(String[] projection, String selection, String[] selectionArgs, String sortOrder) {

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
    }*/

    public int update(PlaceBean bean, String selection, String[] selectionArgs) {
        Log.v(TAG, " update record....");
        return  update(bean.getValues(), selection, selectionArgs);
    }
}
