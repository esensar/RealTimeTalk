package com.smarthomies.realtimetalk.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.managers.AuthenticationManager;
import com.smarthomies.realtimetalk.models.network.AuthenticationResponse;
import com.smarthomies.realtimetalk.utils.NavigationSubject;
import com.smarthomies.realtimetalk.utils.RTTUtil;
import com.smarthomies.realtimetalk.views.activities.MainActivity;
import com.smarthomies.realtimetalk.views.activities.RegistrationActivity;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;

/**
 * Created by ensar on 01/11/16.
 */
public class LoginViewModel extends BaseObservable {
    public static final String TAG = LoginViewModel.class.getSimpleName();

    private AsyncSubject<AuthenticationResponse> loginSubject;

    private ObservableField<String> username = new ObservableField<>();
    private ObservableField<Integer> usernameError = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
    private ObservableField<Integer> passwordError = new ObservableField<>();

    private ObservableField<Boolean> requestInProgress = new ObservableField<>();

    public LoginViewModel() {
        requestInProgress.set(false);
        loginSubject = AsyncSubject.create();
    }

    public AsyncSubject<AuthenticationResponse> createLoginSubject() {
        loginSubject = AsyncSubject.create();
        return loginSubject;
    }

    public AsyncSubject<AuthenticationResponse> getLoginSubject() {
        return loginSubject;
    }

    private void loginUser() {
        requestInProgress.set(true);
        new AuthenticationManager().loginUser(username.get(), password.get()).subscribeOn(Schedulers.io()).subscribe(loginSubject);
    }

    private boolean validateFields() {
        clearErrors();

        passwordError.set(RTTUtil.getPasswordError(password.get()));
        usernameError.set(RTTUtil.getRequiredFieldError(username.get()));

        return passwordError.get() == 0 && usernameError.get() == 0;
    }

    private void clearErrors() {
        usernameError.set(0);
        passwordError.set(0);
    }

    public View.OnClickListener onLoginClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(validateFields()) {
//                    loginUser();
//                }
                onLoginDone();
            }
        };
    }

    public View.OnClickListener onRegisterClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationSubject.getInstance().onNext(new Pair<Class<? extends RTTActivity>, Bundle>(RegistrationActivity.class, null));
            }
        };
    }

    public void onLoginDone() {
        NavigationSubject.getInstance().onNext(new Pair<Class<? extends RTTActivity>, Bundle>(MainActivity.class, null));
    }

    public void onRequestCompleted() {
        requestInProgress.set(false);
    }

    public ObservableField<String> getUsername() {
        return username;
    }

    public void setUsername(ObservableField<String> username) {
        this.username = username;
    }

    public ObservableField<Integer> getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(ObservableField<Integer> usernameError) {
        this.usernameError = usernameError;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setPassword(ObservableField<String> password) {
        this.password = password;
    }

    public ObservableField<Integer> getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(ObservableField<Integer> passwordError) {
        this.passwordError = passwordError;
    }

    public ObservableField<Boolean> getRequestInProgress() {
        return requestInProgress;
    }

    public void setRequestInProgress(ObservableField<Boolean> requestInProgress) {
        this.requestInProgress = requestInProgress;
    }
}
