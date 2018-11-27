package com.example.administrator.mybasetest1;

import android.app.Application;

import com.example.administrator.mybasetest1.app.AppConfig;
import com.example.administrator.mybasetest1.di.Model.AppModel;
import com.example.administrator.mybasetest1.di.component.AppComponent;
import com.example.administrator.mybasetest1.di.component.DaggerAppComponent;
import com.example.administrator.mybasetest1.net.interceptors.DebugInterceptor;

/**
 * Created by Administrator on 2018/11/14.
 */

public class App extends Application {
    public static final String URL_GET_GANK = "http://gank.io/api/";
    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.builder()
                .appModel(new AppModel(this))
                .build();

        AppConfig.init(this)
                .withApiHost(URL_GET_GANK)
                .withAppCommonent(appComponent)
                .withInterceptor(new DebugInterceptor())
                .configure();


    }
}
