package com.smarthomies.realtimetalk.views.activities.bindingutils;

import android.databinding.Observable;
import android.databinding.ObservableField;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;

/**
 * Created by ensar on 01/11/16.
 */
public class OnErrorChangedCallback extends Observable.OnPropertyChangedCallback {

    private TextInputLayout layout;

    public OnErrorChangedCallback(TextInputLayout textInputLayout) {
        layout = textInputLayout;
    }

    @Override
    public void onPropertyChanged(Observable observable, int i) {
        Integer errorRId = ((ObservableField<Integer>) observable).get();
        if (errorRId == 0) {
            layout.setErrorEnabled(false);
        } else {
            layout.setErrorEnabled(true);
            layout.setError(layout.getContext().getString(errorRId));
        }
    }
}
