package com.alexkaz.githubapp.di.modules;

import com.alexkaz.githubapp.model.services.ConnInfoHelper;
import com.alexkaz.githubapp.model.services.GitHubService;
import com.alexkaz.githubapp.model.services.RealmHelper;
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
    UserReposPresenter provideUserReposPresenter(GitHubService gitHubService, ConnInfoHelper connInfoHelper, RealmHelper realmHelper){
        return new UserReposPresenterImpl(gitHubService, connInfoHelper, realmHelper);
    }

    @Singleton
    @Provides
    UsersPresenter provideUsersPresenter(GitHubService gitHubService, ConnInfoHelper connInfoHelper, RealmHelper realmHelper){
        return new UsersPresenterImpl(gitHubService, connInfoHelper, realmHelper);
    }
}
