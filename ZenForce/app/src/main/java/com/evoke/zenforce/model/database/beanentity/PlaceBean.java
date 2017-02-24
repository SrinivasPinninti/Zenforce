package com.evoke.zenforce.model.database.beanentity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.evoke.zenforce.model.database.DbConstants;

/**
 * Created by spinninti on 12/19/2016.
 */
public class PlaceBean extends BaseEntityBean implements Parcelable{

    private long _ID;
    private String placeId;
    private String name;
    private String address;
    private String phone;
    private String website;
    private String lat;
    private String lng;
    private long timeStamp;

    private ContentValues values;


    public PlaceBean() {
        values = new ContentValues();
    }


    protected PlaceBean(Parcel in) {
        _ID = in.readLong();
        placeId = in.readString();
        name = in.readString();
        address = in.readString();
        phone = in.readString();
        website = in.readString();
        lat = in.readString();
        lng = in.readString();
        timeStamp = in.readLong();
        values = in.readParcelable(ContentValues.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_ID);
        dest.writeString(placeId);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeString(website);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeLong(timeStamp);
        dest.writeParcelable(values, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlaceBean> CREATOR = new Creator<PlaceBean>() {
        @Override
        public PlaceBean createFromParcel(Parcel in) {
            return new PlaceBean(in);
        }

        @Override
        public PlaceBean[] newArray(int size) {
            return new PlaceBean[size];
        }
    };

    public long get_ID() {
        return values.getAsLong(DbConstants.PlaceTable.COLUMN_ID);
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
        values.put(DbConstants.PlaceTable.COLUMN_ID, _ID);
    }

    public String getPlaceId() {
        return values.getAsString(DbConstants.PlaceTable.COLUMN_LOCATION_ID);
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
        values.put(DbConstants.PlaceTable.COLUMN_LOCATION_ID, placeId);
    }



    public String getName() {
        return values.getAsString(DbConstants.PlaceTable.COLUMN_NAME);
    }

    public void setName(String name) {
        this.name = name;
        values.put(DbConstants.PlaceTable.COLUMN_NAME, name);
    }

    public String getAddress() {
        return  values.getAsString(DbConstants.PlaceTable.COLUMN_ADDRESS);
    }

    public void setAddress(String address) {
        this.address = address;
        values.put(DbConstants.PlaceTable.COLUMN_ADDRESS, address);
    }

    public String getPhone() {
        return values.getAsString(DbConstants.PlaceTable.COLUMN_PHONE);
    }

    public void setPhone(String phone) {
        this.phone = phone;
        values.put(DbConstants.PlaceTable.COLUMN_PHONE, phone);
    }

    public String getWebsite() {
        return values.getAsString(DbConstants.PlaceTable.COLUMN_WEBSITE);
    }

    public void setWebsite(String website) {
        this.website = website;
        values.put(DbConstants.PlaceTable.COLUMN_WEBSITE, website);
    }

    public int getTimeStamp() {
        return values.getAsInteger(DbConstants.PlaceTable.COLUMN_TIMESTAMP);
    }


    public String getLat() {
        return values.getAsString(DbConstants.PlaceTable.COLUMN_LAT);
    }

    public void setLat(String lat) {
        this.lat = lat;
        values.put(DbConstants.PlaceTable.COLUMN_LAT, lat);
    }

    public String getLng() {
        return values.getAsString(DbConstants.PlaceTable.COLUMN_LNG);
    }

    public void setLng(String lng) {
        this.lng = lng;
        values.put(DbConstants.PlaceTable.COLUMN_LNG, lng);
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        values.put(DbConstants.PlaceTable.COLUMN_TIMESTAMP, timeStamp);
    }

    @Override
    public ContentValues getValues() {
        return values;
    }



}
