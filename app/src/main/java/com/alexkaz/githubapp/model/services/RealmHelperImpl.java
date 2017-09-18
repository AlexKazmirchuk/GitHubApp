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
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    @Override
    public void saveUserRepos(UserEntity user, List<RepoEntity> repos) {
        for (RepoEntity repo : repos) {
            repo.setUserName(user.getLogin());
        }
        realm.beginTransaction();
        realm.copyToRealm(repos);
        realm.commitTransaction();
    }

    @Override
    public List<ShortUserEntity> getAllUsers() {
        return realm.copyFromRealm(realm.where(ShortUserEntity.class).findAll());
    }

    @Override
    public UserEntity getUserByName(String userName) {
        return realm.where(UserEntity.class).equalTo("login",userName).findFirst();
    }

    @Override
    public List<RepoEntity> getUserRepos(UserEntity user) {
        RealmResults<RepoEntity> repos = realm.where(RepoEntity.class)
                                              .equalTo("userName", user.getLogin())
                                              .findAll();
        return realm.copyFromRealm(repos);
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

    @Override
    public void deleteUserByName(String userName) {
        realm.beginTransaction();
        realm.where(UserEntity.class).equalTo("login",userName).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void deleteAllUserRepositories(UserEntity user) {
        realm.beginTransaction();
        realm.where(RepoEntity.class).equalTo("userName", user.getLogin()).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }
}
