package com.alexkaz.githubapp.di.modules;

import com.alexkaz.githubapp.model.services.ConnInfoHelper;
import com.alexkaz.githubapp.model.services.GitHubService;
import com.alexkaz.githubapp.presenter.UserReposPresenter;
import com.alexkaz.githubapp.presenter.UserReposPresenterImpl;
import com.alexkaz.githubapp.presenter.UsersPresenter;
import com.alexkaz.githubapp.presenter.UsersPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Singleton
    @Provides
    UserReposPresenter provideUserReposPresenter(GitHubService gitHubService, ConnInfoHelper connInfoHelper){
        return new UserReposPresenterImpl(gitHubService, connInfoHelper);
    }

    @Singleton
    @Provides
    UsersPresenter provideUsersPresenter(GitHubService gitHubService, ConnInfoHelper connInfoHelper){
        return new UsersPresenterImpl(gitHubService, connInfoHelper);
    }
}
