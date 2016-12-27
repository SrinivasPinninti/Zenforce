package com.evoke.zenforce.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;
import com.evoke.zenforce.model.database.beanentity.NoteEntityBean;

/**
 * Created by spinninti on 12/1/2016.
 */
public class NoteDAO extends BaseDAO{

    private static final String TAG = "NoteDAO";
    private static NoteDAO sNoteDAO;
    private static Context mContext;

    public NoteDAO(Context context) {
        super(context);
    }


    public static NoteDAO getSingletonInstance(Context context) {
        mContext = context;
        if (sNoteDAO == null) {
            sNoteDAO = new NoteDAO(context);
        }
        return sNoteDAO;
    }

    @Override
    public Uri getURI() {
        return DbConstants.NoteTable.CONTENT_URI;
    }

    @Override
    public BaseEntityBean populate(Cursor cursor) {
        NoteEntityBean bean = new NoteEntityBean();
        bean.set_ID(cursor.getLong(DbConstants.NoteTable.ID_ID));
        bean.setVisitId(cursor.getLong(DbConstants.NoteTable.ID_VISIT_ID));
        bean.setNote(cursor.getString(DbConstants.NoteTable.ID_NOTE));
        bean.setTimeStamp(cursor.getInt(DbConstants.NoteTable.ID_TIMESTAMP));
        return bean;
    }

    public long insert(NoteEntityBean bean) {
        Log.v(TAG, " insert note.....");
        return insert(bean.getValues());
    }

    public int update(NoteEntityBean bean, String selection, String[] selectionArgs) {
        Log.v(TAG, " update record....");
        return  update(bean.getValues(), selection, selectionArgs);
    }
}
