package com.evoke.zenforce.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by spinninti on 11/9/2016.
 */
public class NavigationItem implements Parcelable{

    private int drawable;
    private String name;
    private int color;
    private int defaultTextColor;

    public NavigationItem(int color, int drawable, String name, int defaultTextColor) {
        this.color = color;
        this.drawable = drawable;
        this.name = name;
        this.defaultTextColor = defaultTextColor;
    }

    protected NavigationItem(Parcel in) {
        drawable = in.readInt();
        name = in.readString();
        color = in.readInt();
        defaultTextColor = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(drawable);
        dest.writeString(name);
        dest.writeInt(color);
        dest.writeInt(defaultTextColor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NavigationItem> CREATOR = new Creator<NavigationItem>() {
        @Override
        public NavigationItem createFromParcel(Parcel in) {
            return new NavigationItem(in);
        }

        @Override
        public NavigationItem[] newArray(int size) {
            return new NavigationItem[size];
        }
    };

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getDefaultTextColor() {
        return defaultTextColor;
    }
}
