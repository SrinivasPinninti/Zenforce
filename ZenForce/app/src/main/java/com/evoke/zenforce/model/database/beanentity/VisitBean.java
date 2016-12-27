package com.evoke.zenforce.model.database.beanentity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.evoke.zenforce.model.database.DbConstants.VisitTable;

/**
 * Created by spinninti on 11/23/2016.
 */
public class VisitBean extends BaseEntityBean implements Parcelable{

    private long    _ID;

    private String  name;
    private String  address;
    private String  phone;
    private String  website;
    private String  locationId;
    private String  locationLat;
    private String  locationLng;
    private int     timeStamp;

    private ContentValues values;




    public VisitBean() {
        values = new ContentValues();
    }


    protected VisitBean(Parcel in) {
        _ID = in.readLong();
        name = in.readString();
        address = in.readString();
        locationId = in.readString();
        timeStamp = in.readInt();
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
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(locationId);
        dest.writeInt(timeStamp);
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

    public String getPhone() {
        return values.getAsString(VisitTable.COLUMN_PHONE);
    }

    public void setPhone(String phone) {
        this.phone = phone;
        values.put(VisitTable.COLUMN_PHONE, phone);
    }

    public String getWebsite() {
        return values.getAsString(VisitTable.COLUMN_WEBSITE);
    }

    public void setWebsite(String website) {
        this.website = website;
        values.put(VisitTable.COLUMN_WEBSITE, website);
    }

    public String getLocationId() {
        return values.getAsString(VisitTable.COLUMN_LOCATION_ID);
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
        values.put(VisitTable.COLUMN_LOCATION_ID, locationId);
    }

    public String getLocationLat() {
        return values.getAsString(VisitTable.COLUMN_LOCATION_LAT);
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
        values.put(VisitTable.COLUMN_LOCATION_LAT, locationLat);
    }

    public String getLocationLng() {
        return values.getAsString(VisitTable.COLUMN_LOCATION_LNG);
    }

    public void setLocationLng(String locationLng) {
        this.locationLng = locationLng;
        values.put(VisitTable.COLUMN_LOCATION_LNG, locationLng);
    }

    public int getTimeStamp() {
        return values.getAsInteger(VisitTable.COLUMN_TIMESTAMP);
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
//        values.put(VisitTable.COLUMN_VISIT_CREATED_AT, " time('now') " );
        values.put(VisitTable.COLUMN_TIMESTAMP, timeStamp);
    }


    @Override
    public ContentValues getValues() {
        return values;
    }



}