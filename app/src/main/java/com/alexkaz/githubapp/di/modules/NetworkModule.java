package com.alexkaz.githubapp.di.modules;

import android.content.Context;

import com.alexkaz.githubapp.model.api.GitHubApi;
import com.alexkaz.githubapp.model.services.ConnInfoHelper;
import com.alexkaz.githubapp.model.services.ConnInfoHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    GitHubApi provideGithubApi(){
        return new Retrofit.Builder()
                .baseUrl(GitHubApi.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(GitHubApi.class);
    }

    @Provides
    @Singleton
    ConnInfoHelper provideConnInfoHelper(Context context){
        return new ConnInfoHelperImpl(context);
    }
}
