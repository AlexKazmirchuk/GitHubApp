package com.alexkaz.githubapp.presenter;

import com.alexkaz.githubapp.view.UserReposView;

public interface UserReposPresenter extends BasePresenter <UserReposView> {

    void setUserName(String userName);

    void refresh();

    void loadNextPage();

    void loadUserInfo();

    void reset();

}
