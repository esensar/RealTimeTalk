package com.smarthomies.realtimetalk.managers;

import android.util.Log;

import com.smarthomies.realtimetalk.database.UserDAO;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.models.network.ContactRequest;
import com.smarthomies.realtimetalk.models.network.SearchRequest;
import com.smarthomies.realtimetalk.models.network.UsersResponse;
import com.smarthomies.realtimetalk.services.ContactsAPIService;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by ensar on 01/11/16.
 */
public class ContactsManager {
    public static final String TAG = ContactsManager.class.getSimpleName();

    public Observable<UsersResponse> searchForUsers(String searchString) {
        return ContactsAPIService.getInstance().search(getSearchRequest(searchString));
    }

    public Observable<UsersResponse> getContacts() {
        return Observable.just(getUsersFromDb()).concatWith(ContactsAPIService.getInstance().getContacts()
                    .doOnNext(saveContacts));
    }

    public Observable<Object> saveContact(User contact) {
        return ContactsAPIService.getInstance().saveContact(getContactRequest(contact))
                .doOnNext(new UpdateContactDb(contact, true));
    }

    public Observable<Object> deleteContact(User contact) {
        return ContactsAPIService.getInstance().deleteContact(getContactRequest(contact))
                .doOnNext(new UpdateContactDb(contact, false));
    }

    private SearchRequest getSearchRequest(String searchString) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearch(searchString);
        return searchRequest;
    }

    private ContactRequest getContactRequest(User user) {
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setUsername(user.getUsername());
        return contactRequest;
    }

    private UsersResponse getUsersFromDb() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            UsersResponse usersResponse = new UsersResponse();
            usersResponse.setData(new UserDAO().load(realm));
            return usersResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
        return null;
    }

    private Action1<UsersResponse> saveContacts = new Action1<UsersResponse>() {
        @Override
        public void call(UsersResponse usersResponse) {
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                new UserDAO().updateOrCreate(realm, usersResponse.getData(), true);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if(realm != null) {
                    realm.close();
                }
            }
        }
    };

    private class UpdateContactDb implements Action1<Object> {
        private User contact;
        private boolean add;

        public UpdateContactDb(User contact, boolean add) {
            this.contact = contact;
            this.add = add;
        }

        @Override
        public void call(Object o) {
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                if(add) {
                    new UserDAO().updateOrCreate(realm, contact);
                } else {
                    new UserDAO().deleteById(realm, contact.getId());
                }
            } finally {
                if(realm != null) {
                    realm.close();
                }
            }
        }
    }
}
