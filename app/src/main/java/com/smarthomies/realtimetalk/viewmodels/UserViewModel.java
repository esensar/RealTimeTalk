package com.smarthomies.realtimetalk.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.models.db.User;
import com.squareup.picasso.Picasso;

/**
 * Created by ensar on 15/11/16.
 */
public class UserViewModel extends BaseObservable {
    public static final String TAG = UserViewModel.class.getSimpleName();

    private User model;
    private ObservableBoolean state = new ObservableBoolean();

    public UserViewModel(User model) {
        this.model = model;
    }

    @Bindable
    public String getName() {
        return model.getFirstName() + " " + model.getLastName();
    }

    @Bindable
    public String getEmail() {
        return model.getEmail();
    }

    @Bindable
    public String getImageUrl() {
        return model.getImageUrl();
    }

    public ObservableBoolean getState() {
        return state;
    }

    public View.OnClickListener changeContactState() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                state.set(!state.get());
            }
        };
    }

}
