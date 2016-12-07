package com.smarthomies.realtimetalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;

import com.smarthomies.realtimetalk.utils.NavigationSubject;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ensar on 23/10/16.
 */
public abstract class RTTActivity extends AppCompatActivity {
    public static final String TAG = RTTActivity.class.getSimpleName();

    public static final String EXTRA_BUNDLE = "EXTRA_BUNDLE";

    private CompositeSubscription compositeSubscription;

    @Override
    protected void onResume() {
        super.onResume();
        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(NavigationSubject.getInstance().subscribe(new Action1<Pair<Class<? extends RTTActivity>, Bundle>>() {
            @Override
            public void call(Pair<Class<? extends RTTActivity>, Bundle> classBundlePair) {
                if(classBundlePair == null) {
                    finish();
                    return;
                }
                Class activity = classBundlePair.first;
                Bundle bundle = classBundlePair.second;
                Intent intent = new Intent(RTTActivity.this, activity);
                intent.putExtra(EXTRA_BUNDLE, bundle);
                startActivity(intent);
            }
        }));
        compositeSubscription.add(NavigationSubject.getFragmentNavigationInstance().subscribe(new Action1<Pair<Class<? extends RTTFragment>, Bundle>>() {
            @Override
            public void call(Pair<Class<? extends RTTFragment>, Bundle> classBundlePair) {
                RTTActivity.this.startFragment(classBundlePair.first, RTTActivity.this.getFragmentContainerRId(),classBundlePair.second);
            }
        }));
        subscribeToSubjects();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
        unsubscribeFromSubjects();
    }

    protected void subscribeToSubjects() {}

    protected void unsubscribeFromSubjects() {}

    protected int getFragmentContainerRId() {
        return 0;
    }

    public void startFragment(Class<? extends RTTFragment> fragmentClass, int containerId, Bundle bundle) {
        String fragmentName = fragmentClass.getCanonicalName();
        RTTFragment fragment;

        try {
            FragmentManager fm = getSupportFragmentManager();

            // Find fragment by name
            fragment = (RTTFragment) fm.findFragmentByTag(fragmentName);

            if(fragment == null) {
                // Create new fragment
                fragment = fragmentClass.newInstance();

                // Check for bundle
                if (bundle != null) {
                    fragment.setArguments(bundle);
                }
            }

            fm.beginTransaction().replace(containerId, fragment, fragmentName).commit();
            fm.executePendingTransactions();

        } catch (Exception e) {
            Log.e(TAG, "Error starting fragment : " + fragmentName, e);
            throw new RuntimeException(e);
        }
    }

}
