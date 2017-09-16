package com.alexkaz.githubapp.presenter;

import com.alexkaz.githubapp.model.entities.ShortUserEntity;
import com.alexkaz.githubapp.view.UsersView;

import java.util.List;

public interface UsersPresenter  extends BasePresenter <UsersView> {

    void refresh();

    void loadNextPage();

    void reset();

    void saveState(List<ShortUserEntity> users);

    void restoreState();
}
