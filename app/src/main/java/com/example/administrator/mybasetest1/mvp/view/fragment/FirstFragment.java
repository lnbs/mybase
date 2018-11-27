package com.example.administrator.mybasetest1.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mybasetest1.R;
import com.example.administrator.mybasetest1.adapter.GankListAdapter;
import com.example.administrator.mybasetest1.adapter.base.OnLoadMoreListener;
import com.example.administrator.mybasetest1.base.mvpbase.BaseMvpActivity;
import com.example.administrator.mybasetest1.base.mvpbase.BaseMvpFragment;
import com.example.administrator.mybasetest1.bean.GankItemData;
import com.example.administrator.mybasetest1.mvp.presenter.impl.FragmentGankDataPersenterImpl;
import com.example.administrator.mybasetest1.mvp.presenter.impl.GankDataPersenterImpl;
import com.example.administrator.mybasetest1.mvp.view.activity_view.GankDataView;
import com.example.administrator.mybasetest1.widget.LoadingPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2018/11/19.
 */

public class FirstFragment extends BaseMvpFragment<FragmentGankDataPersenterImpl> implements GankDataView<GankItemData>, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    FragmentGankDataPersenterImpl impl;

    @BindView(R.id.frcvdata)
    RecyclerView recyclerView;

    @BindView(R.id.frefresh_view)
    SwipeRefreshLayout swipeRefreshLayout;

    private int PAGE_COUNT = 1;
    private int mTempPageCount = 2;
    private boolean isLoadMore;
    private GankListAdapter gankListAdapter;


    @Override
    public void getGankDataSuccess(GankItemData bean) {
        setState(LoadingPager.LoadResult.success);
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
        setState(LoadingPager.LoadResult.error);
        if (isLoadMore) {
            gankListAdapter.setLoadFailedView(R.layout.load_failed_layout);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void initData() {
        show();
    }

    @Override
    protected void loadData() {
        impl.getGankData(PAGE_COUNT);
    }

    @Override
    protected View childSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_first, null);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        fullScreen(getActivity(),getResources().getColor(R.color.colorAccent));
        //RecyclerView 初始化及加载更多
        gankListAdapter = new GankListAdapter(getActivity(),new ArrayList<GankItemData.GankItem>(),true);
        gankListAdapter.setLoadingView(R.layout.load_loading_layout);
        gankListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (PAGE_COUNT == mTempPageCount && !isReload) {
                    return;
                }
                isLoadMore = true;
                PAGE_COUNT = mTempPageCount;
                impl.getGankData(PAGE_COUNT);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gankListAdapter);

        //SwipeRefreshLayout初始化及刷新数据
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    protected FragmentGankDataPersenterImpl initInjector() {
        mFragmentComponent.inject(this);
        return impl;
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        PAGE_COUNT = 1;
        mTempPageCount = 2;
        impl.getGankData(PAGE_COUNT);
    }

}
