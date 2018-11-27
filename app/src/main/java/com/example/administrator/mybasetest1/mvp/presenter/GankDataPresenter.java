package com.example.administrator.mybasetest1.mvp.presenter;

import com.example.administrator.mybasetest1.base.BaseActivity;
import com.example.administrator.mybasetest1.base.mvpbase.BasePresenter;
import com.example.administrator.mybasetest1.mvp.view.activity_view.GankDataView;

import javax.inject.Scope;

/**
 * Created by Administrator on 2018/11/16.
 */

public interface GankDataPresenter extends BasePresenter<GankDataView>{
    void getGankData(int pageNum);
}
