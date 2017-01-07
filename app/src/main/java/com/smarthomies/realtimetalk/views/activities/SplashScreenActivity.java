package com.smarthomies.realtimetalk.views.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.utils.NavigationSubject;
import com.smarthomies.realtimetalk.utils.RTTAppHelper;

public class SplashScreenActivity extends RTTActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(TextUtils.isEmpty(RTTAppHelper.getInstance().getToken())) {
                    NavigationSubject.getInstance().onNext(new Pair<Class<? extends RTTActivity>, Bundle>(LoginActivity.class, null));
                } else {
                    NavigationSubject.getInstance().onNext(new Pair<Class<? extends RTTActivity>, Bundle>(MainActivity.class, null));
                }
                NavigationSubject.getInstance().onNext(null);
            }
        }, 3000);
    }
}
