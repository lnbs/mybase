package com.example.administrator.mybasetest1.app;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.example.administrator.mybasetest1.di.component.AppComponent;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by Administrator on 2018/11/13.
 */

public class Configurator {
    private static final HashMap<Object,Object> APP_CONFIG=new HashMap<>();
    private static final Handler HANDLER=new Handler();
    private static final ArrayList<Interceptor> INTERCEPTORS=new ArrayList<>();

    private Configurator() {
        APP_CONFIG.put(ConfigKey.CONFIG_READY,false);
        APP_CONFIG.put(ConfigKey.HANDLER,HANDLER);
    }

    private static class Holder{
        private static final Configurator INSTANCE=new Configurator();
    }

    protected static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    protected final HashMap<Object,Object> getAppConfig(){
        return APP_CONFIG;
    }

    public final void configure(){
        Logger.addLogAdapter(new AndroidLogAdapter());
        APP_CONFIG.put(ConfigKey.CONFIG_READY,true);
        Utils.init(AppConfig.getApplicationContext());
    }

    public final Configurator withApiHost(String host){
        APP_CONFIG.put(ConfigKey.API_HOST,host);
        return this;
    }

    public final Configurator withAppCommonent(AppComponent appCommonent){
        APP_CONFIG.put(ConfigKey.APP_COMPONENT,appCommonent);
        return this;
    }



    public final Configurator withInterceptor(Interceptor interceptor){
        if (interceptor!=null){
            INTERCEPTORS.add(interceptor);
            APP_CONFIG.put(ConfigKey.INTERCEPTOR,INTERCEPTORS);
        }
        return this;
    }

    public final Configurator withWeChatAppId(String appId){
        APP_CONFIG.put(ConfigKey.WE_CHAT_APP_ID,appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret){
        APP_CONFIG.put(ConfigKey.WE_CHAT_APP_SECRET,appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity){
        APP_CONFIG.put(ConfigKey.ACTIVITY,activity);
        return this;
    }

    public Configurator withJavascriptInterface(@NonNull String name) {
        APP_CONFIG.put(ConfigKey.JAVASCRIPT_INTERFACE, name);
        return this;
    }

   private void checkConfiguration(){
        final boolean isReady= (boolean) APP_CONFIG.get(ConfigKey.CONFIG_READY);
       if (!isReady) {
           throw new RuntimeException("Configuration is not ready,call configure");
       }
   }

   final <T> T getConfiguration(Object key){
       checkConfiguration();
       final Object value = APP_CONFIG.get(key);
       if (value==null) {
           throw new NullPointerException(key.toString()+" IS NULL");
       }
       return (T) value;
   }
}





















