package com.smarthomies.realtimetalk.services;

import com.smarthomies.realtimetalk.models.network.ContactRequest;
import com.smarthomies.realtimetalk.models.network.SearchRequest;
import com.smarthomies.realtimetalk.models.network.UsersResponse;
import com.smarthomies.realtimetalk.network.APIErrorHandler;
import com.smarthomies.realtimetalk.network.clients.RestClient;
import com.smarthomies.realtimetalk.network.exceptions.APIException;

import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Action1;

/**
 * Created by ensar on 31/10/16.
 */
public class ContactsAPIService {
    public static final String TAG = ContactsAPIService.class.getSimpleName();

    private static ContactsAPIService instance;

    private ContactsAPIService() {}

    public static ContactsAPIService getInstance() {
        if(instance == null) {
            instance = new ContactsAPIService();
        }
        return instance;
    }

    public Observable<UsersResponse> search(SearchRequest request) {
        return RestClient.getInstance().getContactsAPI().searchUsers(request)
                .doOnError(handleApiErrors);
    }

    public Observable<UsersResponse> getContacts() {
        return RestClient.getInstance().getContactsAPI().getContacts()
                .doOnError(handleApiErrors);
    }

    public Observable<Object> saveContact(ContactRequest request) {
        return RestClient.getInstance().getContactsAPI().saveContact(request)
                .doOnError(handleApiErrors);
    }

    public Observable<Object> deleteContact(ContactRequest request) {
        return RestClient.getInstance().getContactsAPI().deleteContact(request)
                .doOnError(handleApiErrors);
    }

    private Action1<Throwable> handleApiErrors = new Action1<Throwable>() {
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
