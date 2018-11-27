package com.example.administrator.mybasetest1.di.Model;

import android.content.Context;

import com.example.administrator.mybasetest1.App;
import com.example.administrator.mybasetest1.di.scope.ContextLife;
import com.example.administrator.mybasetest1.di.scope.PerActivity;
import com.example.administrator.mybasetest1.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/11/16.
 */

@Module
public class AppModel {
    private App mApplication;

    public AppModel(App mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideContext(){
        return mApplication.getApplicationContext();
    }
}
