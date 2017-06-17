package com.android.hackernewsreaderapp.data.utility;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.hackernewsreaderapp.MainApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


/**
 * Created by zaidbintariq on 14/06/2017.
 */

public class CommonUtility {

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MainApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Locale getDefaultLocale() {
        Locale defaultLocale = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            defaultLocale = Resources.getSystem().getConfiguration().getLocales().get(0);
        } else {
            defaultLocale = Resources.getSystem().getConfiguration().locale;
        }
        return defaultLocale;
    }

    public static String getLocalTimeInGMT() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", getDefaultLocale());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(c.getTime());
    }

    public static String getTimeEllapseDifference(long time) {
        long diff = System.currentTimeMillis() - (time * 1000);
        String formattedTime;
        long units = TimeUnit.MILLISECONDS.toMinutes(diff);

        if (units < 60) {
            if (units == 0) {
                formattedTime = "Just now";
            } else if (units == 1) {
                formattedTime = units + " min Ago";
            } else {
                formattedTime = units + " mins Ago";
            }
        } else if (units >= 60 && units < (60 * 24)) {
            units = TimeUnit.MILLISECONDS.toHours(diff);
            if (units == 1) {
                formattedTime = units + " hour Ago";
            } else {
                formattedTime = units + " hours Ago";
            }
        } else {
            units = TimeUnit.MILLISECONDS.toDays(diff);
            if (units == 1) {
                formattedTime = units + " day Ago";
            } else {
                formattedTime = units + " days Ago";
            }
        }

        return formattedTime;
    }
}
