package com.example.administrator.mybasetest1.di.component;

import android.content.Context;

import com.example.administrator.mybasetest1.di.Model.AppModel;
import com.example.administrator.mybasetest1.di.scope.ContextLife;
import com.example.administrator.mybasetest1.di.scope.PerApp;

import dagger.Component;

/**
 * Created by Administrator on 2018/11/16.
 */

@PerApp
@Component(modules = {AppModel.class})
public interface AppComponent {
    @ContextLife("Application")
    Context getApplicationContext();
}
