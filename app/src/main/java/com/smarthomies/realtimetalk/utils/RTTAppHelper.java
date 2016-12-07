package com.smarthomies.realtimetalk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ensar on 31/10/16.
 */
public class RTTAppHelper {
    public static final String TAG = RTTAppHelper.class.getSimpleName();

    public static final String SHARED_PREFERENCES_USER_TOKEN = "SHARED_PREFERENCES_USER_TOKEN";

    private static RTTAppHelper instance;
    private Context context;

    private RTTAppHelper() {

    }

    public static RTTAppHelper getInstance() {
        if(instance == null) {
            instance = new RTTAppHelper();
        }
        return instance;
    }

    public void initWithContext(Context context) {
        this.context = context;
    }

    public void saveToken(String token) {
        writeToSharedPrefs(SHARED_PREFERENCES_USER_TOKEN, token);
    }

    public String getToken() {
        return readFromSharedPrefs(SHARED_PREFERENCES_USER_TOKEN);
    }

    private void writeToSharedPrefs(String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String readFromSharedPrefs(String key) {
        return readFromSharedPrefs(key, null);
    }

    private String readFromSharedPrefs(String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

}
