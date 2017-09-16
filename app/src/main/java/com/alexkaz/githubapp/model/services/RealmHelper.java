package com.alexkaz.githubapp.model.services;

import com.alexkaz.githubapp.model.entities.RepoEntity;
import com.alexkaz.githubapp.model.entities.ShortUserEntity;
import com.alexkaz.githubapp.model.entities.UserEntity;

import java.util.List;

public interface RealmHelper {

    void saveAllUsers(List<ShortUserEntity> users);

    void saveUser(UserEntity user);

    void saveUserRepos(UserEntity user, List<RepoEntity> repos);

    List<ShortUserEntity> getAllUsers();

    UserEntity getUserByName(String userName);

    List<RepoEntity> getUserRepos(UserEntity user);

    int getLastUserID();

    void deleteAllShortUsers();

}
