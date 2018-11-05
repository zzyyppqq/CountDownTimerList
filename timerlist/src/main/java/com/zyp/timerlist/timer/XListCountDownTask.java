package com.zyp.timerlist.timer;

import com.badoo.mobile.util.WeakHandler;
import com.zyp.timerlist.timer.core.XTask;
import com.zyp.timerlist.timer.view.ViewWrapper;


import java.lang.ref.WeakReference;

public abstract class XListCountDownTask extends XTask {

    private WeakHandler mWeakHandler;
    private ViewWrapper mViewWrapper;
    private WeakReference<XListCountDownTimer> mWeakTimeCounter;

    public XListCountDownTask(ViewWrapper mViewWrapper) {
        this.mViewWrapper = mViewWrapper;

    }

    public ViewWrapper getViewWrapper() {
        return mViewWrapper;
    }

    public void setWeakHander(XListCountDownTimer xTimeCounter, WeakHandler weakHandler) {
        this.mWeakTimeCounter = new WeakReference<>(xTimeCounter);
        this.mWeakHandler = weakHandler;
    }

    @Override
    public void run() {
        final int viewId = mViewWrapper.getViewId();
        final long id = mViewWrapper.getId();
        if (isStopTask(viewId, id)) {
            return;
        }
        if (mWeakHandler == null) {
            return;
        }
        mWeakHandler.post(new Runnable() {
            @Override
            public void run() {
                updateView(mViewWrapper);
            }
        });
        onNewTask(this, getDeley());
    }

    protected long getDeley(){
        return 1000;
    }

    protected boolean isStopTask(int viewId, long id) {
        if (mWeakTimeCounter != null) {
            final XListCountDownTimer xTimeCounter = mWeakTimeCounter.get();
            if (xTimeCounter != null) {
                final boolean stopTask = xTimeCounter.isStopTask(viewId, id);
                return stopTask;
            }
        }
        return false;
    }

    protected abstract void updateView(ViewWrapper viewWrapper);


}
