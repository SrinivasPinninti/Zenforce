package com.evoke.zenforce.model.database.beanentity;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.evoke.zenforce.model.database.DbConstants;

/**
 * Created by spinninti on 11/29/2016.
 */
public class MessageEntityBean extends BaseEntityBean implements Parcelable {

    private long _ID;
    private String user;
    private String message;
    private int isSent;
    private long item_id;
    private String channel;
    private String time;


    private ContentValues values;


    public MessageEntityBean() {

        values = new ContentValues();

    }

    protected MessageEntityBean(Parcel in) {
        _ID = in.readLong();
        user = in.readString();
        message = in.readString();
        isSent = in.readInt();
        item_id = in.readLong();
        channel = in.readString();
        time = in.readString();
        values = in.readParcelable(ContentValues.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_ID);
        dest.writeString(user);
        dest.writeString(message);
        dest.writeInt(isSent);
        dest.writeLong(item_id);
        dest.writeString(channel);
        dest.writeString(time);
        dest.writeParcelable(values, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageEntityBean> CREATOR = new Creator<MessageEntityBean>() {
        @Override
        public MessageEntityBean createFromParcel(Parcel in) {
            return new MessageEntityBean(in);
        }

        @Override
        public MessageEntityBean[] newArray(int size) {
            return new MessageEntityBean[size];
        }
    };

    public long get_ID() {
        return values.getAsLong(DbConstants.MessageTable.COLUMN_ID);
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
        values.put(DbConstants.MessageTable.COLUMN_ID, _ID);
    }


    public String getUser() {
        return values.getAsString(DbConstants.MessageTable.COLUMN_USER);
    }

    public void setUser(String user) {
        this.user = user;
        values.put(DbConstants.MessageTable.COLUMN_USER, user);
    }

    public String getMessage() {
        return values.getAsString(DbConstants.MessageTable.COLUMN_MSG);
    }

    public void setMessage(String message) {
        this.message = message;
        values.put(DbConstants.MessageTable.COLUMN_MSG, message);
    }


    public long getItem_id() {
        return values.getAsLong(DbConstants.MessageTable.COLUMN_ITEM_ID);
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
        values.put(DbConstants.MessageTable.COLUMN_ITEM_ID, item_id);
    }

    public int getIsSent() {
        return values.getAsInteger(DbConstants.MessageTable.COLUMN_IS_SENT);
    }

    public void setIsSent(int isSent) {
        this.isSent = isSent;
        values.put(DbConstants.MessageTable.COLUMN_IS_SENT, isSent);
    }

    public String getChannel() {
        return values.getAsString(DbConstants.MessageTable.COLUMN_CHANNEL);
    }

    public void setChannel(String channel) {
        this.channel = channel;
        values.put(DbConstants.MessageTable.COLUMN_CHANNEL, channel);
    }

    public String getTime() {
        return values.getAsString(DbConstants.MessageTable.COLUMN_TIME);
    }

    public void setTime(String time) {
        this.time = time;
        values.put(DbConstants.MessageTable.COLUMN_TIME, time);
    }


    @Override
    public ContentValues getValues() {
        return values;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
