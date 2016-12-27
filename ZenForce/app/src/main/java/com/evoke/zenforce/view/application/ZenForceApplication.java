package com.evoke.zenforce.view.application;

import android.app.Application;

import com.evoke.zenforce.view.activity.PhotoActivity;

/**
 * Created by spinninti on 11/12/2016.
 */
public class ZenForceApplication extends Application{

    private static ZenForceApplication mInstance;

    private PhotoActivity.PhotoInsertedCallBack callback;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static ZenForceApplication getInstance() {
        return mInstance;
    }


    public void setListener(PhotoActivity.PhotoInsertedCallBack listener) {
        callback = listener;
    }

    public PhotoActivity.PhotoInsertedCallBack getCallback() {
        return  callback;
    }


}
