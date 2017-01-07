package com.smarthomies.realtimetalk.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.util.Log;

import com.smarthomies.realtimetalk.managers.ContactsManager;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.models.network.UsersResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ensar on 15/11/16.
 */
public class SearchViewModel extends BaseObservable {
    public static final String TAG = SearchViewModel.class.getSimpleName();

    private ObservableField<List<User>> users = new ObservableField<>();
    private ObservableField<String> search = new ObservableField<>();

    private rx.Observable<String> rxSearch;
    private Subscription subscription;

    public SearchViewModel() {
        rxSearch = rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                search.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable observable, int i) {
                        String query = ((ObservableField<String>) observable).get();
                        if(subscriber.isUnsubscribed()) {

                        } else {
                            subscriber.onNext(query);
                        }
                    }
                });
            }
        });
        rxSearch = rxSearch.debounce(500, TimeUnit.MILLISECONDS);
        subscription = rxSearch.flatMap(new Func1<String, rx.Observable<UsersResponse>>() {
            @Override
            public rx.Observable<UsersResponse> call(String s) {
                return new ContactsManager().searchForUsers(s);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<UsersResponse>() {
            @Override
            public void call(UsersResponse usersResponse) {
                users.set(usersResponse.getData());
            }
        });
    }

    public ObservableField<List<User>> getUsers() {
        return users;
    }

    public void setUsers(ObservableField<List<User>> users) {
        this.users = users;
    }

    public ObservableField<String> getSearch() {
        return search;
    }

    public void setSearch(ObservableField<String> search) {
        this.search = search;
    }

    public void clear() {
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
