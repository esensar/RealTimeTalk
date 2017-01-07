package com.smarthomies.realtimetalk.network.apis;

import com.smarthomies.realtimetalk.models.network.ContactRequest;
import com.smarthomies.realtimetalk.models.network.SearchRequest;
import com.smarthomies.realtimetalk.models.network.UsersResponse;
import com.smarthomies.realtimetalk.network.NetworkingConstants;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ensar on 15/11/16.
 */

public interface ContactsAPI {

    @POST(NetworkingConstants.API_SEARCH_ENDPOINT)
    Observable<UsersResponse> searchUsers(@Body SearchRequest searchRequest);

    @GET(NetworkingConstants.API_CONTACTS_ENDPOINT)
    Observable<UsersResponse> getContacts();

    @POST(NetworkingConstants.API_CONTACTS_ENDPOINT)
    Observable<Object> saveContact(@Body ContactRequest contactRequest);

    @DELETE(NetworkingConstants.API_CONTACTS_DELETE_ENDPOINT)
    Observable<Object> deleteContact(@Path("id") String id);

}
