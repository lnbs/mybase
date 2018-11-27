package com.example.administrator.mybasetest1.mvp.view.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.example.administrator.mybasetest1.R;
import com.example.administrator.mybasetest1.base.BaseActivity;
import com.example.administrator.mybasetest1.mvp.view.fragment.tab.MainTab;

import butterknife.BindView;

public class HomeActivityBaseFragment extends BaseActivity {

    @BindView(android.R.id.tabhost)
    FragmentTabHost fragmentTabHost;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_home_base_fragment;
    }

    @Override
    protected void initView() {
        fullScreen(this,Color.TRANSPARENT);
        fragmentTabHost.setup(this,getSupportFragmentManager(),R.id.module_container);
        initTabs();
    }

    private void initTabs() {
        MainTab[] tabs = MainTab.values();
        for (MainTab tab : tabs) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(getString(tab.getResName()));
            View indication = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
            AppCompatImageView imageView = indication.findViewById(R.id.tab_image);
            AppCompatTextView textView = indication.findViewById(R.id.tab_text);
            textView.setText(getString(tab.getResName()));
            imageView.setImageDrawable(ContextCompat.getDrawable(this,tab.getResIcon()));
            tabSpec.setIndicator(indication);
            fragmentTabHost.addTab(tabSpec,tab.getClz(),null);
//            fragmentTabHost.getTabWidget().setDividerDrawable(R.drawable.divider_line);
            if (Build.VERSION.SDK_INT>=11){
                fragmentTabHost.getTabWidget().setShowDividers(TabWidget.SHOW_DIVIDER_MIDDLE);
            }
//            fragmentTabHost.getTabWidget().setDividerDrawable(null);
        }

    }

}
