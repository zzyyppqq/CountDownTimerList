package com.zyp.timerlist.timer;

import com.badoo.mobile.util.WeakHandler;
import com.zyp.timerlist.timer.core.XTask;
import com.zyp.timerlist.timer.core.XTimer;

public abstract class XCountDownTimer {

    private WeakHandler mWeakHandler;
    private TickTask mTickTask;
    private boolean isMainThread = true;

    public XCountDownTimer(long millisInFuture, long countDownInterval) {
        this(millisInFuture, countDownInterval, true);
    }

    public XCountDownTimer(long millisInFuture, long countDownInterval, boolean isMainThread) {
        this.isMainThread = isMainThread;
        if (isMainThread) {
            mWeakHandler = new WeakHandler();
        }
        mTickTask = new TickTask(this, millisInFuture, countDownInterval);
    }


    protected void tick(final long millisUntilFinished) {
        if (isMainThread) {
            mWeakHandler.post(new Runnable() {
                @Override
                public void run() {
                    onTick(millisUntilFinished);
                }
            });
        }else {
            onTick(millisUntilFinished);
        }
    }

    protected void finish() {
        if (isMainThread) {
            mWeakHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFinish();
                }
            });
        }else {
            onFinish();
        }
    }

    protected abstract void onTick(long millisUntilFinished);

    protected abstract void onFinish();


    public void cancel() {
        XTimer.getInstance().remove(mTickTask);
    }

    public void start() {
        XTimer.getInstance().post(mTickTask);
    }


    class TickTask extends XTask {
        private long millisInFuture;
        private long countDownInterval;
        private XCountDownTimer xCountDownTimer;

        public TickTask(XCountDownTimer xCountDownTimer, long millisInFuture, long countDownInterval) {
            super();
            this.xCountDownTimer = xCountDownTimer;
            this.millisInFuture = millisInFuture;
            this.countDownInterval = countDownInterval;
        }

        @Override
        public void run() {
            if (millisInFuture < 0) {
                xCountDownTimer.finish();
                return;
            }
            xCountDownTimer.tick(millisInFuture);
            millisInFuture -= countDownInterval;
            onNewTask(this, countDownInterval);
        }
    }
}
