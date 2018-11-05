package com.zyp.timerlist.timer.view;

import android.view.View;

import com.zyp.timerlist.timer.view.ViewAware;


public class ViewWrapper<V extends View> {
    private ViewAware<V> mViewAware;
    private long id;

    public ViewWrapper(long id, View view) {
        this.mViewAware = new ViewAware<>(view);
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public int getViewId() {
       return mViewAware.getId();
    }

    public V getView(){
        return mViewAware.getView();
    }
}
