package com.evoke.zenforce.model.database.beanentity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.evoke.zenforce.model.database.DbConstants.VisitTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spinninti on 11/23/2016.
 */
public class VisitBean extends BaseEntityBean implements Parcelable{

    private long    _ID;

    private long   placeId;
    private String name;
    private String address;
    private long   start_time;
    private long   end_time;
    private String imagePath;
    private int    imageCount;
    private String note;
    private int    noteCount;


    private PhotoEntityBean photoEntityBean;

    private List<PhotoEntityBean> photos = new ArrayList<>();

    private long   timeStamp;
    private long photoId;



    private PhotoEntityBean photo;

    private ContentValues values;



    public VisitBean() {
        values = new ContentValues();
    }


    protected VisitBean(Parcel in) {
        _ID = in.readLong();
        timeStamp = in.readLong();
        values = in.readParcelable(ContentValues.class.getClassLoader());
    }

    public static final Creator<VisitBean> CREATOR = new Creator<VisitBean>() {
        @Override
        public VisitBean createFromParcel(Parcel in) {
            return new VisitBean(in);
        }

        @Override
        public VisitBean[] newArray(int size) {
            return new VisitBean[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_ID);
        dest.writeLong(timeStamp);
        dest.writeParcelable(values, flags);
    }



    public long get_ID() {
        Log.d("DAO", "values : " + values);
        return values.getAsLong(VisitTable.COLUMN_ID);
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
        values.put(VisitTable.COLUMN_ID, _ID);
    }

    public long getPlaceId() {
        return values.getAsLong(VisitTable.COLUMN_PLACE_ID);
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
        values.put(VisitTable.COLUMN_PLACE_ID, placeId);
    }

    public String getName() {
        return values.getAsString(VisitTable.COLUMN_NAME);
    }

    public void setName(String name) {
        this.name = name;
        values.put(VisitTable.COLUMN_NAME, name);
    }

    public String getAddress() {
        return values.getAsString(VisitTable.COLUMN_ADDRESS);
    }

    public void setAddress(String address) {
        this.address = address;
        values.put(VisitTable.COLUMN_ADDRESS, address);
    }




    public long getStart_time() {
        return values.getAsLong(VisitTable.COLUMN_START_TIME);
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
        values.put(VisitTable.COLUMN_START_TIME, start_time);
    }

    public long getEnd_time() {
        return values.getAsLong(VisitTable.COLUMN_END_TIME);
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
        values.put(VisitTable.COLUMN_END_TIME, end_time);
    }


    public String getImagePath() {
        return values.getAsString(VisitTable.COLUMN_IMG_PATH);
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        values.put(VisitTable.COLUMN_IMG_PATH, imagePath);
    }

    public int getImageCount() {
        return values.getAsInteger(VisitTable.COLUMN_IMG_COUNT);
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
        values.put(VisitTable.COLUMN_IMG_COUNT, imageCount);
    }

    public String getNote() {
        return values.getAsString(VisitTable.COLUMN_NOTE);
    }

    public void setNote(String note) {
        this.note = note;
        values.put(VisitTable.COLUMN_NOTE, note);
    }

    public int getNoteCount() {
        return values.getAsInteger(VisitTable.COLUMN_NOTE_COUNT);
    }

    public void setNoteCount(int noteCount) {
        this.noteCount = noteCount;
        values.put(VisitTable.COLUMN_NOTE_COUNT, noteCount);
    }

    public PhotoEntityBean getPhoto() {
        return photoEntityBean;
    }

    public void setPhoto(PhotoEntityBean photoEntityBean) {
        this.photoEntityBean = photoEntityBean;
        photos.add(photoEntityBean);
    }


    public void setPhotoId(long photoId) {
        this.photoId = photoId;
        values.put(VisitTable.COLUMN_VISIT_PHOTO_ID, photoId);
    }

    public List<PhotoEntityBean> getPhotoList() {
        return photos;
    }

    public long getTimeStamp() {
        return values.getAsLong(VisitTable.COLUMN_TIMESTAMP);
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        values.put(VisitTable.COLUMN_TIMESTAMP, timeStamp);
    }





    @Override
    public ContentValues getValues() {
        return values;
    }



}