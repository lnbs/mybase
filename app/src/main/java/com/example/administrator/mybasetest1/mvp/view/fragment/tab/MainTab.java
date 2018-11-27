package com.example.administrator.mybasetest1.mvp.view.fragment.tab;

import com.example.administrator.mybasetest1.R;
import com.example.administrator.mybasetest1.mvp.view.fragment.FirstFragment;
import com.example.administrator.mybasetest1.mvp.view.fragment.FourthFragment;
import com.example.administrator.mybasetest1.mvp.view.fragment.SecondFragment;
import com.example.administrator.mybasetest1.mvp.view.fragment.ThirdFragment;

/**
 * Created by Administrator on 2018/11/19.
 */

public enum  MainTab {
    FIREST(0, R.string.MIPAI, R.drawable.miaopai, FirstFragment.class),
    SECOND(1, R.string.WEIXIN, R.drawable.weibo, SecondFragment.class),
    THIRD(0, R.string.WEIBAO, R.drawable.weibo, ThirdFragment.class),
    FOURTH(0, R.string.YOUKU, R.drawable.youku, FourthFragment.class);
    private int index,resName,resIcon;
    private Class<?> clz;

    MainTab(int index, int resName, int resIcon, Class<?> clz) {
        this.index = index;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
