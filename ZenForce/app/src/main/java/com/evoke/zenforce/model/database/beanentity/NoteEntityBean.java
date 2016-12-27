package com.evoke.zenforce.model.database.beanentity;

import android.content.ContentValues;

import com.evoke.zenforce.model.database.DbConstants;

/**
 * Created by spinninti on 11/29/2016.
 */
public class NoteEntityBean extends BaseEntityBean {

    private long _ID;
    private long visitId;
    private String note;
    private int timeStamp;

    private ContentValues values;


    public NoteEntityBean() {

        values = new ContentValues();

    }

    public long get_ID() {
        return values.getAsLong(DbConstants.NoteTable.COLUMN_ID);
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
        values.put(DbConstants.NoteTable.COLUMN_ID, _ID);
    }


    public long getVisitId() {
        return values.getAsInteger(DbConstants.NoteTable.COLUMN_VISIT_ID);
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
        values.put(DbConstants.NoteTable.COLUMN_VISIT_ID, visitId);
    }



    public String getNote() {
        return values.getAsString(DbConstants.NoteTable.COLUMN_NOTE);
    }

    public void setNote(String note) {
        this.note = note;
        values.put(DbConstants.NoteTable.COLUMN_NOTE, note);
    }



    public int getTimeStamp() {
        return values.getAsInteger(DbConstants.NoteTable.COLUMN_TIMESTAMP);
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
        values.put(DbConstants.NoteTable.COLUMN_TIMESTAMP, timeStamp);
    }

    @Override
    public ContentValues getValues() {
        return values;
    }
}
