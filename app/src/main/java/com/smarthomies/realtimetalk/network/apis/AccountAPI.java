package com.smarthomies.realtimetalk.network.apis;

import com.smarthomies.realtimetalk.models.network.ContactRequest;
import com.smarthomies.realtimetalk.models.network.PasswordChangeRequest;
import com.smarthomies.realtimetalk.models.network.RegistrationRequest;
import com.smarthomies.realtimetalk.models.network.SearchRequest;
import com.smarthomies.realtimetalk.models.network.UserResponse;
import com.smarthomies.realtimetalk.models.network.UsersResponse;
import com.smarthomies.realtimetalk.network.NetworkingConstants;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * Created by ensar on 15/11/16.
 */

public interface AccountAPI {

    @GET(NetworkingConstants.API_PROFILE_ENDPOINT)
    Observable<UserResponse> getProfile();

    @PUT(NetworkingConstants.API_PROFILE_ENDPOINT)
    Observable<Object> updateProfile(@Body RegistrationRequest request);

    @PUT(NetworkingConstants.API_PASSWORD_UPDATE_ENDPOINT)
    Observable<Object> changePassword(@Body PasswordChangeRequest request);
}
