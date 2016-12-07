package com.smarthomies.realtimetalk.utils;


import android.os.Bundle;
import android.util.Pair;

import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.RTTFragment;

import rx.subjects.PublishSubject;

/**
 * Created by ensar on 04/11/16.
 */
public class NavigationSubject {
    public static final String TAG = NavigationSubject.class.getSimpleName();

    private static PublishSubject<Pair<Class<? extends RTTActivity>, Bundle>> navigationSubject = PublishSubject.create();
    private static PublishSubject<Pair<Class<? extends RTTFragment>, Bundle>> fragmentNavigationSubject = PublishSubject.create();

    public static PublishSubject<Pair<Class<? extends RTTActivity>, Bundle>> getInstance() {
        return navigationSubject;
    }

    public static PublishSubject<Pair<Class<? extends RTTFragment>, Bundle>> getFragmentNavigationInstance() {
        return fragmentNavigationSubject;
    }
}
