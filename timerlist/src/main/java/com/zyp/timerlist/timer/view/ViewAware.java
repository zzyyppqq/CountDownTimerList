package com.zyp.timerlist.timer.view;

import android.view.View;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class ViewAware<V extends View>{
    protected Reference<V> viewRef;
    public ViewAware(View view) {
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        } else {
            this.viewRef = new WeakReference(view);

        }
    }

    public V getView() {
        return this.viewRef.get();
    }

    public int getId() {
        final V view = getView();
        return view == null ? super.hashCode() : view.hashCode();
    }

}
