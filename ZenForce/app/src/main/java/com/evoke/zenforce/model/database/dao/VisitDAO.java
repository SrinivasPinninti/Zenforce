package com.evoke.zenforce.model.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.evoke.zenforce.model.database.DatabaseHelper;
import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.DbConstants.VisitTable;
import com.evoke.zenforce.model.database.beanentity.BaseEntityBean;
import com.evoke.zenforce.model.database.beanentity.VisitBean;

/**
 * Created by spinninti on 11/23/2016.
 */
public class VisitDAO extends BaseDAO {

    private static final String TAG = "VisitDAO";
    private static Context mContext;

    private static VisitDAO visitDAO;

    protected SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public VisitDAO(Context context) {
        super(context);
        this.mContext = context;
        dbHelper = DatabaseHelper.getInstance(mContext);
        open();

    }

    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DatabaseHelper.getInstance(mContext);
        database = dbHelper.getWritableDatabase();
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
        bean.setPlaceId(cursor.getLong(VisitTable.ID_PLACE_ID));
        bean.setName(cursor.getString(VisitTable.ID_NAME));
        bean.setAddress(cursor.getString(VisitTable.ID_ADDRESS));
        bean.setStart_time(cursor.getLong(VisitTable.ID_START_TIME));
        bean.setEnd_time(cursor.getLong(VisitTable.ID_END_TIME));
        bean.setImagePath(cursor.getString(VisitTable.ID_IMG_PATH));
        bean.setImageCount(cursor.getInt(VisitTable.ID_IMG_COUNT));
        bean.setNote(cursor.getString(VisitTable.ID_NOTE));
        bean.setNoteCount(cursor.getInt(VisitTable.ID_NOTE_COUNT));
        bean.setTimeStamp(cursor.getLong(VisitTable.ID_TIMESTAMP));

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

    public int updateTimeStamp(BaseEntityBean bean, String selection, String[] selectionArgs) {
        Log.v(TAG, " update time stamp......");
        return  update(bean.getValues(), selection, selectionArgs);
    }


    /*public ArrayList<VisitBean> getVisits() {


        ArrayList<VisitBean> visits = new ArrayList<VisitBean>();
      *//*  String query = "SELECT " + VisitTable.COLUMN_NAME + ","
                + VisitTable.COLUMN_ADDRESS + "," + VisitTable.COLUMN_VISIT_PHOTO_ID	+ ","
                + DbConstants.PhotoTable.COLUMN_PATH + ","
                + DbConstants.PhotoTable.COLUMN_COMMENT + " FROM "
                + DataBaseHelper.EMPLOYEE_TABLE + " emp, "
                + DataBaseHelper.DEPARTMENT_TABLE + " dept WHERE emp."
                + DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + " = dept."
                + DataBaseHelper.ID_COLUMN;*//*




      *//*  String query = "SELECT " + VisitTable.COLUMN_NAME + ","
                + VisitTable.COLUMN_ADDRESS + "," + VisitTable.COLUMN_VISIT_PHOTO_ID	+ ","
                + DbConstants.PhotoTable.COLUMN_PATH + ","
                + DbConstants.PhotoTable.COLUMN_COMMENT + " FROM "
                + VisitTable.TABLE_NAME + " v INNER JOIN "
                + DbConstants.PhotoTable.TABLE_NAME + " p ON "
                + VisitTable.COLUMN_VISIT_PHOTO_ID + " = "
                + DbConstants.PhotoTable.COLUMN_ID;*//*


//        String query = "SELECT name,address,photo_id,path,comment FROM visit v INNER JOIN photo p ON visit.photo_id = photo._id";


         String query ="SELECT name, address, photo_id, path, comment FROM visit v INNER JOIN photo p ON v.photo_id = p._id";

        Log.d("query", query);
        Cursor cursor = database.rawQuery(query, null);

        Log.d(TAG, " cursor count : " + cursor.getCount());

        if (cursor != null && cursor.moveToFirst()) {


            do {
                VisitBean visit = new VisitBean();
                visit.setName(cursor.getString(0));
                visit.setAddress(cursor.getString(1));
                visit.setPhotoId(cursor.getLong(2));


                PhotoEntityBean photo = new PhotoEntityBean();

                photo.setPath(cursor.getString(3));

                photo.setComment(cursor.getString(4));

                visit.setPhoto(photo);

                visits.add(visit);

            }  while (cursor.moveToNext());
        }

        return visits;


    }*/



    public VisitBean getLastInsertedRow(String[] projection, String selection, String[] selectionArgs, String sortOrder) {


        Cursor cursor = query(projection, selection, selectionArgs, sortOrder);

        if (cursor != null && cursor.moveToFirst()) {

           VisitBean bean = (VisitBean) populate(cursor);

            return bean;
        }


        return null;


    }






}
