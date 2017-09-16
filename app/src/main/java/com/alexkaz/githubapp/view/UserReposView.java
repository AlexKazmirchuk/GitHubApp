package com.alexkaz.githubapp.view;

import com.alexkaz.githubapp.model.entities.RepoEntity;
import com.alexkaz.githubapp.model.entities.UserEntity;

import java.util.List;

public interface UserReposView extends BaseView {

    void showUserInfo(UserEntity user);

    void showRepos(List<RepoEntity> userRepos);

    void clearUpList();

    void showNoConnectionMessage();

    void hideNoConnectionMessage();

    void hideRepos();

}
