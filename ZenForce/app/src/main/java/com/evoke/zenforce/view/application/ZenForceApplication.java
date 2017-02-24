package com.evoke.zenforce.view.application;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.evoke.zenforce.view.service.PubNubService;

/**
 * Created by spinninti on 11/12/2016.
 */
public class ZenForceApplication extends Application{

    private static final String TAG = "ZenForceApplication";
    private static ZenForceApplication mInstance;



    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Log.d(TAG, " Starting PubNubService......");
        Intent intent = new Intent(this, PubNubService.class);
        startService(intent);
    }



    public static ZenForceApplication getInstance() {
        return mInstance;
    }

}
