package com.evoke.zenforce.model.database.beanentity;

import android.content.ContentValues;

import com.evoke.zenforce.model.database.DbConstants;

/**
 * Created by spinninti on 11/29/2016.
 */
public class BarCodeEntityBean extends BaseEntityBean {

    private long _ID;
    private String number;
    private String name;
    private String exp;
    private String price;
    private String type;
    private String weight;
    private String temp;
    private long timeStamp;

    private ContentValues values;


    public BarCodeEntityBean() {

        values = new ContentValues();

    }

    public long get_ID() {
        return values.getAsLong(DbConstants.BarCodeTable.COLUMN_ID);
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
        values.put(DbConstants.BarCodeTable.COLUMN_ID, _ID);
    }


    public String getNumber() {
        return values.getAsString(DbConstants.BarCodeTable.COLUMN_NUMBER);
    }

    public void setNumber(String number) {
        this.number = number;
        values.put(DbConstants.BarCodeTable.COLUMN_NUMBER, number);
    }

    public String getName() {
        return values.getAsString(DbConstants.BarCodeTable.COLUMN_NAME);
    }

    public void setName(String name) {
        this.name = name;
        values.put(DbConstants.BarCodeTable.COLUMN_NAME, name);
    }

    public String getExp() {
        return values.getAsString(DbConstants.BarCodeTable.COLUMN_EXP_DATE);
    }

    public void setExp(String exp) {
        this.exp = exp;
        values.put(DbConstants.BarCodeTable.COLUMN_EXP_DATE, exp);
    }

    public String getPrice() {
        return values.getAsString(DbConstants.BarCodeTable.COLUMN_PRICE);
    }

    public void setPrice(String price) {
        this.price = price;
        values.put(DbConstants.BarCodeTable.COLUMN_PRICE, price);
    }

    public String getType() {
        return values.getAsString(DbConstants.BarCodeTable.COLUMN_TYPE);
    }

    public void setType(String type) {
        this.type = type;
        values.put(DbConstants.BarCodeTable.COLUMN_TYPE, type);
    }

    public String getWeight() {
        return values.getAsString(DbConstants.BarCodeTable.COLUMN_WEIGHT);
    }

    public void setWeight(String weight) {
        this.weight = weight;
        values.put(DbConstants.BarCodeTable.COLUMN_WEIGHT, weight);
    }

    public String getTemp() {
        return values.getAsString(DbConstants.BarCodeTable.COLUMN_TEMP);
    }

    public void setTemp(String temp) {
        this.temp = temp;
        values.put(DbConstants.BarCodeTable.COLUMN_TEMP, temp);
    }

    public long getTimeStamp() {
        return values.getAsLong(DbConstants.BarCodeTable.COLUMN_TIMESTAMP);
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
        values.put(DbConstants.BarCodeTable.COLUMN_TIMESTAMP, timeStamp);
    }

    @Override
    public ContentValues getValues() {
        return values;
    }
}
