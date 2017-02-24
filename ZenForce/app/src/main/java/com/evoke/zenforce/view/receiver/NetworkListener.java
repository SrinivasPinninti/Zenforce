package com.evoke.zenforce.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.evoke.zenforce.utility.Util;

public class NetworkListener extends BroadcastReceiver {


    private static final String TAG = "NetworkListener";



    @Override
    public void onReceive(Context context, Intent intent) {

        if (Util.isNetworkConnected()) {

            Log.d(TAG, "Network Connected......");
//            Intent intent1 = new Intent(context, ChatIntentService.class);
//            context.startService(intent1);

        } else {

            Log.d(TAG, "Network disconnected......");
        }

    }
}
