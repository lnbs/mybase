package com.example.administrator.mybasetest1.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/11/16.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private View mConvertView;
    private SparseArray<View> mViews;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mConvertView=itemView;
        mViews=new SparseArray<>();
    }

    public static BaseViewHolder create(Context context, int layoutId, ViewGroup parent){
        View itemView= LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseViewHolder(itemView);
    }

    public static BaseViewHolder create(View itemView){
        return new BaseViewHolder(itemView);
    }

    public <T extends View> T getView(int viewId){
            View view=mViews.get(viewId);
            if (view==null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId,view);
            }
            return (T) view;
    }

    public View getmConvertView(){
        return mConvertView;
    }

}
