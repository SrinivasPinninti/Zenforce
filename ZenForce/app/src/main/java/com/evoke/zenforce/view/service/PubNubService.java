package com.evoke.zenforce.view.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.evoke.zenforce.R;
import com.evoke.zenforce.model.pojo.ChatMessage;
import com.evoke.zenforce.utility.Constants;
import com.evoke.zenforce.utility.PubnubKeys;
import com.evoke.zenforce.utility.Util;
import com.evoke.zenforce.view.activity.ChatActivity;
import com.google.gson.Gson;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

public class PubNubService extends Service {


    private static final String TAG = "PubNubService";

    // Foreground notification id
    private static final int NOTIFICATION_ID = 1;


    private boolean isChatServiceRunning = false;
    private boolean isInForeground = false;

    Pubnub pubnub = new Pubnub(PubnubKeys.PUBLISH_KEY, PubnubKeys.SUBSCRIBE_KEY);

    private ChatMessage mChat;

    private final IBinder serviceBinder = new ChatServiceBinder();

    public class ChatServiceBinder extends Binder {

        public PubNubService getService() {
            return PubNubService.this;
        }

    }


    private final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            mChat = (ChatMessage) msg.obj;
            Log.d(TAG, " handleMessage...." + mChat.getMessage());
            Log.d(TAG, " isAppAlive : " + Util.isAppAlive(PubNubService.this));


            if (Util.isAppAlive(PubNubService.this)) {

                Intent intent = new Intent(Constants.BROADCAST_ACTION_RECEIVED_MSG);
                intent.putExtra("ChatMessage", mChat);
                Log.d(TAG, " sendBroadcast ChatMessage....");
                sendBroadcast(intent);

            } else {
                createNotification();
            }


        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        isChatServiceRunning = false;
        isInForeground = false;
        Log.d(TAG, "PubNubService created...");


        try {

            Log.d(TAG, " subscribe CHANNEL_NAME : " + PubnubKeys.CHANNEL_NAME);

            pubnub.subscribe(PubnubKeys.CHANNEL_NAME, new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    super.successCallback(channel, message);
                    Log.d(TAG, "subscribe successCallback called... ");
                    Log.d("successCallback", "message " + message);
                    notifyUser(message);
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    super.errorCallback(channel, error);
                    Log.d("errorCallback", "error " + error);
                }

            });
        } catch (PubnubException pe) {
            Log.e(TAG, pe.toString());
        }
    }


    private void notifyUser(Object message) {
//            JSONObject json = new JSONObject(message.toString());
            Log.d(TAG, " Message sent by others......");

            Log.d(TAG, "Received msg : " + message.toString());
            Gson gson = new Gson();
            ChatMessage chatMessage = gson.fromJson(message.toString(), ChatMessage.class);
            Message msg = handler.obtainMessage();
            msg.obj = chatMessage;
            handler.sendMessage(msg);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called... ");
    }


    public void startService() {
        if (!isChatServiceRunning) {
            isChatServiceRunning = true;
        }
    }


    public void stopService() {
        if (isChatServiceRunning) {
            isChatServiceRunning = false;
        }
    }

    public boolean isChatServiceRunning() {
        return isChatServiceRunning;
    }


    /**
     * Place the service into the foreground
     */
    public void foreground() {
//        startForeground(NOTIFICATION_ID, createNotification());
        isInForeground = true;
    }

    /**
     * Return the service to the background
     */
    public void background() {
//        stopForeground(true);
    }

    private void createNotification() {

        Log.d(TAG, "createNotification...." + mChat);
        if (mChat == null) {
            return;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("PubNub Msg : " + mChat.getMessage())
                .setContentText("Tap to return to the chat")
                .setSmallIcon(R.mipmap.appicon);

        builder.setAutoCancel(true);

        Intent resultIntent = new Intent(this, ChatActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(101, builder.build());
        playBeep();
//        return builder.build();
    }

    /**
     * Plays device's default notification sound
     */
    public void playBeep() {

        try {
            Uri notification = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
