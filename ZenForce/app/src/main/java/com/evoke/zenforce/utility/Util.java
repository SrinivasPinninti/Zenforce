package com.evoke.zenforce.utility;

import android.app.ActivityManager;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.evoke.zenforce.view.application.ZenForceApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by spinninti on 11/12/2016.
 */
public class Util {

    public static final int REQUEST_TYPE_PLACE = 3;
    public static  String USER = "Manager";

    public static long sVisitId = 0;

    public static int sPhoto_count = 0;
    public static int sNote_count = 0;

    public static boolean isNetworkConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) ZenForceApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) return false;

        if (networkInfo.getType() == connectivityManager.TYPE_WIFI) {
            return true;
        } else if (networkInfo.getType() == connectivityManager.TYPE_MOBILE) {
            return true;
        }

        return false;

    }


    public static boolean isLocationServicesEnabled() {

        LocationManager lm = (LocationManager) ZenForceApplication.getInstance().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }


        return gps_enabled && network_enabled;

    }

    public static String getVisitPeriod(long startTime, long endTime) {

        long millis =  Math.abs(endTime > startTime ?
                (endTime - startTime) :
                (System.currentTimeMillis() - startTime));


//        if(millis < 0) {
//            throw new IllegalArgumentException("Duration must be greater than zero!");
//        }

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

        Log.e("Util", " visit duration : " + sb.toString());
        return sb.toString();
    }



    private static String formatDigits(long num) {
        return (num < 10) ? "0" + num : new Long(num).toString();
    }

    /*public static String getDuration(long startTime, long endTime) {

        String result = (String) DateUtils.getRelativeTimeSpanString(startTime, endTime, 0);

        return result;
    }*/


    public static String getTime(long milliSeconds) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        Date date = new Date(milliSeconds);
        String time=dateFormat.format(date);
        System.out.println(time);
        return time;
    }

    public static String getTimeStamp(long startTime, long endTime) {

        return getTime(startTime) + "-" + getTime(endTime);
    }

    public static boolean isAppAlive(Context appContext) {

        ActivityManager activityManager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;

        if (services.get(0).topActivity.getPackageName().toString()
                .equalsIgnoreCase(appContext.getPackageName().toString())) {
            isActivityFound = true;
        }

        return isActivityFound;

    }

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df3 = new SimpleDateFormat("EEE, d MMM yyyy HH:mm: aaa");
        return df3.format(c.getTime());
    }



}
