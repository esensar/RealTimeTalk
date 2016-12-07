package com.smarthomies.realtimetalk.services;

import com.smarthomies.realtimetalk.models.network.AuthenticationResponse;
import com.smarthomies.realtimetalk.models.network.LoginRequest;
import com.smarthomies.realtimetalk.models.network.RegistrationRequest;
import com.smarthomies.realtimetalk.network.APIErrorHandler;
import com.smarthomies.realtimetalk.network.RestClient;
import com.smarthomies.realtimetalk.network.exceptions.APIException;

import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Action1;

/**
 * Created by ensar on 31/10/16.
 */
public class AuthenticationAPIService {
    public static final String TAG = AuthenticationAPIService.class.getSimpleName();

    private static AuthenticationAPIService instance;

    private AuthenticationAPIService() {}

    public static AuthenticationAPIService getInstance() {
        if(instance == null) {
            instance = new AuthenticationAPIService();
        }
        return instance;
    }

    public Observable<AuthenticationResponse> login(LoginRequest request) {
        return RestClient.getInstance().getAuthenticationAPI().login(request)
                .doOnError(handleLoginErrors);
    }

    public Observable<AuthenticationResponse> register(RegistrationRequest request) {
        return RestClient.getInstance().getAuthenticationAPI().register(request)
                .doOnError(handleRegistrationErrors);
    }

    private Action1<Throwable> handleLoginErrors = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            try {
                APIErrorHandler.handleLoginErrors(throwable);
            } catch (APIException apiException) {
                throw Exceptions.propagate(apiException);
            }
        }
    };

    private Action1<Throwable> handleRegistrationErrors = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            try {
                APIErrorHandler.handleRegistrationErrors(throwable);
            } catch (APIException apiException) {
                throw Exceptions.propagate(apiException);
            }
        }
    };
}
