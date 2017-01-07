package com.smarthomies.realtimetalk.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.RTTFragment;
import com.smarthomies.realtimetalk.managers.AccountManager;
import com.smarthomies.realtimetalk.managers.AuthenticationManager;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.models.network.AuthenticationResponse;
import com.smarthomies.realtimetalk.models.network.UserResponse;
import com.smarthomies.realtimetalk.utils.NavigationSubject;
import com.smarthomies.realtimetalk.utils.RTTUtil;
import com.smarthomies.realtimetalk.views.activities.MainActivity;
import com.smarthomies.realtimetalk.views.fragments.registration.PasswordRegistrationFragment;
import com.smarthomies.realtimetalk.views.fragments.registration.UserNameRegistrationFragment;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ensar on 01/11/16.
 */
public class ProfileViewModel extends BaseObservable {
    public static final String TAG = ProfileViewModel.class.getSimpleName();

    private ObservableField<String> firstName = new ObservableField<>();
    private ObservableField<String> lastName = new ObservableField<>();
    private ObservableField<Integer> firstNameError = new ObservableField<>();
    private ObservableField<Integer> lastNameError = new ObservableField<>();
    private ObservableField<String> oldPassword = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
    private ObservableField<String> passwordConfirmation = new ObservableField<>();
    private ObservableField<Integer> oldPasswordError = new ObservableField<>();
    private ObservableField<Integer> passwordError = new ObservableField<>();
    private ObservableField<Integer> passwordConfirmationError = new ObservableField<>();
    private ObservableField<String> email = new ObservableField<>();
    private ObservableField<Integer> emailError = new ObservableField<>();
    private ObservableBoolean passwordsVisibility = new ObservableBoolean();

    private CompositeSubscription subscription;

    public ProfileViewModel() {
        subscription = new CompositeSubscription();
    }

    private boolean validateNames() {
        clearNamesErrors();

        firstNameError.set(RTTUtil.getRequiredFieldError(firstName.get()));
        lastNameError.set(RTTUtil.getRequiredFieldError(lastName.get()));
        emailError.set(RTTUtil.getRequiredFieldError(email.get()));

        return firstNameError.get() == 0 && lastNameError.get() == 0 && emailError.get() == 0;
    }

    private boolean validatePassword() {
        clearPasswordErrors();

        oldPasswordError.set(RTTUtil.getPasswordError(oldPassword.get()));
        passwordError.set(RTTUtil.getPasswordError(password.get()));
        passwordConfirmationError.set(RTTUtil.getPasswordConfirmationError(password.get(), passwordConfirmation.get()));

        return passwordError.get() == 0 && passwordConfirmationError.get() == 0 && oldPasswordError.get() == 0;
    }

    private void clearNamesErrors() {
        firstNameError.set(0);
        lastNameError.set(0);
        emailError.set(0);
    }

    private void clearPasswordErrors() {
        oldPasswordError.set(0);
        passwordError.set(0);
        passwordConfirmationError.set(0);
    }

    public ObservableField<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(ObservableField<String> firstName) {
        this.firstName = firstName;
    }

    public ObservableField<String> getLastName() {
        return lastName;
    }

    public void setLastName(ObservableField<String> lastName) {
        this.lastName = lastName;
    }

    public ObservableField<Integer> getFirstNameError() {
        return firstNameError;
    }

    public void setFirstNameError(ObservableField<Integer> firstNameError) {
        this.firstNameError = firstNameError;
    }

    public ObservableField<Integer> getLastNameError() {
        return lastNameError;
    }

    public void setLastNameError(ObservableField<Integer> lastNameError) {
        this.lastNameError = lastNameError;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setPassword(ObservableField<String> password) {
        this.password = password;
    }

    public ObservableField<String> getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(ObservableField<String> passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public ObservableField<Integer> getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(ObservableField<Integer> passwordError) {
        this.passwordError = passwordError;
    }

    public ObservableField<Integer> getPasswordConfirmationError() {
        return passwordConfirmationError;
    }

    public void setPasswordConfirmationError(ObservableField<Integer> passwordConfirmationError) {
        this.passwordConfirmationError = passwordConfirmationError;
    }

    public ObservableBoolean getPasswordsVisibility() {
        return passwordsVisibility;
    }

    public void setPasswordsVisibility(ObservableBoolean passwordsVisibility) {
        this.passwordsVisibility = passwordsVisibility;
    }

    public ObservableField<String> getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(ObservableField<String> oldPassword) {
        this.oldPassword = oldPassword;
    }

    public ObservableField<Integer> getOldPasswordError() {
        return oldPasswordError;
    }

    public void setOldPasswordError(ObservableField<Integer> oldPasswordError) {
        this.oldPasswordError = oldPasswordError;
    }

    public ObservableField<String> getEmail() {
        return email;
    }

    public void setEmail(ObservableField<String> email) {
        this.email = email;
    }

    public ObservableField<Integer> getEmailError() {
        return emailError;
    }

    public void setEmailError(ObservableField<Integer> emailError) {
        this.emailError = emailError;
    }

    public void onSaveClicked() {
        saveProfile();

    }

    public void loadProfile() {
        subscription.add(new AccountManager().getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        User user = userResponse.getData();
                        if(user != null) {
                            firstName.set(user.getFirstName());
                            lastName.set(user.getLastName());
                            email.set(user.getEmail());
                            clearPasswordErrors();
                            clearNamesErrors();
                        }
                    }
                }));
    }

    public void saveProfile() {
        if (validateNames()) {
            if(passwordsVisibility.get() && validatePassword()) {
                subscription.add(new AccountManager().changePassword(oldPassword.get(), password.get())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Object o) {

                            }
                        }));
            }
            User user = new User();
            user.setFirstName(firstName.get());
            user.setLastName(lastName.get());
            user.setEmail(email.get());
            subscription.add(new AccountManager().updateProfile(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Object>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Object o) {
                            if(!passwordsVisibility.get() || validatePassword()) {
                                NavigationSubject.getInstance().onNext(null);
                            }
                        }
                    }));
        }
    }

    public void clear() {
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
