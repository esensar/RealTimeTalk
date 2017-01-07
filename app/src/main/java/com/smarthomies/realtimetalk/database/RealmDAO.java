package com.smarthomies.realtimetalk.database;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by ensar on 12/12/16.
 */
public class RealmDAO<T extends RealmObject> {
    public static final String TAG = RealmDAO.class.getSimpleName();

    protected Class<T> entityClass;

    public RealmDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> load(Realm realm) {
        RealmResults result = realm.where(entityClass).findAll();
        return realm.copyFromRealm(result);
    }

    public void cleanSave(Realm realm, List objects) {
        deleteAll(realm);

        realm.beginTransaction();
        for (Object realmObject : objects) {
            realm.copyToRealm((RealmObject) realmObject);
        }

        realm.commitTransaction();
    }

    public void save(Realm realm, List objects) {

        realm.beginTransaction();

        for (Object realmObject : objects) {
            realm.copyToRealm((RealmObject) realmObject);
        }

        realm.commitTransaction();
    }

    public void updateOrCreate(Realm realm, T object) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }

    public void updateOrCreate(Realm realm, List<? extends RealmObject> objects, boolean deleteIfMissing) {
        if(objects.isEmpty()) {
            return;
        }
        Class clazz = objects.get(0).getClass();
        realm.beginTransaction();
        List liveObjects = realm.copyToRealmOrUpdate(objects);

        // If true, all objects in DB which are not updated or added will be deleted
        if(deleteIfMissing) {
            RealmResults allDbObjects = realm.where(entityClass).findAll();
            for(int i=0; i<allDbObjects.size(); i++) {
                if(!liveObjects.contains(allDbObjects.get(i))) {
                    allDbObjects.deleteFromRealm(i);
                }
            }
        }
        realm.commitTransaction();
    }

    public void deleteAll(Realm realm) {
        realm.beginTransaction();
        realm.where(entityClass).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }
}
