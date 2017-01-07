package com.smarthomies.realtimetalk.services;

import com.smarthomies.realtimetalk.models.network.PasswordChangeRequest;
import com.smarthomies.realtimetalk.models.network.RegistrationRequest;
import com.smarthomies.realtimetalk.models.network.UserResponse;
import com.smarthomies.realtimetalk.network.APIErrorHandler;
import com.smarthomies.realtimetalk.network.clients.RestClient;
import com.smarthomies.realtimetalk.network.exceptions.APIException;

import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Action1;

/**
 * Created by ensar on 31/10/16.
 */
public class AccountAPIService {
    public static final String TAG = AccountAPIService.class.getSimpleName();

    private static AccountAPIService instance;

    private AccountAPIService() {}

    public static AccountAPIService getInstance() {
        if(instance == null) {
            instance = new AccountAPIService();
        }
        return instance;
    }

    public Observable<UserResponse> getProfile() {
        return RestClient.getInstance().getAccountAPI().getProfile()
                .doOnError(handleGeneralErrors);
    }

    public Observable<Object> updateProfile(RegistrationRequest request) {
        return RestClient.getInstance().getAccountAPI().updateProfile(request)
                .doOnError(handleGeneralErrors);
    }

    public Observable<Object> changePassword(PasswordChangeRequest request) {
        return RestClient.getInstance().getAccountAPI().changePassword(request)
                .doOnError(handleGeneralErrors);
    }

    private Action1<Throwable> handleGeneralErrors = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            try {
                APIErrorHandler.handleGeneralAPIErrors(throwable);
            } catch (APIException apiException) {
                throw Exceptions.propagate(apiException);
            }
        }
    };
}
