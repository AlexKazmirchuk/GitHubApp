package com.alexkaz.githubapp.presenter;

import com.alexkaz.githubapp.model.services.ConnInfoHelper;
import com.alexkaz.githubapp.model.services.GitHubService;
import com.alexkaz.githubapp.view.UserReposView;

import io.reactivex.disposables.Disposable;

public class UserReposPresenterImpl implements UserReposPresenter {

    private UserReposView view;
    private GitHubService gitHubService;
    private ConnInfoHelper helper;

    private String userName;
    private int page = 1;

    private boolean userInfoLoaded;
    private boolean repoListLoaded;

    private Disposable disposable;

    public UserReposPresenterImpl(GitHubService gitHubService, ConnInfoHelper connInfoHelper) {
        this.gitHubService = gitHubService;
        this.helper = connInfoHelper;
    }

    @Override
    public void bindView(UserReposView view) {
        this.view = view;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void refresh() {
        if (helper.isOnline()){
            if (disposable != null ){
                if (!disposable.isDisposed()){
                    disposable.dispose();
                }
            }
            if (!userInfoLoaded){
                loadUserInfo();
            }
            page = 1;
            view.clearUpList();
            view.hideRepos();
            loadNextPage();
        } else {
            view.showWarningMessage("No internet connection!");
        }
    }

    @Override
    public void loadNextPage() {
        if (helper.isOnline()){
            view.hideNoConnectionMessage();
            view.showLoading();
            int perPage = 8;
            disposable = gitHubService.getUserRepos(userName ,page, perPage).subscribe(repos -> {
                repoListLoaded = true;
                view.showRepos(repos);
                page++;
                if (userInfoLoaded){
                    view.hideLoading();
                } else {
                    view.hideRepos();
                }
            }, throwable -> {
                view.hideLoading();
                view.showWarningMessage(throwable.getMessage());
            });
        } else {
            if (!userInfoLoaded && !repoListLoaded){
                view.showNoConnectionMessage();
            } else {
                view.showWarningMessage("No internet connection!");
            }
        }
    }

    @Override
    public void loadUserInfo() {
        if (helper.isOnline()){
            view.hideNoConnectionMessage();
            view.showLoading();
            gitHubService.getUser(userName).subscribe(user -> {
                view.showUserInfo(user);
                userInfoLoaded = true;
                if (repoListLoaded){
                    view.hideLoading();
                    view.showRepos(null);
                }
            }, throwable -> {
                view.hideLoading();
                view.showWarningMessage(throwable.getMessage());
            });
        } else {
            if (!userInfoLoaded && !repoListLoaded){
                view.showNoConnectionMessage();
            } else {
                view.showWarningMessage("No internet connection!");
            }
        }
    }

    @Override
    public void reset() {
        page = 1;
        userName = null;
        userInfoLoaded = false;
        repoListLoaded = false;
        if (disposable != null ){
            if (!disposable.isDisposed()){
                disposable.dispose();
            }
        }
    }
}
