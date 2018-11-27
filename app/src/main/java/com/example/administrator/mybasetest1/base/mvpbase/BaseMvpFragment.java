package com.example.administrator.mybasetest1.base.mvpbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.mybasetest1.app.AppConfig;
import com.example.administrator.mybasetest1.app.ConfigKey;
import com.example.administrator.mybasetest1.base.BaseFragment;
import com.example.administrator.mybasetest1.di.Model.FragmentModel;
import com.example.administrator.mybasetest1.di.component.AppComponent;
import com.example.administrator.mybasetest1.di.component.DaggerFragmentComponent;
import com.example.administrator.mybasetest1.di.component.FragmentComponent;

/**
 * Created by Administrator on 2018/11/16.
 * 将dagger2依赖注入通过抽象方法提取出来，由具体的Fragement去实现
 * 继承自BaseFragment,实现了BaseViwe,BasePresenter有attachView，detachView两个方法
 */

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment {
    protected FragmentComponent mFragmentComponent;
    protected T mPresenter;

    /**---------------------------------子类需要实现的方法-------------------------------*/
    protected abstract T initInjector();//注入presenter对象

    /**-------------------------------生命周期方法-------------------------------*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentComponenet();
        mPresenter=initInjector();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null) {
            mPresenter.detachView();
        }
    }
    /**------------------------------初始化方法-------------------------------------*/
    private void initFragmentComponenet() {
        AppComponent appComponent = AppConfig.getConfiguration(ConfigKey.APP_COMPONENT);
        mFragmentComponent=DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModel(new FragmentModel(this))
                .build();
    }
}



















