package com.zyp.countdowntimelist.demo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zyp.countdowntimelist.R;
import com.zyp.countdowntimelist.base.BaseAdapter;
import com.zyp.countdowntimelist.base.BaseViewHolder;
import com.zyp.countdowntimelist.util.DataUtil;
import com.zyp.timerlist.timer.XCountDownTimer;
import com.zyp.timerlist.timer.XListCountDownTask;
import com.zyp.timerlist.timer.XListCountDownTimer;
import com.zyp.timerlist.timer.core.XTimer;
import com.zyp.timerlist.timer.view.ViewWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private TimeDownAdapter mTimeDownAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initAdapter();

        initData();

        initCountDownTimer();


    }

    private void initCountDownTimer() {

        XCountDownTimer xCountDownTimer = new XCountDownTimer(30 * 1000, 1000) {
            @Override
            protected void onTick(long millisUntilFinished) {
                Log.e(TAG, "millisUntilFinished : " + millisUntilFinished / 1000);
            }

            @Override
            protected void onFinish() {
                Log.e(TAG, "onFinish" + " , thread:" + Thread.currentThread().getName());
            }
        };
        xCountDownTimer.start();

    }


    private void runThread() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTimeDownAdapter = new TimeDownAdapter();
        recyclerView.setAdapter(mTimeDownAdapter);
    }

    public static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;

    private void initData() {
        final long curTime = SystemClock.elapsedRealtime();
        List<TimeEntry> datas = new ArrayList<>();
        for (long i = 0; i < 100; i++) {
            final TimeEntry timeEntry = new TimeEntry(i, curTime + ONE_DAY_MS * i + i * 1000 + 10000);
            datas.add(timeEntry);
        }
        mTimeDownAdapter.setDatas(datas);
    }


    class TimeDownAdapter extends BaseAdapter<TimeEntry> {

        private final XListCountDownTimer mXTimeCounter;

        public TimeDownAdapter() {
            mXTimeCounter = new XListCountDownTimer();
        }

        @Override
        protected BaseViewHolder<TimeEntry> createViewHolder(View view) {
            return new TimeDownViewHolder(view, mXTimeCounter);
        }

        @Override
        protected int itemLayout() {
            return R.layout.item_layout;
        }

        class TimeDownViewHolder extends BaseViewHolder<TimeEntry> {
            private XListCountDownTimer mXTimeCounter;

            @BindView(R.id.tv_down_time)
            TextView tvDownTime;

            public TimeDownViewHolder(View itemView, XListCountDownTimer xTimeCounter) {
                super(itemView);
                this.mXTimeCounter = xTimeCounter;
                ButterKnife.bind(this, itemView);
            }

            @Override
            public void bindData(TimeEntry timeEntry) {
                final long time = timeEntry.getTime();
                final long id = timeEntry.getId();

                mXTimeCounter.running(new XListCountDownTask(new ViewWrapper(id, tvDownTime)) {
                    @Override
                    protected boolean updateView(ViewWrapper viewWrapper) {
                        final TextView tvDownTime = (TextView) viewWrapper.getView();

                        long remainTime = time - SystemClock.elapsedRealtime();
                        if (remainTime < 0) {
                            tvDownTime.setText("剩余 :" + DataUtil.formatDownTime(0));
                            return false;
                        }
                        final String downTime = DataUtil.formatDownTime(remainTime);
                        tvDownTime.setText("剩余 :" + downTime);
                        return true;
                    }

                });
            }
        }
    }

}
