package com.example.administrator.mybasetest1.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.example.administrator.mybasetest1.R;
import com.example.administrator.mybasetest1.adapter.base.BaseAdapter;
import com.example.administrator.mybasetest1.adapter.base.BaseViewHolder;
import com.example.administrator.mybasetest1.app.AppConfig;
import com.example.administrator.mybasetest1.bean.GankItemData;
import com.example.administrator.mybasetest1.utils.image.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2018/11/17.
 */

public class GankListAdapter extends BaseAdapter<GankItemData.GankItem> {

    /**
     * --------------------------RecyclerView.Adapter会调用的方法-------------------------------
     *
     * @param mContext
     * @param mDatas
     * @param mOpenLoadMore
     */
    public GankListAdapter(Context mContext, List<GankItemData.GankItem> mDatas, boolean mOpenLoadMore) {
        super(mContext, mDatas, mOpenLoadMore);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_gank_layout;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, GankItemData.GankItem gankItem) {
        TextView tv_item_des=viewHolder.getView(R.id.gank_item_desc);
        TextView tv_item_who=viewHolder.getView(R.id.gank_item_who);
        TextView tv_item_publicshedat=viewHolder.getView(R.id.gank_item_publishedat);
        ImageView iv_icon=viewHolder.getView(R.id.gank_item_icon);

        tv_item_des.setText(gankItem.getDesc());
        tv_item_who.setText(StringUtils.isEmpty(gankItem.getWho())?"null":gankItem.getWho());
        tv_item_publicshedat.setText(gankItem.getPublishedAt().substring(0, 10));
        List<String> images = gankItem.getImages();
        if (images!=null&&images.size()>0) {
            ImageLoader.load(mContext,
                    images.get(0) + "?imageView2/0/w/100", iv_icon, R.drawable.web);
        }else {
            String url = gankItem.getUrl();
            int iconId;
            if (url.contains("github")) {
                iconId = R.drawable.github;
            } else if (url.contains("jianshu")) {
                iconId = R.drawable.jianshu;
            } else if (url.contains("csdn")) {
                iconId = R.drawable.csdn;
            } else if (url.contains("miaopai")) {
                iconId = R.drawable.miaopai;
            } else if (url.contains("acfun")) {
                iconId = R.drawable.acfun;
            } else if (url.contains("bilibili")) {
                iconId = R.drawable.bilibili;
            } else if (url.contains("youku")) {
                iconId = R.drawable.youku;
            } else if (url.contains("weibo")) {
                iconId = R.drawable.weibo;
            } else if (url.contains("weixin")) {
                iconId = R.drawable.weixin;
            } else {
                iconId = R.drawable.web;
            }
            ImageLoader.load(mContext, iconId, iv_icon);
        }
    }
}
