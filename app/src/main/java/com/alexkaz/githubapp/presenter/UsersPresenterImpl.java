package com.alexkaz.githubapp.presenter;

import com.alexkaz.githubapp.model.entities.ShortUserEntity;
import com.alexkaz.githubapp.model.services.ConnInfoHelper;
import com.alexkaz.githubapp.model.services.GitHubService;
import com.alexkaz.githubapp.model.services.RealmHelper;
import com.alexkaz.githubapp.view.UsersView;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class UsersPresenterImpl implements UsersPresenter {

    private UsersView view;
    private GitHubService gitHubService;
    private ConnInfoHelper connInfoHelper;
    private RealmHelper realmHelper;

    private int since = 0;

    private Disposable disposable;

    public UsersPresenterImpl(GitHubService gitHubService, ConnInfoHelper connInfoHelper, RealmHelper realmHelper) {
        this.gitHubService = gitHubService;
        this.connInfoHelper = connInfoHelper;
        this.realmHelper = realmHelper;
    }

    @Override
    public void bindView(UsersView view) {
        this.view = view;
    }

    @Override
    public void reload() {
        if (connInfoHelper.isOnline()){
            view.clearUpList();
            reset();
            view.hideRepos();
            loadNextPage();
        } else {
            view.showWarningMessage("No internet connection!");
        }
    }

    @Override
    public void loadNextPage() {
        if (connInfoHelper.isOnline()){
            view.hideNoConnectionMessage();
            view.showLoading();
            int perPage = 10;
            disposable = gitHubService.getUsers(since,perPage).subscribe(users -> {
                view.hideLoading();
                view.showUsers(users);
                if (users.size() > 1)
                    since = users.get(users.size()-1).getId();
            }, throwable -> {
                view.hideLoading();
                view.showWarningMessage(throwable.getMessage());
            });
        } else {
            if (realmHelper.getAllShortUsers().isEmpty()){
                view.showNoConnectionMessage();
            } else {
                view.showWarningMessage("No internet connection!");
            }
        }
    }

    @Override
    public void reset() {
        since = 0;
        if (disposable != null ){
            if (!disposable.isDisposed()){
                disposable.dispose();
            }
        }
    }

    @Override
    public void saveState(List<ShortUserEntity> users) {
        realmHelper.deleteAllShortUsers();
        realmHelper.saveAllShortUsers(users);
    }

    @Override
    public void restoreState() {
        since = realmHelper.getLastShortUserID();
        if (!realmHelper.getAllShortUsers().isEmpty()){
            view.showUsers(realmHelper.getAllShortUsers());
        } else {
            loadNextPage();
        }
    }
}
