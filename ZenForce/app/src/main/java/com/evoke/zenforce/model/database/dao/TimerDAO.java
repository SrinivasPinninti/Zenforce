/*
package com.evoke.zenforce.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;
import com.evoke.zenforce.model.database.beanentity.TimerEntityBean;

*/
/**
 * Created by spinninti on 12/1/2016.
 *//*

public class TimerDAO extends BaseDAO{

    private static final String TAG = "TimerDAO";
    private static TimerDAO sTimerDAO;
    private static Context mContext;

    public TimerDAO(Context context) {
        super(context);
    }


    public static TimerDAO getSingletonInstance(Context context) {
        mContext = context;
        if (sTimerDAO == null) {
            sTimerDAO = new TimerDAO(context);
        }
        return sTimerDAO;
    }

    @Override
    public Uri getURI() {
        return DbConstants.TimerTable.CONTENT_URI;
    }

    @Override
    public BaseEntityBean populate(Cursor cursor) {
        TimerEntityBean bean = new TimerEntityBean();
        bean.set_ID(cursor.getLong(DbConstants.TimerTable.ID_ID));
        bean.setVisitId(cursor.getLong(DbConstants.TimerTable.ID_VISIT_ID));
        bean.setStartTime(cursor.getLong(DbConstants.TimerTable.ID_START));
        bean.setEndTime(cursor.getLong(DbConstants.TimerTable.ID_END));
        bean.setDuration(cursor.getLong(DbConstants.TimerTable.ID_DURATION));
        bean.setTimeStamp(cursor.getInt(DbConstants.NoteTable.ID_TIMESTAMP));
        return bean;
    }

    public long insert(BaseEntityBean bean) {
        Log.v(TAG, " insert note.....");
        return insert(bean.getValues());
    }

    public int update(BaseEntityBean bean, String selection, String[] selectionArgs) {
        Log.v(TAG, " update record....");
        return  update(bean.getValues(), selection, selectionArgs);
    }

    public int updateEndTime(BaseEntityBean bean, String selection, String[] selectionArgs) {
        Log.v(TAG, " update record....");
        return  update(bean.getValues(), selection, selectionArgs);
    }

    public long getStartTime(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = query(projection, selection, selectionArgs, sortOrder);

        if (cursor != null && cursor.moveToFirst()) {

            do {

               TimerEntityBean bean = (TimerEntityBean) populate(cursor);
                Log.v(TAG, " StartTime from db : "+bean.getStartTime());
                return bean.getStartTime();

            } while (cursor.moveToNext());
        }
        Log.v(TAG, " return 0....");
        return 0;

    }
}
*/
