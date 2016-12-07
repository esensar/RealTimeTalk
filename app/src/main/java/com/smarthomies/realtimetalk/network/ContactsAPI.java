package com.smarthomies.realtimetalk.network;

import com.smarthomies.realtimetalk.models.network.SearchRequest;
import com.smarthomies.realtimetalk.models.network.UsersResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ensar on 15/11/16.
 */

public interface ContactsAPI {

    @POST(NetworkingConstants.API_SEARCH_ENDPOINT)
    Observable<UsersResponse> searchUsers(@Body SearchRequest searchRequest);

}
