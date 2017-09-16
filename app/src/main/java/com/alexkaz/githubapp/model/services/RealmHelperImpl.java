package com.alexkaz.githubapp.model.services;

import android.content.Context;

import com.alexkaz.githubapp.model.entities.RepoEntity;
import com.alexkaz.githubapp.model.entities.ShortUserEntity;
import com.alexkaz.githubapp.model.entities.UserEntity;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelperImpl implements RealmHelper {

    private Realm realm;

    public RealmHelperImpl(Context context) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void saveAllUsers(List<ShortUserEntity> users) {
        realm.beginTransaction();
        realm.insert(users);
        realm.commitTransaction();
    }

    @Override
    public void saveUser(UserEntity user) {
        realm.beginTransaction();
        //realm.copyToRealm(user); // todo extend UserEntity to RealmObject
        realm.commitTransaction();
    }

    @Override
    public void saveUserRepos(UserEntity user, List<RepoEntity> repos) {
        // todo impl later
    }

    @Override
    public List<ShortUserEntity> getAllUsers() {
        return realm.copyFromRealm(realm.where(ShortUserEntity.class).findAll());
    }

    @Override
    public UserEntity getUserByName(String userName) {
        // todo impl later
        return null;
    }

    @Override
    public List<RepoEntity> getUserRepos(UserEntity user) {
        // todo impl later
        return null;
    }

    @Override
    public int getLastUserID() {
        RealmResults<ShortUserEntity> result = realm.where(ShortUserEntity.class).findAll();
        result = result.sort("id");
        if (result.size() >= 1)
            return result.last().getId();
        else
            return 0;
    }

    @Override
    public void deleteAllShortUsers() {
        realm.beginTransaction();
        realm.where(ShortUserEntity.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }
}
