package com.smarthomies.realtimetalk.database;

import com.smarthomies.realtimetalk.models.db.User;

import io.realm.Realm;

/**
 * Created by ensar on 12/12/16.
 */
public class UserDAO extends RealmDAO<User> {
    public static final String TAG = UserDAO.class.getSimpleName();

    public UserDAO() {
        super(User.class);
    }

    public User findUserById(Realm realm, int id) {
        User user = realm.where(entityClass).equalTo(User.PRIMARY_KEY, id).findFirst();
        if(user != null) {
            return realm.copyFromRealm(user);
        } else {
            return null;
        }
    }

    public void deleteById(Realm realm, int id) {
        realm.beginTransaction();
        realm.where(entityClass).equalTo(User.PRIMARY_KEY, id).findFirst().deleteFromRealm();
        realm.commitTransaction();
    }
}
