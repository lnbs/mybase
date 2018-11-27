package com.example.administrator.mybasetest1.di.component;

import android.app.Activity;
import android.content.Context;

import com.example.administrator.mybasetest1.di.Model.ActivityModel;
import com.example.administrator.mybasetest1.di.Model.AppModel;
import com.example.administrator.mybasetest1.di.scope.ContextLife;
import com.example.administrator.mybasetest1.di.scope.PerActivity;
import com.example.administrator.mybasetest1.mvp.view.activity.MainActivity;

import dagger.Component;

/**
 * Created by Administrator on 2018/11/16.
 */

@PerActivity
@Component(modules = ActivityModel.class ,dependencies = AppComponent.class)
public interface ActivityComponent {

//    Activity getActivity();
//
//    Context getCont

    void inject(MainActivity activity);
}
