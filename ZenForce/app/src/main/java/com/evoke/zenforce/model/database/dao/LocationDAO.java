package com.evoke.zenforce.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;
import com.evoke.zenforce.model.database.beanentity.LocationBean;

/**
 * Created by spinninti on 11/23/2016.
 */
public class LocationDAO extends BaseDAO {

    private static final String TAG = "LocationDAO";
    private static Context mContext;

    private static LocationDAO locationDAO;

    public LocationDAO(Context context) {
        super(context);
    }

    @Override
    public Uri getURI() {
        return DbConstants.LocationTable.CONTENT_URI;
    }


    public static LocationDAO getSingletonInstance(Context context) {
        mContext = context;
        if (locationDAO == null) {
            locationDAO = new LocationDAO(context);
        }
        return locationDAO;
    }

    @Override
    public BaseEntityBean populate(Cursor cursor) {
        LocationBean bean = new LocationBean();
        bean.set_ID(cursor.getLong(DbConstants.LocationTable.ID_ID));
        bean.setLocationId(cursor.getString(DbConstants.LocationTable.ID_LOCATION_ID));
        bean.setName(cursor.getString(DbConstants.LocationTable.ID_NAME));
        bean.setAddress(cursor.getString(DbConstants.LocationTable.ID_ADDRESS));
        bean.setPhone(cursor.getString(DbConstants.LocationTable.ID_PHONE));
        bean.setWebsite(cursor.getString(DbConstants.LocationTable.ID_WEBSITE));
        bean.setTimeStamp(cursor.getInt(DbConstants.LocationTable.ID_TIMESTAMP));

        return bean;
    }

    public long insert(LocationBean bean) {
        Log.v(TAG, " insert record....");
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

    public int update(LocationBean bean, String selection, String[] selectionArgs) {
        Log.v(TAG, " update record....");
        return  update(bean.getValues(), selection, selectionArgs);
    }
}
