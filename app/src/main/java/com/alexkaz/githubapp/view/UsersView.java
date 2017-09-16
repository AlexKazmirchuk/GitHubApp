package com.alexkaz.githubapp.view;

import com.alexkaz.githubapp.model.entities.ShortUserEntity;

import java.util.List;

public interface UsersView extends BaseView {

    void showUsers(List<ShortUserEntity> users);

    void clearUpList();

    void showNoConnectionMessage();

    void hideNoConnectionMessage();

    void hideRepos();

}
