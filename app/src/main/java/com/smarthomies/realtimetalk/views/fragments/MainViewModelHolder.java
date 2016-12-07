package com.smarthomies.realtimetalk.views.fragments;

import com.smarthomies.realtimetalk.viewmodels.MainViewModel;

/**
 * Created by ensar on 15/11/16.
 */
public class MainViewModelHolder extends ViewModelHolder {
    public static final String TAG = MainViewModelHolder.class.getSimpleName();

    private MainViewModel mainViewModel = new MainViewModel();

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }
}
