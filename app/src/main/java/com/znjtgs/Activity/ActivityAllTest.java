package com.znjtgs.Activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.znjtgs.R;
import com.znjtgs.ulits.ToastUlits;

import java.util.ArrayList;

/**
 * Created by JKX_GXL on 2017/5/15.
 * 综合测试
 */

public class ActivityAllTest extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<ClassInfo> arrayList = new ArrayList<>();
    private ActivityAdapter activityAdapter;

    @Override
    protected int initLayouotId() {
        return R.layout.activity_all_test_layout;
    }

    @Override
    protected void initComponent() {
        listView = (ListView) findViewById(R.id.ll_activity_all_test_layout);
        activityAdapter = new ActivityAdapter();
        initArrayList();
        listView.setAdapter(activityAdapter);
        listView.setOnItemClickListener(this);
    }

    /**
     * 初始化
     */
    private void initArrayList() {
        arrayList.add(new ClassInfo("我的账号", MyAccountActivity.class));
        arrayList.add(new ClassInfo("注册界面", RegisterActivity.class));
        arrayList.add(new ClassInfo("主界面", MainActivity.class));
        arrayList.add(new ClassInfo("导航界面", NavigationActivity.class));
        arrayList.add(new ClassInfo("停车查询", ParkQueryActivity.class));
        arrayList.add(new ClassInfo("我的座驾", MyCardActivity.class));
        arrayList.add(new ClassInfo("我的路况", MyRoadActivity.class));
     //   arrayList.add(new ClassInfo("道路查询", RoadEnvirActivity.class));
        arrayList.add(new ClassInfo("公交查询", BusQueryActivity.class));
        arrayList.add(new ClassInfo("充值历史", CarRechagerHistoryActivity.class));
        arrayList.add(new ClassInfo("车辆限行", CarLimitActivity.class));
        arrayList.add(new ClassInfo("停车和ETC缴费记录", CarParkHistoryActivity.class));
        arrayList.add(new ClassInfo("路况查询", RoadConditionQueryActivity.class));
        arrayList.add(new ClassInfo("红绿灯管理",ActivityRedGreeLightManager.class));

    }


   private  long time=0;//单击返回键时间记录
    /**
     * 双击推出功能实现
     */
    @Override
    public void onBackPressed() {
        if (time>2000) {
            ToastUlits.ShowToastShort(this, "再按一次退");
            time=System.currentTimeMillis();
            return;
        }else {
            System.exit(0);//退出整个APP
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, activityAdapter.getItem(position).getaClass()));
    }

    private class ActivityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public ClassInfo getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) convertView;
            if (textView == null) {
                textView = (TextView) getLayoutInflater().inflate(R.layout.listview_item, null);
            }
            textView.setText(arrayList.get(position).getName());
            return textView;
        }
    }


    public class ClassInfo {
        private String name;
        private Class<? extends Activity> aClass;

        public ClassInfo(String name, Class<? extends Activity> aClass) {
            this.name = name;
            this.aClass = aClass;
        }

        public String getName() {

            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class<? extends Activity> getaClass() {
            return aClass;
        }

        public void setaClass(Class<? extends Activity> aClass) {
            this.aClass = aClass;
        }
    }

}
