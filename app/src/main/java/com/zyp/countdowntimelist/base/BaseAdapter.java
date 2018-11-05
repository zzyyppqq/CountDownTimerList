package com.zyp.countdowntimelist.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyp.countdowntimelist.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    private LayoutInflater mInflater;
    protected ArrayList<T> mDatas = new ArrayList<>();

    @NonNull
    @Override
    public BaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        final View view = mInflater.inflate(itemLayout(), parent, false);
        return createViewHolder(view);
    }

    protected abstract BaseViewHolder<T> createViewHolder(View view);

    protected abstract int itemLayout();

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T> holder, int position) {
        final T itemData = getItemData(position);
        if (itemData != null){
            holder.bindData(itemData);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setDatas(@NonNull List<T> datas) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        } else {
            mDatas.clear();
        }
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public T getItemData(int position) {
        if (mDatas != null && mDatas.size() > 0) {
            return mDatas.get(position);
        }
        return null;
    }

}
