package com.demo.grillyapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class PreferencesUtils {

    public final static String USER_CREDENTIALS = "userCredentials";

    public final static String USER_STATUS = "status";

    public final static String USER_IMAGE = "userImage";
    public final static String USER_NAME = "userName";
    public final static String USER_STREET = "userStreet";
    public final static String USER_PHONE = "userPhone";

    public PreferencesUtils(){}


    public static void saveUserStatus(Context context){
        SharedPreferences userSharedPref = context.getSharedPreferences(USER_CREDENTIALS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSharedPref.edit();

        editor.putBoolean(USER_STATUS, true);
        editor.apply();
    }
    public static void removeUserStatus(Context context){
        SharedPreferences userSharedPref = context.getSharedPreferences(USER_CREDENTIALS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSharedPref.edit();

        editor.putBoolean(USER_STATUS, false);
        editor.apply();
    }
    public static boolean isUserSigned(Context context){
        SharedPreferences userSharedPref = context.getSharedPreferences(USER_CREDENTIALS, Context.MODE_PRIVATE);
        return userSharedPref.getBoolean(USER_STATUS, false);
    }

    public static void saveUserImage(Context context, Uri imageUri){
        SharedPreferences userSharedPref = context.getSharedPreferences(USER_CREDENTIALS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSharedPref.edit();

        if(imageUri != null)
            editor.putString(USER_IMAGE, imageUri.toString());

        editor.apply();
    }
    public static String getUserImageString(Context context){
        SharedPreferences userSharedPref = context.getSharedPreferences(USER_CREDENTIALS, Context.MODE_PRIVATE);
        return userSharedPref.getString(USER_IMAGE, null);
    }
    public static void saveUserData(Context context, String name, String street, String phone){
        SharedPreferences userSharedPref = context.getSharedPreferences(USER_CREDENTIALS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSharedPref.edit();

        if(name != null)
            editor.putString(USER_NAME, name);
        if(street != null)
            editor.putString(USER_STREET, street);
        if(phone != null)
            editor.putString(USER_PHONE, phone);

        editor.apply();
    }
    public static String getUserName(Context context){
        SharedPreferences userSharedPref = context.getSharedPreferences(USER_CREDENTIALS, Context.MODE_PRIVATE);
        return userSharedPref.getString(USER_NAME, null);
    }
    public static String getUserStreet(Context context){
        SharedPreferences userSharedPref = context.getSharedPreferences(USER_CREDENTIALS, Context.MODE_PRIVATE);
        return userSharedPref.getString(USER_STREET, null);
    }
    public static String getUserPhone(Context context){
        SharedPreferences userSharedPref = context.getSharedPreferences(USER_CREDENTIALS, Context.MODE_PRIVATE);
        return userSharedPref.getString(USER_PHONE, null);
    }

}
