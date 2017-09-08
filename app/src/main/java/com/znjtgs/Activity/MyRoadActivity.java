package com.znjtgs.Activity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.znjtgs.Adapter.RoadRedGreeLightAdapter;
import com.znjtgs.R;
import com.znjtgs.entity.MyRoadTrafficLightInfo;
import com.znjtgs.request.BaseRequest;
import com.znjtgs.request.RequestRedGreedLight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MyRoadActivity extends BaseActivity implements OnClickListener, BaseRequest.OnResultDataListener {
    private ArrayAdapter<String> adapter;
    private Spinner spinner;
    private ListView lv;
    private ArrayList<MyRoadTrafficLightInfo> list;
    private RoadRedGreeLightAdapter redGreeLightAdapter;
    private static final int UPDATA_LISTVIEW = 0X5523;

    private RequestRedGreedLight lights;


    @Override
    protected int initLayouotId() {
        return R.layout.my_road_activity_layout;
    }

    @Override
    protected void initComponent() {
//		setMainTitle("我的路况");

        list = new ArrayList<>();
        // TODO Auto-generated method stub
        findViewById(R.id.btn_query_road).setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.spinner_paixu);
        lv = (ListView) findViewById(R.id.lv_TrafficLightID);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        adapter.add("红灯升序");
        adapter.add("绿灯降序");
        spinner.setAdapter(adapter);
        redGreeLightAdapter = new RoadRedGreeLightAdapter(this);
        redGreeLightAdapter.setList(list);
        lv.setAdapter(redGreeLightAdapter);
    }


    @Override
    public void onClick(View v) {
        RequestRedGreedLight();
    }

    private void RequestRedGreedLight() {
        list.clear();
        index=0;
        for (int i = 1; i <= 5; i++) {
            lights = new RequestRedGreedLight(this);
            lights.setTrafficLightID(i);
            lights.setListener(this);
            lights.connection();
        }

    }

    private MyComparator myComparator = new MyComparator();


    private int index = 0;

    @Override
    public void onResultData(BaseRequest baseRequest, Object object) {
        index++;
        MyRoadTrafficLightInfo lightInfo = (MyRoadTrafficLightInfo) object;
        list.add(lightInfo);

        if (index >= 4) {
            myComparator.setSortName(spinner.getSelectedItemPosition() == 0 ? MyComparator.red : MyComparator.greed);
            Collections.sort(list, myComparator);
            redGreeLightAdapter.notifyDataSetChanged();// 更新listView；

        }

    }


    private class MyComparator implements Comparator<MyRoadTrafficLightInfo> {
        private static final int red = 0x55;
        private static final int greed = 0x54;
        private int name = red;

        public void setSortName(int name) {
            this.name = name;
        }

        @Override
        public int compare(MyRoadTrafficLightInfo arg0, MyRoadTrafficLightInfo arg1) {
            switch (name) {
                case red:
                    if (arg0.getReadTime() < arg1.getReadTime())
                        return -1;
                    break;
                case greed:
                    if (arg0.getGreenTime() > arg1.getGreenTime())
                        return -1;
                    break;
            }

            return 0;
        }
    }


}
