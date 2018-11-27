package com.example.administrator.mybasetest1.di.Model;

import android.app.Activity;
import android.content.Context;

import com.example.administrator.mybasetest1.di.scope.ContextLife;
import com.example.administrator.mybasetest1.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2018/11/16.
 */

@Module
public class ActivityModel {
    private Activity mActivity;

    public ActivityModel(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity(){
        return mActivity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideContext(){
        return mActivity;
    }

    @Provides
    public Context provideContext1(){
        return mActivity;
    }

}
