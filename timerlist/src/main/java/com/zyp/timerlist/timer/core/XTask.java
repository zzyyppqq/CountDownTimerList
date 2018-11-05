package com.zyp.timerlist.timer.core;

public abstract class XTask implements Runnable {

    interface OnNewTaskListener{
        void newTask(XTask task, long deley);
    }

    private OnNewTaskListener mOnNewTaskListener;

    public void setOnNewTaskListener(OnNewTaskListener listener) {
        this.mOnNewTaskListener = listener;
    }

    public void onNewTask(XTask task,long deley){
        if (mOnNewTaskListener != null){
            mOnNewTaskListener.newTask(task,deley);
        }
    }

}
