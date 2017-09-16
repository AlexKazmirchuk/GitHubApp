package com.alexkaz.githubapp.di.components;

import com.alexkaz.githubapp.MainActivity;
import com.alexkaz.githubapp.di.modules.AppModule;
import com.alexkaz.githubapp.di.modules.NetworkModule;
import com.alexkaz.githubapp.di.modules.PresenterModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,
                      PresenterModule.class,
                      AppModule.class})
public interface MyComponent {
    void inject(MainActivity activity);
}
