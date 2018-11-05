package com.zyp.countdowntimelist.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected Context mContext;
    protected View itemView;
    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mContext = itemView.getContext();
        this.itemView = itemView;
        this.itemView.setOnClickListener(this);
    }

    public abstract void bindData(T t);

    @Override
    public void onClick(View v) {

    }
}
