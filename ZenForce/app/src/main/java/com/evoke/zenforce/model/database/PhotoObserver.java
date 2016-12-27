package com.evoke.zenforce.model.database;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * Created by spinninti on 12/16/2016.
 */
public class PhotoObserver extends ContentObserver{

    private static final String TAG = "PhotoObserver";
    private Context mContext;
    private Handler mHandler;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public PhotoObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }


    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.d(TAG, " onChange 1......");

    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.d(TAG, " onChange 2......");
    }
}
