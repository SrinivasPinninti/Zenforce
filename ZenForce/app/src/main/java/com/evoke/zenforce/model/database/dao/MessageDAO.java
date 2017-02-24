package com.evoke.zenforce.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;
import com.evoke.zenforce.model.database.beanentity.MessageEntityBean;

/**
 * Created by spinninti on 12/1/2016.
 */
public class MessageDAO extends BaseDAO{

    private static final String TAG = "MessageDAO";
    private static MessageDAO barCodeDAO;
    private static Context mContext;

    public MessageDAO(Context context) {
        super(context);
    }


    public static MessageDAO getSingletonInstance(Context context) {
        mContext = context;
        if (barCodeDAO == null) {
            barCodeDAO = new MessageDAO(context);
        }
        return barCodeDAO;
    }

    @Override
    public Uri getURI() {
        return DbConstants.MessageTable.CONTENT_URI;
    }

    @Override
    public BaseEntityBean populate(Cursor cursor) {
        MessageEntityBean bean = new MessageEntityBean();
        bean.set_ID(cursor.getLong(DbConstants.MessageTable.ID_ID));
        bean.setUser(cursor.getString(DbConstants.MessageTable.ID_USER));
        bean.setMessage(cursor.getString(DbConstants.MessageTable.ID_MSG));
        bean.setIsSent(cursor.getInt(DbConstants.MessageTable.ID_IS_SENT));
        bean.setItem_id(cursor.getLong(DbConstants.MessageTable.ID_ITEM_ID));
        bean.setChannel(cursor.getString(DbConstants.MessageTable.ID_CHANNEL));
        bean.setTime(cursor.getString(DbConstants.MessageTable.ID_TIME));
        return bean;
    }

    public long insert(MessageEntityBean bean) {
        Log.v(TAG, " insert message.....");
        return insert(bean.getValues());
    }

    public int update(MessageEntityBean bean, String selection, String[] selectionArgs) {
        Log.v(TAG, " offline message state to : " + bean.getIsSent());
        return update(bean.getValues(), selection, selectionArgs);
    }

}
