package com.example.administrator.mybasetest1.adapter.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/16.
 *
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_COMMON_VIEW=10001;
    private static final int TYPE_FOOTER_VIEW=10002;
    private static final int TYPE_EMPTY_VIEW=10003;
    private static final int TYPE_DEFAULT_VIEW=10004;

    private OnItemClickListener<T> mItemClickListener;
    private OnLoadMoreListener mLoadMoreListener;

    public Context mContext;
    private List<T> mDatas;
    private boolean mOpenLoadMore;//是否开启加载更多

    private boolean isAutoLoadMore=true;//自动加载更多开关

    private View mLoadingView;
    private View mEmptyView;
    private View mLoadEndView;
    private View mLoadFailedView;
    private RelativeLayout mFooterLayout;

    /**-------------------------子类需要实现的方法-----------------------*/
    protected abstract int getItemLayoutId();
    protected abstract void convert(BaseViewHolder viewHolder, T t);


    /**--------------------------RecyclerView.Adapter会调用的方法-------------------------------*/

    public BaseAdapter(Context mContext, List<T> mDatas, boolean mOpenLoadMore) {
        this.mContext = mContext;
        this.mDatas = mDatas==null?new ArrayList<T>():mDatas;
        this.mOpenLoadMore = mOpenLoadMore;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder=null;
        switch (viewType){
            case TYPE_COMMON_VIEW:
                viewHolder=BaseViewHolder.create(mContext,getItemLayoutId(),parent);
                break;
            case TYPE_FOOTER_VIEW:
                if (mFooterLayout==null) {
                    mFooterLayout=new RelativeLayout(mContext);
                }
                viewHolder=BaseViewHolder.create(mFooterLayout);
                break;
            case TYPE_EMPTY_VIEW:
                viewHolder=BaseViewHolder.create(mEmptyView);
                break;
            case TYPE_DEFAULT_VIEW:
                viewHolder=BaseViewHolder.create(new View(mContext));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TYPE_COMMON_VIEW:
                bindCommonItem(holder,position);
                break;
        }
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position) {
        final BaseViewHolder viewHolder= (BaseViewHolder) holder;
        convert(viewHolder,mDatas.get(position));
        viewHolder.getmConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener!=null) {
                    mItemClickListener.onItemClick(viewHolder,mDatas.get(position),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDatas!=null) {
            return mDatas.size()+getFootViewCount();
        }
        return 0;
    }

    private int getFootViewCount() {
        return mOpenLoadMore ? 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas!=null&&mDatas.isEmpty()&&mEmptyView!=null) {
            return TYPE_EMPTY_VIEW;
        }
        if (isFootView(position)){
            return TYPE_FOOTER_VIEW;
        }
        if (mDatas!=null&&mDatas.isEmpty()) {
            return TYPE_DEFAULT_VIEW;
        }
        return TYPE_COMMON_VIEW;
    }

    public T getItem(int position){
        return mDatas.get(position);
    }

    private boolean isFootView(int position){
        return mOpenLoadMore&&getItemCount()>1&&position>=getItemCount()-1;
    }

    /**
     * StaggeredGridLayoutManager模式时，FooterView可占据一行
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isFootView(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams layoutParams=holder.itemView.getLayoutParams();
            if (layoutParams!=null&&layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams lp= (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                lp.setFullSpan(true);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager= (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isFootView(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        startLoadMore(recyclerView,layoutManager);
    }

    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        if (!mOpenLoadMore||mLoadMoreListener==null) {
            return;
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState== RecyclerView.SCROLL_STATE_IDLE) {
                    if (!isAutoLoadMore&&findLastVisibleItemPosition(layoutManager)+1==getItemCount()) {
                        scrollLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isAutoLoadMore&&findLastVisibleItemPosition(layoutManager)+1==getItemCount()) {
                    scrollLoadMore();
                }else if (isAutoLoadMore){
                    isAutoLoadMore=false;
                }
            }
        });
    }

    private void scrollLoadMore(){
        if (mFooterLayout.getChildAt(0)==mLoadingView){
            if (mLoadMoreListener!=null) {
                mLoadMoreListener.onLoadMore(false);
            }
        }
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager){
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            return findMax(lastVisibleItemPositions);
        }
        return -1;
    }

    private int findMax(int[] lastVisiblePositions){
        int max=lastVisiblePositions[0];
        for (int lastVisiblePosition : lastVisiblePositions) {
            if (lastVisiblePosition>max) {
                max=lastVisiblePosition;
            }
        }
        return max;
    }

    /**
     * 移除脚布局
     */
    private void removeFooterView(){
        mFooterLayout.removeAllViews();
    }

    private void addFooterView(View footerViwe){
        if (footerViwe==null) {
            return;
        }
        if (mFooterLayout==null) {
            mFooterLayout=new RelativeLayout(mContext);
        }
        removeFooterView();
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mFooterLayout.addView(footerViwe,params);
    }

    public void setLoadingView(View loadingView){
        mLoadingView=loadingView;
        addFooterView(mLoadingView);
    }



    public void setLoadFailedView(View loadFailedView){
        if (loadFailedView==null) {
            return;
        }
        mLoadFailedView=loadFailedView;
        addFooterView(mLoadFailedView);
        mLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFooterView(mLoadingView);
                mLoadMoreListener.onLoadMore(true);
            }
        });
    }

    public void setLoadEndView(View loadEndView){
        mLoadEndView=loadEndView;
        addFooterView(mLoadEndView);
    }

    /**---------------------------------------使用步骤-------------------------------------------*/
    public void setNewData(List<T> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setLoadMoreData(List<T> datas){
        int size=mDatas.size();
        mDatas.addAll(datas);
        notifyItemInserted(size);
    }

    public void setData(List<T> datas){
        mDatas.addAll(0,datas);
        notifyDataSetChanged();
    }

    public void setLoadFailedView(int loadFailedViewId){
        setLoadingView(inflate(loadFailedViewId));
    }

    public void setLoadEndView(int loadEndViewId){
        setLoadingView(inflate(loadEndViewId));
    }

    public void setEmptyView(View emptyView){
        mEmptyView=emptyView;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener){
        mLoadMoreListener=loadMoreListener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener){
        mItemClickListener=itemClickListener;
    }

    public void setLoadingView(int loadingViewId){
        setLoadingView(inflate(loadingViewId));
    }

    private View inflate(int loadingViewId) {
        if (loadingViewId<=0) {
            return null;
        }
        return LayoutInflater.from(mContext).inflate(loadingViewId,null);
    }


}
