package com.evoke.zenforce.view.chip;

/**
 * Created by spinninti on 11/28/2016.
 */
public class Tag implements Chip {


    private String mName;
    private int mType = 0;


    public Tag(String mName, int mType) {
       this(mName);
        this.mType = mType;
    }

    public Tag(String mName) {
        this.mName = mName;
    }

    public String getName() {
        return mName;
    }

    public int getType() {
        return mType;
    }

    @Override
    public String getText() {
        return mName;
    }
}