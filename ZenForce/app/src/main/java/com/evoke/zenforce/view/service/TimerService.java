package com.evoke.zenforce.view.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.evoke.zenforce.R;
import com.evoke.zenforce.view.activity.VisitActivity;

import java.util.concurrent.TimeUnit;

public class TimerService extends Service {


    private static final String TAG = TimerService.class.getSimpleName();

    // Start and end times in milliseconds
    private long startTime, endTime;

    // Is the service tracking time?
    private boolean isTimerRunning;

    // Foreground notification id
    private static final int NOTIFICATION_ID = 1;

    // Service binder
    private final IBinder serviceBinder = new RunServiceBinder();

    public class RunServiceBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }

    @Override
    public void onCreate() {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Creating service");
        }
        startTime = 0;
        endTime = 0;
        isTimerRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting service");
        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Binding service");
        }
        return serviceBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Destroying service");
        }
    }

    /**
     * Starts the timer
     */
    public void startTimer() {
        if (!isTimerRunning) {
            startTime = System.currentTimeMillis();
            isTimerRunning = true;
        }
        else {
            Log.e(TAG, "startTimer request for an already running timer");
        }
    }

    /**
     * Stops the timer
     */
    public void stopTimer() {
        if (isTimerRunning) {
            endTime = System.currentTimeMillis();
            isTimerRunning = false;
        }
        else {
            Log.e(TAG, "stopTimer request for a timer that isn't running");
        }
    }

    /**
     * @return whether the timer is running
     */
    public boolean isTimerRunning() {
        return isTimerRunning;
    }

    /**
     * Returns the  elapsed time
     *
     * @return the elapsed time in seconds
     */
    public long elapsedTime() {
        // If the timer is running, the end time will be zero
//        return endTime > startTime ?
//                (endTime - startTime) / 1000 :
//                (System.currentTimeMillis() - startTime) / 1000;


        return endTime > startTime ?
                (endTime - startTime) :
                (System.currentTimeMillis() - startTime);
    }


    public  String formatElapsedTime() {

        long millis = elapsedTime();

        if(millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

//	    long days = TimeUnit.MILLISECONDS.toDays(millis);
//	    millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);

        if (hours > 0) {
            sb.append(hours).append(":")
                    .append(formatDigits(minutes)).append(":")
                    .append(formatDigits(seconds));
        } else {
            sb.append(formatDigits(minutes)).append(":")
                    .append(formatDigits(seconds));
        }

//        Log.e(TAG, " formatElapsedTime : " + sb.toString());
        return sb.toString();
    }



    private String formatDigits(long num) {
        return (num < 10) ? "0" + num : new Long(num).toString();
    }

    /**
     * Place the service into the foreground
     */
    public void foreground() {
        startForeground(NOTIFICATION_ID, createNotification());
    }

    /**
     * Return the service to the background
     */
    public void background() {
        stopForeground(true);
    }

    /**
     * Creates a notification for placing the service into the foreground
     *
     * @return a notification for interacting with the service when in the foreground
     */
    private Notification createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Timer Active")
                .setContentText("Tap to return to the timer")
                .setSmallIcon(R.mipmap.appicon);

        Intent resultIntent = new Intent(this, VisitActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this, 0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        return builder.build();
    }
}
