package com.alexkaz.githubapp.model.services;

import com.alexkaz.githubapp.model.entities.RepoEntity;
import com.alexkaz.githubapp.model.entities.ShortUserEntity;
import com.alexkaz.githubapp.model.entities.UserEntity;

import java.util.List;

public interface RealmHelper {

    void updateShortUser(int userId, int changesCount);

    void saveAllShortUsers(List<ShortUserEntity> users);

    void saveUser(UserEntity user);

    void saveUserRepos(UserEntity user, List<RepoEntity> repos);

    List<ShortUserEntity> getAllShortUsers();

    UserEntity getUserByName(String userName);

    List<RepoEntity> getUserRepos(UserEntity user);

    int getLastShortUserID();

    void deleteAllShortUsers();

    void deleteUserByName(String userName);

    void deleteAllUserRepositories(UserEntity user);

}
