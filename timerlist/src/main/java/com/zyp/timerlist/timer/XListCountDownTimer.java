package com.zyp.timerlist.timer;

import android.util.Log;

import com.badoo.mobile.util.WeakHandler;
import com.zyp.timerlist.BuildConfig;
import com.zyp.timerlist.timer.core.XTimer;
import com.zyp.timerlist.timer.view.ViewWrapper;


import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XListCountDownTimer {
    public static final String TAG = XListCountDownTimer.class.getName();

    private final WeakHandler mWeakHandler;

    private ConcurrentHashMap<Integer, Long> taskMap = new ConcurrentHashMap();

    public XListCountDownTimer() {
        mWeakHandler = new WeakHandler();
    }

    public boolean isStopTask(int viewId, long id) {
        final Long _id = taskMap.get(viewId);
        if (_id == null || id != _id) {
            return true;
        }
        return false;
    }

    public void running(XListCountDownTask countDownTask) {
        countDownTask.setWeakHander(this,mWeakHandler);
        final ViewWrapper timerInfo = countDownTask.getViewWrapper();
        final int viewId = timerInfo.getViewId();
        final long id = timerInfo.getId();

        boolean isStartNewTask = true;
        if (BuildConfig.DEBUG)Log.e(TAG, "id : " + id + " , viewId:" + viewId);
        if (BuildConfig.DEBUG)printMap();
        if (taskMap.containsKey(viewId)) {//viewholder复用
            if (isStopTask(viewId, id)) {
                //停止任务
                taskMap.remove(viewId);
                if (BuildConfig.DEBUG)Log.e(TAG, "停止任务 : " + id + " , viewId:" + viewId);
            } else {
                //执行中不需要开启新任务
                isStartNewTask = false;
                if (BuildConfig.DEBUG)Log.e(TAG, "执行中不需要开启新任务 : " + id + " , viewId:" + viewId);
            }
        } else {//viewholder重复创建
            if (taskMap.containsValue(id)) {
                final Iterator<Map.Entry<Integer, Long>> iterator = taskMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<Integer, Long> next = iterator.next();
                    final Long value = next.getValue();
                    final Integer key = next.getKey();
                    if (value != null && value.longValue() == id) {
                        taskMap.remove(key);
                        if (BuildConfig.DEBUG)Log.e(TAG, "viewholder重复创建 : " + id + " , viewId:" + viewId);
                    }
                }
            }
        }
        if (isStartNewTask) {
            if (BuildConfig.DEBUG) Log.e(TAG, "新任务: " + id + " , viewId:" + viewId);
            taskMap.put(viewId, id);
            XTimer.getInstance().post(countDownTask);
        }
    }

    private void printMap() {
        final Iterator<Map.Entry<Integer, Long>> iterator = taskMap.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            final Map.Entry<Integer, Long> next = iterator.next();
            final Long value = next.getValue();
            final Integer key = next.getKey();
            sb.append(key).append(":").append(value).append(" , ");
        }
        if (BuildConfig.DEBUG)Log.e(TAG, "size :"+taskMap.size()+" , taskMap: " + sb.toString());
    }


}
