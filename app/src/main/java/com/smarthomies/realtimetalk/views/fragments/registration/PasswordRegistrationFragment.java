package com.smarthomies.realtimetalk.views.fragments.registration;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.databinding.PasswordRegistrationBinding;
import com.smarthomies.realtimetalk.models.network.AuthenticationResponse;
import com.smarthomies.realtimetalk.utils.RTTErrorUtil;
import com.smarthomies.realtimetalk.views.activities.bindingutils.OnErrorChangedCallback;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.CompositeException;
import rx.schedulers.Schedulers;

/**
 * Created by ensar on 08/11/16.
 */
public class PasswordRegistrationFragment extends RegistrationFragment {
    public static final String TAG = PasswordRegistrationFragment.class.getSimpleName();

    private Subscription registrationSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PasswordRegistrationBinding binding = DataBindingUtil.inflate(inflater, R.layout.password_registration, container, false);
        View view = binding.getRoot();
        binding.setViewModel(getViewModel());

        getViewModel().getPasswordError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilPassword));
        getViewModel().getPasswordConfirmationError().addOnPropertyChangedCallback(new OnErrorChangedCallback(binding.tilConfirmPassword));

        return view;
    }

    @Override
    protected void subscribeToSubjects() {
        super.subscribeToSubjects();
        registrationSubscription = getViewModel().getRegistrationSubject().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new RegistrationSubscriber());
    }

    @Override
    protected void unsubscribeFromSubjects() {
        super.unsubscribeFromSubjects();
        if(registrationSubscription != null && !registrationSubscription.isUnsubscribed()) {
            registrationSubscription.unsubscribe();
        }
    }

    private void reconnectToRegistrationSubject() {
        registrationSubscription = getViewModel().createRegistrationSubject().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new RegistrationSubscriber());
    }

    private void handleException(Throwable e) {
        Toast.makeText(getContext(), RTTErrorUtil.getErrorString(e), Toast.LENGTH_SHORT).show();
    }

    private class RegistrationSubscriber extends Subscriber<AuthenticationResponse> {
        @Override
        public void onCompleted() {
            reconnectToRegistrationSubject();
            getViewModel().onRequestCompleted();
        }

        @Override
        public void onError(Throwable e) {
            reconnectToRegistrationSubject();
            getViewModel().onRequestCompleted();

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
            getViewModel().onRegistrationDone();
        }
    }
}
