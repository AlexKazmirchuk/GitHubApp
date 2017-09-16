package com.alexkaz.githubapp.presenter;

import com.alexkaz.githubapp.view.UsersView;

public interface UsersPresenter  extends BasePresenter <UsersView> {

    void refresh();

    void loadNextPage();

    void reset();
}
