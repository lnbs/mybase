package com.example.administrator.mybasetest1.base.mvpbase;

/**
 * Created by Administrator on 2018/11/16.
 */

public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T> {

    protected T mPresenterView;


    @Override
    public void attachView(T view) {
        mPresenterView=view;
    }

    @Override
    public void detachView() {
        mPresenterView=null;
    }
}
