package com.smarthomies.realtimetalk.views.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.databinding.ActivityRegistrationBinding;
import com.smarthomies.realtimetalk.views.fragments.registration.RegistrationFragment;
import com.smarthomies.realtimetalk.views.fragments.registration.RegistrationViewModelHolder;
import com.smarthomies.realtimetalk.views.fragments.registration.UserInfoRegistrationFragment;

public class RegistrationActivity extends RTTActivity {

    private RegistrationViewModelHolder viewModelHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add retained to fragment manager
        if(getSupportFragmentManager().findFragmentByTag(RegistrationViewModelHolder.class.getName()) == null) {
            viewModelHolder = new RegistrationViewModelHolder();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(viewModelHolder, RegistrationViewModelHolder.class.getName())
                    .commit();
        }
        else {
            viewModelHolder = (RegistrationViewModelHolder)getSupportFragmentManager().findFragmentByTag(RegistrationViewModelHolder.class.getName());
        }

        ActivityRegistrationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        binding.setViewModel(viewModelHolder.getRegistrationViewModel());

        startFragment(UserInfoRegistrationFragment.class, null);

    }

    @Override
    protected int getFragmentContainerRId() {
        return R.id.fragment_container;
    }

    public void startFragment(Class<? extends RegistrationFragment> clazz, Bundle bundle) {
        startFragment(clazz, R.id.fragment_container, bundle);
    }

    public RegistrationViewModelHolder getViewModelHolder() {
        return viewModelHolder;
    }
}
