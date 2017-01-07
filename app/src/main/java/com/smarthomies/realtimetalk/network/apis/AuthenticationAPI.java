package com.smarthomies.realtimetalk.network.apis;

import com.smarthomies.realtimetalk.models.network.AuthenticationResponse;
import com.smarthomies.realtimetalk.models.network.LoginRequest;
import com.smarthomies.realtimetalk.models.network.RegistrationRequest;
import com.smarthomies.realtimetalk.network.NetworkingConstants;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ensar on 31/10/16.
 */

public interface AuthenticationAPI {

    @POST(NetworkingConstants.API_LOGIN_ENDPOINT)
    Observable<AuthenticationResponse> login(@Body LoginRequest request);

    @POST(NetworkingConstants.API_REGISTRATION_ENDPOINT)
    Observable<AuthenticationResponse> register(@Body RegistrationRequest request);

    @DELETE(NetworkingConstants.API_LOGOUT_ENDPOINT)
    Observable<Object> logout();

}
