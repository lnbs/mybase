package com.example.administrator.mybasetest1.base.mvpbase;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.example.administrator.mybasetest1.app.AppConfig;
import com.example.administrator.mybasetest1.app.ConfigKey;
import com.example.administrator.mybasetest1.base.BaseActivity;
import com.example.administrator.mybasetest1.di.Model.ActivityModel;
import com.example.administrator.mybasetest1.di.component.ActivityComponent;
import com.example.administrator.mybasetest1.di.component.AppComponent;
import com.example.administrator.mybasetest1.di.component.DaggerActivityComponent;

/**
 * 继承BaseActivity 实现了BaseView
 * Created by Administrator on 2018/11/15.
 */

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity{

    //对象注入管理者
    protected ActivityComponent build;

    protected T mPresenter;
    /**------------------------------需要实现的方法--------------------------------------*/


    /**
     * 前两步继承自BaseActivity
     * 第三步完成注入并返回注入的对象
     * @return
     */
    protected abstract T initInjector();

    /**
     * 第四步设置页面数据
     */
    protected abstract void initData();

    /**---------------------------------生命周期方法-------------------------------*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityComonter();
        mPresenter=initInjector();
        mPresenter.attachView(this);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }

    /**--------------------------------初始化方法-----------------------------------*/

    private void initActivityComonter() {
        AppComponent appComponent=AppConfig.getConfiguration(ConfigKey.APP_COMPONENT);
        build= DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModel(new ActivityModel(this))
                .build();
    }

}
