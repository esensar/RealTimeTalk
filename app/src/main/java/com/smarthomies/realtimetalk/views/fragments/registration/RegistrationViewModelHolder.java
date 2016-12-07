package com.smarthomies.realtimetalk.views.fragments.registration;

import com.smarthomies.realtimetalk.viewmodels.RegistrationViewModel;
import com.smarthomies.realtimetalk.views.fragments.ViewModelHolder;

/**
 * Created by ensar on 13/11/16.
 */
public class RegistrationViewModelHolder extends ViewModelHolder {
    public static final String TAG = RegistrationViewModelHolder.class.getSimpleName();

    private RegistrationViewModel registrationViewModel = new RegistrationViewModel();

    public RegistrationViewModel getRegistrationViewModel() {
        return registrationViewModel;
    }

    public void setRegistrationViewModel(RegistrationViewModel registrationViewModel) {
        this.registrationViewModel = registrationViewModel;
    }
}
