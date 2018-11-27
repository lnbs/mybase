package com.example.administrator.mybasetest1.mvp.presenter.impl;

import android.content.Context;

import com.example.administrator.mybasetest1.base.mvpbase.BasePresenterImpl;
import com.example.administrator.mybasetest1.bean.GankItemData;
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

public class FragmentGankDataPersenterImpl extends BasePresenterImpl<GankDataView> implements GankDataPresenter{


    @Inject
    public FragmentGankDataPersenterImpl() {

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
