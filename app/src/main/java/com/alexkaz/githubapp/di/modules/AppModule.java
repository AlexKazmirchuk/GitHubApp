package com.alexkaz.githubapp.di.modules;

import android.content.Context;

import com.alexkaz.githubapp.MyApp;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private MyApp app;

    public AppModule(MyApp app) {
        this.app = app;
    }

    @Provides
    Context provideAppContext(){
        return app;
    }

}
