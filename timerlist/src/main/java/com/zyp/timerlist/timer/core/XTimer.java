package com.zyp.timerlist.timer.core;

import android.os.Handler;
import android.os.HandlerThread;


public class XTimer implements XTask.OnNewTaskListener {
    private static XTimer xTimer = null;
    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private XTimer(){
        init();
    }

    public static XTimer getInstance() {
        if (xTimer == null){
            synchronized (XTimer.class){
                if (xTimer == null){
                    xTimer = new XTimer();
                }
            }
        }
        return xTimer;
    }

    private void init() {
        mHandlerThread = new HandlerThread("XTimerThread"){
            @Override
            protected void onLooperPrepared() {
                super.onLooperPrepared();

                if (mHandler == null) {
                    mHandler = new Handler(getLooper());
                }
            }
        };
        mHandlerThread.start();
    }


    public void post(XTask task){
        post(task,0);
    }

    public void post(XTask task,long deley){
        if (mHandler == null){
            mHandler = new Handler(mHandlerThread.getLooper());
        }
        task.setOnNewTaskListener(this);
        if (deley > 0) {
            mHandler.postDelayed(task, deley);
        }else {
            mHandler.post(task);
        }
    }

    public void remove(XTask task){
        if (mHandler != null){
            mHandler.removeCallbacks(task);
        }
    }

    public void destory(){
        if(null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if(null != mHandlerThread) {
            mHandlerThread.quit();
            mHandlerThread.interrupt();
            mHandlerThread = null;
        }
    }


    @Override
    public void newTask(XTask task, long deley) {
        post(task,deley);
    }
}
