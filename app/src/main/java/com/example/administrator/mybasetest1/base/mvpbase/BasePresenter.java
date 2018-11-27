package com.example.administrator.mybasetest1.base.mvpbase;

/**
 * Created by Administrator on 2018/11/15.
 */

public interface BasePresenter<T extends BaseView> {
    /**
     * 绑定view
     * @param view
     */
    void attachView(T view);

    /**
     * 解绑View
     */
    void detachView();
}
