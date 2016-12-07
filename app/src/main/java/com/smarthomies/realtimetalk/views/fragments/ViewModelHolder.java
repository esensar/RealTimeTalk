package com.smarthomies.realtimetalk.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by ensar on 13/11/16.
 */
public abstract class ViewModelHolder extends Fragment {
    public static final String TAG = ViewModelHolder.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
