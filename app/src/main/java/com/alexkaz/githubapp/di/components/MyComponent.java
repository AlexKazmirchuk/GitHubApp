package com.alexkaz.githubapp.di.components;

import com.alexkaz.githubapp.di.modules.AppModule;
import com.alexkaz.githubapp.di.modules.DataModule;
import com.alexkaz.githubapp.di.modules.NetworkModule;
import com.alexkaz.githubapp.di.modules.PresenterModule;
import com.alexkaz.githubapp.model.services.RealmHelper;
import com.alexkaz.githubapp.view.UserReposActivity;
import com.alexkaz.githubapp.view.UsersActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,
                      PresenterModule.class,
                      AppModule.class,
                      DataModule.class})
public interface MyComponent {
    void inject(UserReposActivity activity);
    void inject(UsersActivity activity);
    RealmHelper realmHelper();
}
