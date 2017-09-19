package com.alexkaz.githubapp.presenter;

import com.alexkaz.githubapp.model.entities.RepoEntity;
import com.alexkaz.githubapp.model.entities.UserEntity;
import com.alexkaz.githubapp.view.UserReposView;

import java.util.List;

public interface UserReposPresenter extends BasePresenter <UserReposView> {

    void setUserName(String userName);

    void reload();

    void loadNextPage();

    void loadUserInfo();

    void reset();

    void save(UserEntity user, List<RepoEntity> repos);

    void restore();

}
