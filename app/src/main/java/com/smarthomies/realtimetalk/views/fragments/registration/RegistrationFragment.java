package com.smarthomies.realtimetalk.views.fragments.registration;

import com.smarthomies.realtimetalk.RTTFragment;
import com.smarthomies.realtimetalk.viewmodels.RegistrationViewModel;
import com.smarthomies.realtimetalk.views.activities.RegistrationActivity;

/**
 * Created by ensar on 08/11/16.
 */
public class RegistrationFragment extends RTTFragment {
    public static final String TAG = RegistrationFragment.class.getSimpleName();

    protected RegistrationViewModel getViewModel() {
        return ((RegistrationActivity) getActivity()).getViewModelHolder().getRegistrationViewModel();
    }
}
