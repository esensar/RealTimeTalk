package com.smarthomies.realtimetalk.views.fragments;

import com.smarthomies.realtimetalk.viewmodels.ProfileViewModel;

/**
 * Created by ensar on 15/11/16.
 */
public class ProfileViewModelHolder extends ViewModelHolder {
    public static final String TAG = ProfileViewModelHolder.class.getSimpleName();

    private ProfileViewModel profileViewModel = new ProfileViewModel();

    public ProfileViewModel getProfileViewModel() {
        return profileViewModel;
    }

    public void setProfileViewModel(ProfileViewModel profileViewModel) {
        this.profileViewModel = profileViewModel;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileViewModel.clear();
    }
}
