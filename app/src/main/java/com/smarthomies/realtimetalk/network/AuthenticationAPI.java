package com.smarthomies.realtimetalk.network;

import com.smarthomies.realtimetalk.models.network.AuthenticationResponse;
import com.smarthomies.realtimetalk.models.network.LoginRequest;
import com.smarthomies.realtimetalk.models.network.RegistrationRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * Created by ensar on 31/10/16.
 */

public interface AuthenticationAPI {

    @POST(NetworkingConstants.API_LOGIN_ENDPOINT)
    Observable<AuthenticationResponse> login(@Body LoginRequest request);

    @PUT(NetworkingConstants.API_REGISTRATION_ENDPOINT)
    Observable<AuthenticationResponse> register(@Body RegistrationRequest request);

}
