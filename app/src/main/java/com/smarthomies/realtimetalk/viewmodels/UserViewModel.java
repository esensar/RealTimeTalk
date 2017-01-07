package com.smarthomies.realtimetalk.viewmodels;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.database.UserDAO;
import com.smarthomies.realtimetalk.managers.ContactsManager;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.views.activities.CallActivity;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ensar on 15/11/16.
 */
public class UserViewModel extends BaseObservable {
    public static final String TAG = UserViewModel.class.getSimpleName();

    private User model;
    private ObservableBoolean state = new ObservableBoolean();

    public UserViewModel() {
        this.model = new User();
    }

    public UserViewModel(User model) {
        setModel(model);
    }

    public void setModel(User model) {
        this.model = model;
        if (model == null) {
            this.model = new User();
        }

        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            if (new UserDAO().findUserById(realm, this.model.getId()) != null) {
                state.set(true);
            } else {
                state.set(false);
            }
        } finally {
            if(realm != null) {
                realm.close();
            }
        }

        notifyChange();
    }

    @Bindable
    public String getName() {
        return model.getFirstName() + " " + model.getLastName();
    }

    @Bindable
    public String getEmail() {
        return model.getEmail();
    }

    @Bindable
    public String getImageUrl() {
        return model.getImageUrl();
    }

    public ObservableBoolean getState() {
        return state;
    }

    public View.OnClickListener changeContactState() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Object> objectObservable = state.get()
                        ? new ContactsManager().deleteContact(model)
                        : new ContactsManager().saveContact(model);

                final Subscription subscription = objectObservable
                        .delaySubscription(3000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                state.set(!state.get());
                            }

                            @Override
                            public void onNext(Object o) {

                            }
                        });
                Log.d(TAG, "onClick: ");
                state.set(!state.get());
                String action = state.get() ? "added" : "removed";
                Snackbar.make(v, model.getFirstName() + " " + model.getLastName() + " " + action + " to contacts.", Snackbar.LENGTH_LONG)
                        .setDuration(3000)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(subscription != null && !subscription.isUnsubscribed()) {
                                    subscription.unsubscribe();
                                    state.set(!state.get());
                                }
                            }
                        }).show();
            }
        };
    }

    public View.OnClickListener call() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), CallActivity.class));
            }
        };
    }

}
