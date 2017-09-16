package com.alexkaz.githubapp.presenter;

import com.alexkaz.githubapp.model.services.ConnInfoHelper;
import com.alexkaz.githubapp.model.services.GitHubService;
import com.alexkaz.githubapp.view.UsersView;

import io.reactivex.disposables.Disposable;

public class UsersPresenterImpl implements UsersPresenter {

    private UsersView view;
    private GitHubService gitHubService;
    private ConnInfoHelper connInfoHelper;

    private int since = 0;

    private Disposable disposable;

    private boolean usersLoaded = false;

    public UsersPresenterImpl(GitHubService gitHubService, ConnInfoHelper connInfoHelper) {
        this.gitHubService = gitHubService;
        this.connInfoHelper = connInfoHelper;
    }

    @Override
    public void bindView(UsersView view) {
        this.view = view;
    }

    @Override
    public void refresh() {
        if (connInfoHelper.isOnline()){
            reset();
            view.clearUpList();
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
            if (!usersLoaded){
                view.showNoConnectionMessage();
            } else {
                view.showWarningMessage("No internet connection!");
            }
        }
    }

    @Override
    public void reset() {
        since = 0;
        usersLoaded = false;
        if (disposable != null ){
            if (!disposable.isDisposed()){
                disposable.dispose();
            }
        }
    }
}
