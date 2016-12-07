package com.smarthomies.realtimetalk.managers;

import com.smarthomies.realtimetalk.models.network.SearchRequest;
import com.smarthomies.realtimetalk.models.network.UsersResponse;
import com.smarthomies.realtimetalk.services.ContactsAPIService;

import rx.Observable;

/**
 * Created by ensar on 01/11/16.
 */
public class ContactsManager {
    public static final String TAG = ContactsManager.class.getSimpleName();

    public Observable<UsersResponse> searchForUsers(String searchString) {
        return ContactsAPIService.getInstance().search(getSearchRequest(searchString));
    }

    private SearchRequest getSearchRequest(String searchString) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearch(searchString);
        return searchRequest;
    }
}
