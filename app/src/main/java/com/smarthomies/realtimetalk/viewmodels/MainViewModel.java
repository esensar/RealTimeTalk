package com.smarthomies.realtimetalk.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.utils.NavigationSubject;
import com.smarthomies.realtimetalk.views.activities.MainActivity;
import com.smarthomies.realtimetalk.views.activities.SearchActivity;

/**
 * Created by ensar on 15/11/16.
 */
public class MainViewModel extends BaseObservable {
    public static final String TAG = MainViewModel.class.getSimpleName();

    UserViewModel userViewModel;

    public void setUser(User user) {
        userViewModel = new UserViewModel(user);
    }

    @Bindable
    public UserViewModel getUserViewModel() {
        return userViewModel;
    }

    public void setUserViewModel(UserViewModel userViewModel) {
        this.userViewModel = userViewModel;
    }

    public View.OnClickListener onSearchClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationSubject.getInstance().onNext(new Pair<Class<? extends RTTActivity>, Bundle>(SearchActivity.class, null));
            }
        };
    }
}
