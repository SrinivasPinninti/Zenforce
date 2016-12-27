package com.evoke.zenforce.model.database.beanentity;

import android.content.ContentValues;

import com.evoke.zenforce.model.database.DbConstants;

/**
 * Created by spinninti on 12/19/2016.
 */
public class LocationBean extends BaseEntityBean{

    private long _ID;
    private String locationId;
    private String name;
    private String address;
    private String phone;
    private String website;
    private int timeStamp;

    private ContentValues values;


    public LocationBean() {
        values = new ContentValues();
    }


    public long get_ID() {
        return values.getAsLong(DbConstants.LocationTable.COLUMN_ID);
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
        values.put(DbConstants.LocationTable.COLUMN_ID, _ID);
    }

    public String getLocationId() {
        return values.getAsString(DbConstants.LocationTable.COLUMN_LOCATION_ID);
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
        values.put(DbConstants.LocationTable.COLUMN_LOCATION_ID, locationId);
    }



    public String getName() {
        return values.getAsString(DbConstants.LocationTable.COLUMN_NAME);
    }

    public void setName(String name) {
        this.name = name;
        values.put(DbConstants.LocationTable.COLUMN_NAME, name);
    }

    public String getAddress() {
        return  values.getAsString(DbConstants.LocationTable.COLUMN_ADDRESS);
    }

    public void setAddress(String address) {
        this.address = address;
        values.put(DbConstants.LocationTable.COLUMN_ADDRESS, address);
    }

    public String getPhone() {
        return values.getAsString(DbConstants.LocationTable.COLUMN_PHONE);
    }

    public void setPhone(String phone) {
        this.phone = phone;
        values.put(DbConstants.LocationTable.COLUMN_PHONE, phone);
    }

    public String getWebsite() {
        return values.getAsString(DbConstants.LocationTable.COLUMN_WEBSITE);
    }

    public void setWebsite(String website) {
        this.website = website;
        values.put(DbConstants.LocationTable.COLUMN_WEBSITE, website);
    }

    public int getTimeStamp() {
        return values.getAsInteger(DbConstants.LocationTable.COLUMN_TIMESTAMP);
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
        values.put(DbConstants.LocationTable.COLUMN_TIMESTAMP, timeStamp);
    }

    @Override
    public ContentValues getValues() {
        return values;
    }
}
