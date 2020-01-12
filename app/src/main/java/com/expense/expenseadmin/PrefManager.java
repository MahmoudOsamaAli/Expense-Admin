package com.expense.expenseadmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefManager {


    public static final String KEY_HEADS = "pref_heads";
    public static final String USER_ID = "pref_UID";
    public static final String USER_EMAIL = "pref_user_email";
    public static final String USER_PHONE = "pref_user_phone";
    public static final String USER_TOKEN = "pref_user_firebase_token";
    public static final String USER_NAME = "pref_user_displayed_name";


    private SharedPreferences prefs;

    public PrefManager(Context c) {
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public void saveLong(String key, long value) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(key, value);
        edit.apply();
    }

    public void saveBoolean(String key, boolean b) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(key, b);
        edit.apply();
    }

    public void saveInt(String key, int b) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(key, b);
        edit.apply();
    }

    public String readString(String key) {
        return prefs.getString(key, "");
    }

    public boolean readBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    public long readLong(String key) {
        return prefs.getLong(key, 0);
    }
}
