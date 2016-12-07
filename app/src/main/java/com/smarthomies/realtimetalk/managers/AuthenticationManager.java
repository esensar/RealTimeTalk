package com.smarthomies.realtimetalk.managers;

import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.models.network.AuthenticationResponse;
import com.smarthomies.realtimetalk.models.network.LoginRequest;
import com.smarthomies.realtimetalk.models.network.RegistrationRequest;
import com.smarthomies.realtimetalk.services.AuthenticationAPIService;
import com.smarthomies.realtimetalk.utils.RTTAppHelper;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by ensar on 01/11/16.
 */
public class AuthenticationManager {
    public static final String TAG = AuthenticationManager.class.getSimpleName();

    public Observable<AuthenticationResponse> loginUser(String username, String password) {
        return AuthenticationAPIService.getInstance().login(getLoginRequest(username, password)).doOnNext(processAuthenticationResponse);
    }

    public Observable<AuthenticationResponse> registerUser(User user, final String username, final String password) {
        RegistrationRequest registrationRequest = getRegistrationRequest(user, username, password);
        return AuthenticationAPIService.getInstance().register(registrationRequest)
                .flatMap(new Func1<AuthenticationResponse, Observable<LoginRequest>>() {
                    @Override
                    public Observable<LoginRequest> call(AuthenticationResponse authenticationResponse) {
                        return Observable.just(getLoginRequest(username, password));
                    }
                })
                .flatMap(requestLogin).doOnNext(processAuthenticationResponse);
    }

    private Func1<LoginRequest, Observable<AuthenticationResponse>> requestLogin = new Func1<LoginRequest, Observable<AuthenticationResponse>>() {
        @Override
        public Observable<AuthenticationResponse> call(LoginRequest request) {
            return AuthenticationAPIService.getInstance().login(request);
        }
    };

    private Action1<AuthenticationResponse> processAuthenticationResponse = new Action1<AuthenticationResponse>() {
        @Override
        public void call(AuthenticationResponse authenticationResponse) {
            RTTAppHelper.getInstance().saveToken(authenticationResponse.getToken());
        }
    };

    private RegistrationRequest getRegistrationRequest(User user, String username, String password) {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername(username);
        registrationRequest.setPassword(password);
        registrationRequest.setEmail(user.getEmail());
        registrationRequest.setFirstName(user.getFirstName());
        registrationRequest.setLastName(user.getLastName());
        return registrationRequest;
    }

    private LoginRequest getLoginRequest(String username, String password) {
        return new LoginRequest(username, password);
    }
}
