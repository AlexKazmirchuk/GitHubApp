package com.alexkaz.githubapp.model.services;

import com.alexkaz.githubapp.model.api.GitHubApi;
import com.alexkaz.githubapp.model.entities.RepoEntity;
import com.alexkaz.githubapp.model.entities.ShortUserEntity;
import com.alexkaz.githubapp.model.entities.UserEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GitHubService {

    private GitHubApi gitHubApi;

    @Inject
    public GitHubService(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    public Observable<List<ShortUserEntity>> getUsers(int since, int perPage){
        return gitHubApi.getUsers(since,perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<RepoEntity>> getUserRepos(String user, int page, int perPage){
        return gitHubApi.getUserRepos(user, page, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<UserEntity> getUser(String username){
        return gitHubApi.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
