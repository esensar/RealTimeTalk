package com.smarthomies.realtimetalk.managers;

import com.smarthomies.realtimetalk.database.UserDAO;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.models.network.PasswordChangeRequest;
import com.smarthomies.realtimetalk.models.network.RegistrationRequest;
import com.smarthomies.realtimetalk.models.network.UserResponse;
import com.smarthomies.realtimetalk.models.network.UsersResponse;
import com.smarthomies.realtimetalk.services.AccountAPIService;
import com.smarthomies.realtimetalk.utils.RTTAppHelper;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by ensar on 01/11/16.
 */
public class AccountManager {
    public static final String TAG = AccountManager.class.getSimpleName();

    public Observable<UserResponse> getProfile() {
        return Observable.just(getUserFromDb()).concatWith(AccountAPIService.getInstance().getProfile()
                .doOnNext(saveProfile));
    }

    public Observable<Object> updateProfile(User newProfile) {
        return AccountAPIService.getInstance().updateProfile(getUpdateRequest(newProfile))
                .doOnNext(new UpdateProfileDb(newProfile));
    }

    public Observable<Object> changePassword(String oldPassword, String newPassword) {
        return AccountAPIService.getInstance().changePassword(getPasswordChangeRequest(oldPassword, newPassword));
    }

    private PasswordChangeRequest getPasswordChangeRequest(String oldPassword, String newPassword) {
        PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest();
        passwordChangeRequest.setCurrentPassword(oldPassword);
        passwordChangeRequest.setNewPassword(newPassword);
        return passwordChangeRequest;
    }

    private RegistrationRequest getUpdateRequest(User user) {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmail(user.getEmail());
        registrationRequest.setFirstName(user.getFirstName());
        registrationRequest.setLastName(user.getLastName());
        return registrationRequest;
    }

    private Action1<UserResponse> saveProfile = new Action1<UserResponse>() {
        @Override
        public void call(UserResponse userResponse) {
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                new UserDAO().updateOrCreate(realm, userResponse.getData());
            } finally {
                if(realm != null) {
                    realm.close();
                }
            }
        }
    };

    private UserResponse getUserFromDb() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            UserResponse userResponse = new UserResponse();
            userResponse.setData(new UserDAO().findUserById(realm, RTTAppHelper.getInstance().getUserId()));
            return userResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
        return null;
    }

    private class UpdateProfileDb implements Action1<Object> {
        private User contact;

        public UpdateProfileDb(User contact) {
            this.contact = contact;
        }

        @Override
        public void call(Object o) {
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                new UserDAO().updateOrCreate(realm, contact);
            } finally {
                if(realm != null) {
                    realm.close();
                }
            }
        }
    }
}
