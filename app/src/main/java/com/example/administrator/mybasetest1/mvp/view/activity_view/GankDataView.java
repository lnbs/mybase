package com.example.administrator.mybasetest1.mvp.view.activity_view;

import com.example.administrator.mybasetest1.base.mvpbase.BaseView;

/**
 * Created by Administrator on 2018/11/16.
 */

public interface GankDataView<T> extends BaseView{
    void getGankDataSuccess(T bean);
    void getGankDataError(String msg);
}
