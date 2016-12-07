package com.smarthomies.realtimetalk.views.activities.bindingutils;

import android.databinding.BindingAdapter;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.widget.ImageView;

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
        view.addHeaderView(navHeaderMainBinding.getRoot());
    }


}
