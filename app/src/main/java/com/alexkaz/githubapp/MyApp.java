package com.alexkaz.githubapp;

import android.app.Application;

import com.alexkaz.githubapp.di.components.DaggerMyComponent;
import com.alexkaz.githubapp.di.components.MyComponent;
import com.alexkaz.githubapp.di.modules.AppModule;

public class MyApp extends Application {

    private MyComponent myComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        myComponent = DaggerMyComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public MyComponent getMyComponent(){
        return myComponent;
    }
}
