package com.example.administrator.mybasetest1.app;

import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2018/11/13.
 */

public class AppConfig {
    public static Configurator init(Context context){
        Configurator.getInstance()
                .getAppConfig()
                .put(ConfigKey.APPLICATION_CONTEXT,context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key){
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext(){
        return getConfiguration(ConfigKey.APPLICATION_CONTEXT);
    }

    public static Handler getHandler(){
        return getConfiguration(ConfigKey.HANDLER);
    }
}
