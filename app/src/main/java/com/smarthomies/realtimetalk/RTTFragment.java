package com.smarthomies.realtimetalk;

import android.support.v4.app.Fragment;

/**
 * Created by ensar on 23/10/16.
 */
public class RTTFragment extends Fragment {
    public static final String TAG = RTTFragment.class.getSimpleName();

    protected void subscribeToSubjects() {}

    protected void unsubscribeFromSubjects() {}

    @Override
    public void onResume() {
        super.onResume();
        subscribeToSubjects();
    }

    @Override
    public void onPause() {
        super.onPause();
        unsubscribeFromSubjects();
    }
}
