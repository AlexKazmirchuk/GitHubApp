package com.alexkaz.githubapp.presenter;

import com.alexkaz.githubapp.model.entities.RepoEntity;
import com.alexkaz.githubapp.model.entities.UserEntity;
import com.alexkaz.githubapp.model.services.ConnInfoHelper;
import com.alexkaz.githubapp.model.services.GitHubService;
import com.alexkaz.githubapp.model.services.RealmHelper;
import com.alexkaz.githubapp.view.UserReposView;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class UserReposPresenterImpl implements UserReposPresenter {

    private static final int PER_PAGE = 10;

    private UserReposView view;

    private GitHubService gitHubService;
    private ConnInfoHelper helper;
    private RealmHelper realmHelper;

    private String userName;
    private int page = 1;

    private boolean userInfoLoaded;
    private boolean repoListLoaded;

    private Disposable disposable;

    public UserReposPresenterImpl(GitHubService gitHubService, ConnInfoHelper helper, RealmHelper realmHelper) {
        this.gitHubService = gitHubService;
        this.helper = helper;
        this.realmHelper = realmHelper;
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
    public void reload() {
        if (helper.isOnline()){
            view.clearUpList();
            if (disposable != null ){
                if (!disposable.isDisposed()){
                    disposable.dispose();
                }
            }
            if (!userInfoLoaded){
                loadUserInfo();
            }
            page = 1;
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
            disposable = gitHubService.getUserRepos(userName ,page, PER_PAGE).subscribe(repos -> {
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

    @Override
    public void save(UserEntity user, List<RepoEntity> repos) {
        realmHelper.deleteAllUserRepositories(user);
        realmHelper.saveUser(user);
        realmHelper.saveUserRepos(user, repos);
    }

    @Override
    public void restore() {
        UserEntity user;
        List<RepoEntity> repos;

        user = realmHelper.getUserByName(userName);
        if (user != null){
            view.showUserInfo(user);
            userInfoLoaded = true;
            repos = realmHelper.getUserRepos(user);
            if (!repos.isEmpty()){
                view.showRepos(repos);
                repoListLoaded  = true;
                page = repos.size()/PER_PAGE + 1;
            }
        } else {
            loadNextPage();
        }
    }
}
