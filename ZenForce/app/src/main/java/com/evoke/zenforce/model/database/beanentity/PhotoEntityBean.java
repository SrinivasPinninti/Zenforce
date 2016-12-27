package com.evoke.zenforce.model.database.beanentity;

import android.content.ContentValues;

import com.evoke.zenforce.model.database.DbConstants;
import com.evoke.zenforce.model.database.DbConstants.PhotoTable;

/**
 * Created by spinninti on 11/29/2016.
 */
public class PhotoEntityBean extends BaseEntityBean{

    private long _ID;
    private long visitId;
    private String path;
    private String tag;
    private int timeStamp;



    private ContentValues values;


    public PhotoEntityBean() {
        values = new ContentValues();
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public long getVisitId() {
        return values.getAsInteger(DbConstants.PhotoTable.COLUMN_VISIT_ID);
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
        values.put(DbConstants.PhotoTable.COLUMN_VISIT_ID, visitId);
    }

    public String getPath() {
        return values.getAsString(PhotoTable.COLUMN_PHOTO_PATH);
    }

    public void setPath(String path) {
        this.path = path;
        values.put(PhotoTable.COLUMN_PHOTO_PATH, path);
    }

    public String getTag() {
        return values.getAsString(PhotoTable.COLUMN_PHOTO_TAG);
    }

    public void setTag(String tag) {
        this.tag = tag;
        values.put(PhotoTable.COLUMN_PHOTO_TAG, tag);
    }


    public int getTimeStamp() {
        return values.getAsInteger(DbConstants.PhotoTable.COLUMN_TIMESTAMP);
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
        values.put(DbConstants.PhotoTable.COLUMN_TIMESTAMP, timeStamp);
    }

    @Override
    public ContentValues getValues() {
        return values;
    }
}
