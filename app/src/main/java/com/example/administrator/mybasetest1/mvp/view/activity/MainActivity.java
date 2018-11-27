package com.example.administrator.mybasetest1.mvp.view.activity;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.example.administrator.mybasetest1.R;
import com.example.administrator.mybasetest1.adapter.GankListAdapter;
import com.example.administrator.mybasetest1.adapter.base.OnLoadMoreListener;
import com.example.administrator.mybasetest1.base.BaseActivity;
import com.example.administrator.mybasetest1.base.mvpbase.BaseMvpActivity;
import com.example.administrator.mybasetest1.bean.GankItemData;
import com.example.administrator.mybasetest1.di.scope.ContextLife;
import com.example.administrator.mybasetest1.di.scope.PerActivity;
import com.example.administrator.mybasetest1.mvp.presenter.GankDataPresenter;
import com.example.administrator.mybasetest1.mvp.presenter.impl.GankDataPersenterImpl;
import com.example.administrator.mybasetest1.mvp.view.activity_view.GankDataView;
import com.example.administrator.mybasetest1.net.RestClient;
import com.example.administrator.mybasetest1.net.callback.IFailure;
import com.example.administrator.mybasetest1.net.callback.ISuccess;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity<GankDataPersenterImpl> implements GankDataView<GankItemData>, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    GankDataPersenterImpl persenter;


    @BindView(R.id.rcvdata)
    RecyclerView recyclerView;

    @BindView(R.id.refresh_view)
    SwipeRefreshLayout swipeRefreshLayout;

    private int PAGE_COUNT = 1;
    private int mTempPageCount = 2;
    private boolean isLoadMore;
    private GankListAdapter gankListAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    /**------------------------------数据获取流程--------------------------------*/
    @Override
    protected GankDataPersenterImpl initInjector() {
        build.inject(this);
        return persenter;
    }

    @Override
    protected void initView() {
        fullScreen(this,getResources().getColor(R.color.colorAccent));
        //RecyclerView 初始化及加载更多
        gankListAdapter = new GankListAdapter(this,new ArrayList<GankItemData.GankItem>(),true);
        gankListAdapter.setLoadingView(R.layout.load_loading_layout);
        gankListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (PAGE_COUNT == mTempPageCount && !isReload) {
                    return;
                }
                isLoadMore = true;
                PAGE_COUNT = mTempPageCount;
                persenter.getGankData(PAGE_COUNT);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gankListAdapter);

        //SwipeRefreshLayout初始化及刷新数据
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);
        //实现首次自动显示加载提示
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

    }

    @Override
    protected void initData() {
        persenter.getGankData(PAGE_COUNT);
    }

    /**------------------------------------数据处理流程--------------------------------------*/

    @Override
    public void getGankDataSuccess(GankItemData bean) {
        List<GankItemData.GankItem> results = bean.getResults();
        if (isLoadMore) {
            if (results.size() == 0) {
                gankListAdapter.setLoadEndView(R.layout.load_end_layout);
            } else {
                gankListAdapter.setLoadMoreData(results);
                mTempPageCount++;
            }
        } else {
            gankListAdapter.setNewData(results);
            swipeRefreshLayout.setRefreshing(false);
        }

    }


    @Override
    public void getGankDataError(String msg) {
        if (isLoadMore) {
            gankListAdapter.setLoadFailedView(R.layout.load_failed_layout);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onRefresh() {
        isLoadMore = false;
        PAGE_COUNT = 1;
        mTempPageCount = 2;
        persenter.getGankData(PAGE_COUNT);
    }
}
