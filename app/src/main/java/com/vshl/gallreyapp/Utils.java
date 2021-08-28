package com.vshl.gallreyapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class Utils {
    private Context context;

    public static String TREND_SELECTED;
    public static String TREND_ID;

    public static String app_Ad_id;
    public static String banner_ad_id;
    public static String interstitial_full_screen;
    public static String rewerded_ad_id;

    public Utils(Context context) {
        this.context = context;
    }

    public void showToastSuccessMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showToastErrorMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public boolean isConnected() {
        boolean hasConnection;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            hasConnection = true;
        } else {
            hasConnection = false;
        }
        return hasConnection;
    }

}
