package com.smarthomies.realtimetalk.views.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.databinding.ActivityLoginBinding;
import com.smarthomies.realtimetalk.models.network.AuthenticationResponse;
import com.smarthomies.realtimetalk.utils.RTTErrorUtil;
import com.smarthomies.realtimetalk.viewmodels.LoginViewModel;
import com.smarthomies.realtimetalk.views.activities.bindingutils.OnErrorChangedCallback;


import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.CompositeException;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LoginActivity extends RTTActivity {

    private LoginViewModel viewModel;
    private Subscription loginSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = new LoginViewModel();
        binding.setViewModel(viewModel);

        viewModel.getPasswordError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilPassword));
        viewModel.getUsernameError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilUsername));
    }

    @Override
    protected void subscribeToSubjects() {
        super.subscribeToSubjects();
        loginSubscription = viewModel.getLoginSubject().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new LoginSubscriber());
    }

    @Override
    protected void unsubscribeFromSubjects() {
        super.unsubscribeFromSubjects();
        if(loginSubscription != null && !loginSubscription.isUnsubscribed()) {
            loginSubscription.unsubscribe();
        }
    }

    private void reconnectToLoginSubject() {
        loginSubscription = viewModel.createLoginSubject().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new LoginSubscriber());
    }

    private class LoginSubscriber extends Subscriber<AuthenticationResponse> {
        @Override
        public void onCompleted() {
            reconnectToLoginSubject();
            viewModel.onRequestCompleted();
        }

        @Override
        public void onError(Throwable e) {
            reconnectToLoginSubject();
            viewModel.onRequestCompleted();

            if (e instanceof CompositeException) {
                for (Throwable ex : ((CompositeException) e).getExceptions()) {
                    if(ex instanceof RuntimeException) {
                        handleException(ex.getCause());
                    }
                }
            } else {
                handleException(e);
            }
        }

        @Override
        public void onNext(AuthenticationResponse authenticationResponse) {
            viewModel.onLoginDone();
        }
    }

    private void handleException(Throwable e) {
        Toast.makeText(this, RTTErrorUtil.getErrorString(e), Toast.LENGTH_SHORT).show();
    }
}

