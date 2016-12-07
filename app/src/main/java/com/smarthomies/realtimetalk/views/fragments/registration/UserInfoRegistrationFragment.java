package com.smarthomies.realtimetalk.views.fragments.registration;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.databinding.UserInfoRegistrationBinding;
import com.smarthomies.realtimetalk.views.activities.bindingutils.OnErrorChangedCallback;

/**
 * Created by ensar on 08/11/16.
 */
public class UserInfoRegistrationFragment extends RegistrationFragment {
    public static final String TAG = UserInfoRegistrationFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UserInfoRegistrationBinding binding = DataBindingUtil.inflate(inflater, R.layout.user_info_registration, container, false);
        View view = binding.getRoot();
        binding.setViewModel(getViewModel());

        getViewModel().getUsernameError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilUsername));
        getViewModel().getEmailError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilEmail));


        return view;
    }
}
