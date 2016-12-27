package com.evoke.zenforce.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.evoke.zenforce.model.database.DatabaseHelper;
import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.DbConstants.VisitTable;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;
import com.evoke.zenforce.model.database.beanentity.VisitBean;

import java.util.ArrayList;

/**
 * Created by spinninti on 11/23/2016.
 */
public class VisitDAO extends BaseDAO {

    private static final String TAG = "VisitDAO";
    private static Context mContext;

    private static VisitDAO visitDAO;

    public VisitDAO(Context context) {
        super(context);
    }

    @Override
    public Uri getURI() {
        return DbConstants.VisitTable.CONTENT_URI;
    }


    public static VisitDAO getSingletonInstance(Context context) {
        mContext = context;
        if (visitDAO == null) {
            visitDAO = new VisitDAO(context);
        }
        return visitDAO;
    }

    @Override
    public BaseEntityBean populate(Cursor cursor) {
        VisitBean bean = new VisitBean();
        bean.set_ID(cursor.getLong(VisitTable.ID_ID));
        bean.setName(cursor.getString(VisitTable.ID_NAME));
        bean.setAddress(cursor.getString(VisitTable.ID_ADDRESS));
        bean.setPhone(cursor.getString(VisitTable.ID_PHONE));
        bean.setWebsite(cursor.getString(VisitTable.ID_WEBSITE));
        bean.setLocationId(cursor.getString(VisitTable.ID_LOCATION_ID));
        bean.setLocationLat(cursor.getString(VisitTable.ID_LOCATION_LAT));
        bean.setLocationLng(cursor.getString(VisitTable.ID_LOCATION_LNG));
        bean.setTimeStamp(cursor.getInt(VisitTable.ID_TIMESTAMP));

        return bean;
    }

    public long insert(BaseEntityBean bean) {
        Log.v(TAG, " insert record....");
        return  insert(bean.getValues());
    }



    public int update(BaseEntityBean bean, String selection, String[] selectionArgs) {
        Log.v(TAG, " update photoId record....");
        return  update(bean.getValues(), selection, selectionArgs);
    }





    public ArrayList<BaseEntityBean> getHistoryByVisit(long visitId) {

        Log.v(TAG, " getHistoryByVisit " + visitId);
        DatabaseHelper mDbHelper = new DatabaseHelper(mContext);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String rawQuery = "SELECT name FROM " + DbConstants.VisitTable.TABLE_NAME + " INNER JOIN " + DbConstants.PhotoTable.TABLE_NAME
                + " ON " + VisitTable.COLUMN_ID + " = " + DbConstants.PhotoTable.COLUMN_VISIT_ID;
//                + " WHERE " + DbConstants.VisitTable.COLUMN_ID + " = " +  8;



//        SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate
//        FROM Orders
//        INNER JOIN Customers
//        ON Orders.CustomerID=Customers.CustomerID;


//        String MY_QUERY = "SELECT * FROM photo INNER JOIN note ON " +  DbConstants.PhotoTable.COLUMN_VISIT_ID + " = " +  DbConstants.NoteTable.COLUMN_VISIT_ID + " WHERE " +
//                DbConstants.PhotoTable.COLUMN_VISIT_ID + " = " + visitId;


        Cursor cursor = db.rawQuery(rawQuery, null);

        Log.d(TAG, " History records count : " + cursor.getCount());

        if (cursor != null && cursor.moveToFirst()) {



            do {



            }while (cursor.moveToNext());

        }


        return null;
    }
}
