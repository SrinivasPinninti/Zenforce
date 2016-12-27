package com.evoke.zenforce.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;
import com.evoke.zenforce.model.database.beanentity.PhotoEntityBean;

/**
 * Created by spinninti on 11/29/2016.
 */
public class PhotoDAO extends BaseDAO{

    private static final String TAG = "PhotoDAO";

    private static PhotoDAO sPhotoDAO;
    private static Context mContext;




    public PhotoDAO(Context context) {
        super(context);
    }

    public static PhotoDAO getSingletonInstance(Context context) {
        mContext = context;
        if (sPhotoDAO == null) {
            sPhotoDAO = new PhotoDAO(context);
        }

        return sPhotoDAO;

    }

    @Override
    public Uri getURI() {
        return DbConstants.PhotoTable.CONTENT_URI;
    }

    public long insert(BaseEntityBean bean) {
        Log.v(TAG, " insert photo.....");
        return insert(bean.getValues());
    }

    @Override
    public BaseEntityBean populate(Cursor cursor) {
        PhotoEntityBean bean = new PhotoEntityBean();
        bean.set_ID(cursor.getLong(DbConstants.PhotoTable.ID_ID));
        bean.setVisitId(cursor.getLong(DbConstants.PhotoTable.ID_VISIT_ID));
        bean.setPath(cursor.getString(DbConstants.PhotoTable.ID_PHOTO_PATH));
        bean.setTag(cursor.getString(DbConstants.PhotoTable.ID_PHOTO_TAG));
        bean.setTimeStamp(cursor.getInt(DbConstants.PhotoTable.ID_TIMESTAMP));
        return bean;
    }

    public int update(PhotoEntityBean bean, String selection, String[] selectionArgs) {
        Log.v(TAG, " update record....");
        return  update(bean.getValues(), selection, selectionArgs);
    }
}
