package com.smarthomies.realtimetalk.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.smarthomies.realtimetalk.RTTActivity;
import com.smarthomies.realtimetalk.database.UserDAO;
import com.smarthomies.realtimetalk.managers.AccountManager;
import com.smarthomies.realtimetalk.managers.AuthenticationManager;
import com.smarthomies.realtimetalk.managers.ContactsManager;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.models.network.AuthenticationResponse;
import com.smarthomies.realtimetalk.models.network.UserResponse;
import com.smarthomies.realtimetalk.models.network.UsersResponse;
import com.smarthomies.realtimetalk.utils.NavigationSubject;
import com.smarthomies.realtimetalk.views.activities.LoginActivity;
import com.smarthomies.realtimetalk.views.activities.MainActivity;
import com.smarthomies.realtimetalk.views.activities.ProfileActivity;
import com.smarthomies.realtimetalk.views.activities.SearchActivity;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ensar on 15/11/16.
 */
public class MainViewModel extends BaseObservable {
    public static final String TAG = MainViewModel.class.getSimpleName();

    private AsyncSubject<Object> logoutSubject;

    private CompositeSubscription subscription;

    private ObservableField<List<User>> contacts = new ObservableField<>();

    private ObservableField<UserViewModel> userViewModel = new ObservableField<>();

    public MainViewModel() {
        logoutSubject = AsyncSubject.create();
        subscription = new CompositeSubscription();
        userViewModel.set(new UserViewModel());
    }

    public void loadContacts() {
        subscription.add(new ContactsManager().getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UsersResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UsersResponse usersResponse) {
                        contacts.set(usersResponse.getData());
                    }
                }));
    }

    public void loadProfile() {
        subscription.add(new AccountManager().getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        userViewModel.get().setModel(userResponse.getData());
                        userViewModel.notifyChange();
                    }
                }));
    }

    public AsyncSubject<Object> createLogoutSubject() {
        logoutSubject = AsyncSubject.create();
        return logoutSubject;
    }

    public AsyncSubject<Object> getLogoutSubject() {
        return logoutSubject;
    }

    private void logoutUser() {
        new AuthenticationManager().logout().subscribeOn(Schedulers.io()).subscribe(logoutSubject);
    }

    public ObservableField<List<User>> getContacts() {
        return contacts;
    }

    public void setContacts(ObservableField<List<User>> contacts) {
        this.contacts = contacts;
    }

    @Bindable
    public UserViewModel getUserViewModel() {
        return userViewModel.get();
    }

    public View.OnClickListener onSearchClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationSubject.getInstance().onNext(new Pair<Class<? extends RTTActivity>, Bundle>(SearchActivity.class, null));
            }
        };
    }

    public void onLogoutClick() {
        logoutUser();
    }

    public void onProfileClick() {
        NavigationSubject.getInstance().onNext(new Pair<Class<? extends RTTActivity>, Bundle>(ProfileActivity.class, null));
    }

    public void onLogoutDone() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            new UserDAO().deleteAll(realm);
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
        NavigationSubject.getInstance().onNext(new Pair<Class<? extends RTTActivity>, Bundle>(LoginActivity.class, null));
    }

    public void clear() {
        if(subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
