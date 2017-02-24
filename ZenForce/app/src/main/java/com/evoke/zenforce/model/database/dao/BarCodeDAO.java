package com.evoke.zenforce.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.beanentity.BarCodeEntityBean;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;

/**
 * Created by spinninti on 12/1/2016.
 */
public class BarCodeDAO extends BaseDAO{

    private static final String TAG = "BarCodeDAO";
    private static BarCodeDAO barCodeDAO;
    private static Context mContext;

    public BarCodeDAO(Context context) {
        super(context);
    }


    public static BarCodeDAO getSingletonInstance(Context context) {
        mContext = context;
        if (barCodeDAO == null) {
            barCodeDAO = new BarCodeDAO(context);
        }
        return barCodeDAO;
    }

    @Override
    public Uri getURI() {
        return DbConstants.BarCodeTable.CONTENT_URI;
    }

    @Override
    public BaseEntityBean populate(Cursor cursor) {
        BarCodeEntityBean bean = new BarCodeEntityBean();
        bean.set_ID(cursor.getLong(DbConstants.BarCodeTable.ID_ID));
        bean.setNumber(cursor.getString(DbConstants.BarCodeTable.ID_NUMBER));
        bean.setName(cursor.getString(DbConstants.BarCodeTable.ID_NAME));
        bean.setExp(cursor.getString(DbConstants.BarCodeTable.ID_EXP_DATE));
        bean.setPrice(cursor.getString(DbConstants.BarCodeTable.ID_PRICE));
        bean.setType(cursor.getString(DbConstants.BarCodeTable.ID_TYPE));
        bean.setWeight(cursor.getString(DbConstants.BarCodeTable.ID_WEIGHT));
        bean.setTemp(cursor.getString(DbConstants.BarCodeTable.ID_TEMP));
        bean.setTimeStamp(cursor.getInt(DbConstants.BarCodeTable.ID_TIMESTAMP));
        return bean;
    }

    public long insert(BarCodeEntityBean bean) {
        Log.v(TAG, " insert note.....");
        return insert(bean.getValues());
    }

    public BarCodeEntityBean getData(String number) {

        String selection =  DbConstants.BarCodeTable.COLUMN_NUMBER + " = ?";
        String[] selectionArgs = new String[] { number };

        Cursor cursor = query(null, selection, selectionArgs, null);

        if (cursor != null && cursor.moveToFirst()) {

            BarCodeEntityBean bean = (BarCodeEntityBean) populate(cursor);

            return bean;

        }

        return null;

    }

}
