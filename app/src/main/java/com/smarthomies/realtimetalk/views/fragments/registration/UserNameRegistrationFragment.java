package com.smarthomies.realtimetalk.views.fragments.registration;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.databinding.UserNameRegistrationBinding;
import com.smarthomies.realtimetalk.views.activities.bindingutils.OnErrorChangedCallback;

/**
 * Created by ensar on 08/11/16.
 */
public class UserNameRegistrationFragment extends RegistrationFragment {
    public static final String TAG = UserNameRegistrationFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UserNameRegistrationBinding binding = DataBindingUtil.inflate(inflater, R.layout.user_name_registration, container, false);
        View view = binding.getRoot();
        binding.setViewModel(getViewModel());

        getViewModel().getFirstNameError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilFirstName));
        getViewModel().getLastNameError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilLastName));

        return view;
    }
}
