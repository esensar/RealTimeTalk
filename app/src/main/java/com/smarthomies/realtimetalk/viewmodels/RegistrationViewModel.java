package com.smarthomies.realtimetalk.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.RTTFragment;
import com.smarthomies.realtimetalk.managers.AuthenticationManager;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.models.network.AuthenticationResponse;
import com.smarthomies.realtimetalk.utils.NavigationSubject;
import com.smarthomies.realtimetalk.utils.RTTUtil;
import com.smarthomies.realtimetalk.views.activities.MainActivity;
import com.smarthomies.realtimetalk.views.fragments.registration.PasswordRegistrationFragment;
import com.smarthomies.realtimetalk.views.fragments.registration.UserNameRegistrationFragment;

import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;

/**
 * Created by ensar on 01/11/16.
 */
public class RegistrationViewModel extends BaseObservable {
    public static final String TAG = RegistrationViewModel.class.getSimpleName();

    private AsyncSubject<AuthenticationResponse> registrationSubject;

    private ObservableField<String> username = new ObservableField<>();
    private ObservableField<String> firstName = new ObservableField<>();
    private ObservableField<String> lastName = new ObservableField<>();
    private ObservableField<String> email = new ObservableField<>();
    private ObservableField<Integer> usernameError = new ObservableField<>();
    private ObservableField<Integer> firstNameError = new ObservableField<>();
    private ObservableField<Integer> lastNameError = new ObservableField<>();
    private ObservableField<Integer> emailError = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
    private ObservableField<String> passwordConfirmation = new ObservableField<>();
    private ObservableField<Integer> passwordError = new ObservableField<>();
    private ObservableField<Integer> passwordConfirmationError = new ObservableField<>();

    public RegistrationViewModel() {
        requestInProgress.set(false);
        registrationSubject = AsyncSubject.create();
    }

    public AsyncSubject<AuthenticationResponse> createRegistrationSubject() {
        registrationSubject = AsyncSubject.create();
        return registrationSubject;
    }

    public AsyncSubject<AuthenticationResponse> getRegistrationSubject() {
        return registrationSubject;
    }

    private void registerUser() {
        requestInProgress.set(true);
        User user = new User();
        user.setEmail(email.get());
        user.setFirstName(firstName.get());
        user.setLastName(lastName.get());
        new AuthenticationManager().registerUser(user, username.get(), password.get()).subscribeOn(Schedulers.io()).subscribe(registrationSubject);
    }

    public View.OnClickListener onRegisterClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatePassword()) {
                    registerUser();
                }
            }
        };
    }

    public View.OnClickListener onExitClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationSubject.getInstance().onNext(null);
            }
        };
    }

    public View.OnClickListener onNamesNext() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateNames()) {
                    NavigationSubject.getFragmentNavigationInstance().onNext(new Pair<Class<? extends RTTFragment>, Bundle>(PasswordRegistrationFragment.class, null));
                }
            }
        };
    }

    public View.OnClickListener onInfoNext() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInfo()) {
                    NavigationSubject.getFragmentNavigationInstance().onNext(new Pair<Class<? extends RTTFragment>, Bundle>(UserNameRegistrationFragment.class, null));
                }
            }
        };
    }

    private boolean validateNames() {
        clearNamesErrors();

        firstNameError.set(RTTUtil.getRequiredFieldError(firstName.get()));
        lastNameError.set(RTTUtil.getRequiredFieldError(lastName.get()));

        return firstNameError.get() == 0 && lastNameError.get() == 0;
    }

    private boolean validateInfo() {
        clearInfoErrors();

        usernameError.set(RTTUtil.getRequiredFieldError(username.get()));
        emailError.set(RTTUtil.getRequiredFieldError(email.get()));

        return usernameError.get() == 0 && emailError.get() == 0;
    }

    private boolean validatePassword() {
        clearPasswordErrors();

        passwordError.set(RTTUtil.getPasswordError(password.get()));
        passwordConfirmationError.set(RTTUtil.getPasswordConfirmationError(password.get(), passwordConfirmation.get()));

        return passwordError.get() == 0 && passwordConfirmationError.get() == 0;
    }

    private void clearInfoErrors() {
        usernameError.set(0);
        emailError.set(0);
    }

    private void clearNamesErrors() {
        firstNameError.set(0);
        lastNameError.set(0);
    }

    private void clearPasswordErrors() {
        passwordError.set(0);
        passwordConfirmationError.set(0);
    }

    public void onRegistrationDone() {
        NavigationSubject.getInstance().onNext(new Pair<Class<? extends RTTActivity>, Bundle>(MainActivity.class, null));
    }

    public void onRequestCompleted() {
        requestInProgress.set(false);
    }


    private ObservableField<Boolean> requestInProgress = new ObservableField<>();

    public ObservableField<String> getUsername() {
        return username;
    }

    public void setUsername(ObservableField<String> username) {
        this.username = username;
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

    public ObservableField<String> getEmail() {
        return email;
    }

    public void setEmail(ObservableField<String> email) {
        this.email = email;
    }

    public ObservableField<Integer> getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(ObservableField<Integer> usernameError) {
        this.usernameError = usernameError;
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

    public ObservableField<Integer> getEmailError() {
        return emailError;
    }

    public void setEmailError(ObservableField<Integer> emailError) {
        this.emailError = emailError;
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

    public ObservableField<Boolean> getRequestInProgress() {
        return requestInProgress;
    }

    public void setRequestInProgress(ObservableField<Boolean> requestInProgress) {
        this.requestInProgress = requestInProgress;
    }
}
