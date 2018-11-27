package com.example.administrator.mybasetest1.mvp.presenter.impl;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.example.administrator.mybasetest1.base.BaseActivity;
import com.example.administrator.mybasetest1.base.mvpbase.BasePresenter;
import com.example.administrator.mybasetest1.base.mvpbase.BasePresenterImpl;
import com.example.administrator.mybasetest1.bean.GankItemData;
import com.example.administrator.mybasetest1.di.scope.ContextLife;
import com.example.administrator.mybasetest1.di.scope.PerActivity;
import com.example.administrator.mybasetest1.mvp.presenter.GankDataPresenter;
import com.example.administrator.mybasetest1.mvp.view.activity_view.GankDataView;
import com.example.administrator.mybasetest1.net.RestClient;
import com.example.administrator.mybasetest1.net.callback.IFailure;
import com.example.administrator.mybasetest1.net.callback.ISuccess;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by Administrator on 2018/11/16.
 */

public class GankDataPersenterImpl extends BasePresenterImpl<GankDataView> implements GankDataPresenter{

    Context mContext;

    @Inject
    public GankDataPersenterImpl(Context context) {
        mContext=context;
    }

    @Override
    public void getGankData(int pageNum) {
        RestClient.builder()
                .url("data/all/10/"+pageNum)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Gson gson=new Gson();
                        GankItemData gankItemData = gson.fromJson(response, GankItemData.class);
                        mPresenterView.getGankDataSuccess(gankItemData);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        mPresenterView.getGankDataError("请求失败");
                    }
                })
                .build()
                .get();
    }


}
