package com.smarthomies.realtimetalk.views.activities.bindingutils;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.support.design.widget.NavigationView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.viewmodels.UserViewModel;
import com.squareup.picasso.Picasso;

import com.smarthomies.realtimetalk.databinding.NavHeaderMainBinding;

/**
 * Created by ensar on 17/11/16.
 */
public class BindingAdapters {
    public static final String TAG = BindingAdapters.class.getSimpleName();

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).placeholder(R.mipmap.ic_launcher).into(view);
    }

    @BindingAdapter({"headerLayout"})
    public static void setHeaderViewModel(NavigationView view, UserViewModel userViewModel) {
        NavHeaderMainBinding navHeaderMainBinding = NavHeaderMainBinding.inflate(LayoutInflater.from(view.getContext()));
        navHeaderMainBinding.setViewModel(userViewModel);
        navHeaderMainBinding.executePendingBindings();
        for(int i = 0; i < view.getHeaderCount(); i++) {
            view.removeHeaderView(view.getHeaderView(i));
        }
        view.addHeaderView(navHeaderMainBinding.getRoot());
    }

    @BindingAdapter("android:imeActionId")
    public static void setActionId(EditText view, int id) {
        if (view.getContext() instanceof Activity) {
            final View actionView = ((Activity) view.getContext()).findViewById(id);
            view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == EditorInfo.IME_ACTION_DONE) {
                        actionView.performClick();
                    }
                    return false;
                }
            });
        }
    }


}
