package com.vshl.gallreyapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    public static final String TACHUKADI_APP="tachukadi_app";
    private static Preferences preference;
    private SharedPreferences mSharedPreferences = null;
    private Preferences(Context context){
        if(mSharedPreferences ==null){
            mSharedPreferences = context.getSharedPreferences(TACHUKADI_APP, Context.MODE_PRIVATE);
        }
    }
    private static Preferences getPreference(Context context){
        if(preference == null){
            preference = new Preferences(context);
        }
        return preference;
    }


    public static void setString(Context aContext, Key aKey, String aValue){
        preference = getPreference(aContext);
        preference.mSharedPreferences.edit().putString(aKey.name(),aValue).apply();
    }

    public static String getString(Context aContext, Key aKey){
        preference = getPreference(aContext);
        return preference.mSharedPreferences.getString(aKey.name(),null);
    }

    public static void setInt(Context aContext, Key aKey, int aValue){
        preference = getPreference(aContext);
        preference.mSharedPreferences.edit().putInt(aKey.name(),aValue).apply();
    }

    public static int getInt(Context aContext, Key aKey){
        preference = getPreference(aContext);
        return preference.mSharedPreferences.getInt(aKey.name(),0);
    }

    public static boolean getBoolean(Context aContext, Key aKey){
        preference = getPreference(aContext);
        return preference.mSharedPreferences.getBoolean(aKey.name(),false);
    }


    public static void setBoolean(Context aContext, Key aKey, boolean aValue){
        preference = getPreference(aContext);
        preference.mSharedPreferences.edit().putBoolean(aKey.name(),aValue).apply();
    }

    public static String getPreferences(Context context, String keyValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(keyValue, "");
    }
}
