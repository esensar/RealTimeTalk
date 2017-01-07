package com.smarthomies.realtimetalk;

import android.app.Application;

import com.smarthomies.realtimetalk.utils.RTTAppHelper;

import io.realm.Realm;

/**
 * Created by ensar on 31/10/16.
 */
public class RTTApp extends Application {
    public static final String TAG = RTTApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        RTTAppHelper.getInstance().initWithContext(getApplicationContext());

        Realm.init(this);
    }
}
