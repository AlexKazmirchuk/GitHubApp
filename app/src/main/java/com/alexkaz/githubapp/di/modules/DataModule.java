package com.alexkaz.githubapp.di.modules;

import android.content.Context;

import com.alexkaz.githubapp.model.services.RealmHelper;
import com.alexkaz.githubapp.model.services.RealmHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    RealmHelper provideRealmHelper(Context context){
        return new RealmHelperImpl(context);
    }

}
