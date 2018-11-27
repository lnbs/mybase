package com.example.administrator.mybasetest1.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.mybasetest1.base.mvpbase.BaseView;
import com.example.administrator.mybasetest1.widget.LoadingPager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/11/15.
 */

public abstract class BaseFragment extends Fragment implements BaseView {
    protected BaseActivity mBaseActivity;
    protected LoadingPager mLoadingPager;
    protected boolean mIsViewInitiated;
    protected boolean mIsVisibleToUser;
    protected boolean mIsDataInitiated;

    /**----------------------------子类需实现的方法--------------------------------*/
    //请求网络加载数据
    protected abstract void loadData();

    //加载成功页面，对应子类的布局
    protected abstract View childSuccessView();

    protected abstract void initData();


    /**------------------------生命周期方法--------------------------*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLoadingPager==null){
            mLoadingPager=new LoadingPager(getContext()) {
                @Override
                protected View createSuccessView() {
                    View view = childSuccessView();
                    return view;
                }

                @Override
                protected void load() {
                    loadData();
                }
            };
        }
        return mLoadingPager;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBaseActivity= (BaseActivity) getActivity();
        initData();
    }


    /**-----------------------回调的方法----------------------------*/
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    /**----------------------------使用方法--------------------------*/

    //第一步 调用此方法，回调loadData方法请求数据，并显示加载加载数据的页面
    public void show(){
        if (mLoadingPager!=null) {
            mLoadingPager.show();
        }
    }

    //第二步 重写childSuccessView方法，返回数据加载成功是的页面，并使用以下方法改变页面布局
    public void setState(LoadingPager.LoadResult result){
        if (mLoadingPager!=null) {
            mLoadingPager.setState(result);
        }
    }

    public void fullScreen(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }


    @Override
    public void showToast(String msg) {
        mBaseActivity.showToast(msg);
    }
}
