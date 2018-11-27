package com.example.administrator.mybasetest1.adapter.base;


/**
 * Created by Administrator on 2018/11/16.
 */

public interface OnItemClickListener<T> {
    void onItemClick(BaseViewHolder viewHolder,T data,int position);
}
