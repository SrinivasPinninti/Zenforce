package com.evoke.zenforce.utility;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;

import com.evoke.zenforce.view.application.ZenForceApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by spinninti on 11/12/2016.
 */
public class Util {


    public static final int REQUEST_TYPE_PLACE = 3;

    public static boolean isNetworkConnected(){

            ConnectivityManager connectivityManager = (ConnectivityManager) ZenForceApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null) return  false;

            if(networkInfo.getType() == connectivityManager.TYPE_WIFI) {
                return  true;
            } else if(networkInfo.getType() == connectivityManager.TYPE_MOBILE) {
                return true;
            }

        return false;

    }


    public static boolean isLocationServicesEnabled() {

        LocationManager lm = (LocationManager)ZenForceApplication.getInstance().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}


        return gps_enabled && network_enabled;

    }

    public static int getItemColor(Context context, int color) {
        return  ContextCompat.getColor(context, color);
    }

    public static String getDateTime(int dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "d MMM yyyy HH:mm a", Locale.getDefault());
//        Date date = new Date();
//        return Integer.valueOf(dateFormat.format(date));
        long date = dateTime;
        Date dateObj = new Date(date * 1000);
        return dateFormat.format(dateObj);

    }



    public static String getDisplayDateTime(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, d MMM yyyy HH:mm a", Locale.getDefault());
//        Date date = new Date();
        return dateFormat.format(date);
    }
}
