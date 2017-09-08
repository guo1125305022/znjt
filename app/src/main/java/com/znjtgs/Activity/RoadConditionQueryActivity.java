package com.znjtgs.Activity;


import android.widget.LinearLayout;
import com.znjtgs.R;
import com.znjtgs.entity.CarStateRoadInfo;
import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/11.
 */

public class RoadConditionQueryActivity extends BaseActivity {
    private LinearLayout layout;
    private ArrayList<CarStateRoadInfo> carStateRoadInfos = new ArrayList<>(3);
    private boolean run;
    private boolean pause;
    private Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            while (run) {
                while (!pause) {
                    logic();
                    ThreadSleep(10000);
                }
                ThreadSleep(10000);
            }
        }
    };
    private void ThreadSleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void logic() {
        for (CarStateRoadInfo info : carStateRoadInfos) {
            info.Rqueest();
        }
    }

    @Override
    protected int initLayouotId() {
        return R.layout.activity_road_condition_query_layout;
    }

    @Override
    protected void initComponent() {
        setMainTitle("路况查询");
        layout = (LinearLayout) findViewById(R.id.ll_road_condition_query);
        carStateRoadInfos.add(new CarStateRoadInfo(this, 1, 1));
        carStateRoadInfos.add(new CarStateRoadInfo(this, 2, 2));
        carStateRoadInfos.add(new CarStateRoadInfo(this, 3, 3));
        for (CarStateRoadInfo info : carStateRoadInfos) {
            layout.addView(info.getView());
        }
        run=true;
        pause=false;
        thread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pause=false;
        //logic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause=true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        run=false;
        pause=true;
    }
}
