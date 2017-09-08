package com.znjtgs.Activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.znjtgs.Cantast;
import com.znjtgs.R;
import com.znjtgs.config.AppNetParams;
import com.znjtgs.request.HttpOkUlits;
import com.znjtgs.ulits.UserManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class BusQueryActivity extends BaseActivity {
    private Thread thread;
    private boolean pause;
    private boolean run;
    private ListView busStationInfo;
    private RquestBus rquestBus;
    private ArrayList<BusStationInfo> busStationInfoArrayList = new ArrayList<>();
    private BusAdapter busAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handleMsg(msg);
        }
    };

    private void handleMsg(Message msg) {
        busStationInfoArrayList.clear();
        BusStationInfo[] busStationInfos = (BusStationInfo[]) msg.obj;
        Collections.addAll(busStationInfoArrayList, busStationInfos);
        busAdapter.notifyDataSetChanged();
    }

    @Override
    protected int initLayouotId() {
        return R.layout.activity_bus_query_layout;
    }

    @Override
    protected void initComponent() {
        busStationInfo = (ListView) findViewById(R.id.lv_list_busStation);
        busAdapter = new BusAdapter();
        busStationInfo.setAdapter(busAdapter);
        rquestBus = new RquestBus();
        thread = new Thread(rquestBus);
        run = true;
        pause=false;
        thread.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pause = true;
        run = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pause = false;
        busAdapter.notifyDataSetChanged();
    }

    private void sendMsg(int what, Object object) {
        Message message = new Message();
        message.what = what;
        message.obj = object;
        handler.sendMessage(message);
    }

    /**
     * 公交站台信息适配器
     *
     * @author 郭小浪
     * @version 1.0
     * @since 这个适配器是绑定公交站台和公交车信息
     */
    private class BusAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return busStationInfoArrayList.size();
        }

        @Override
        public BusStationInfo getItem(int position) {
            return busStationInfoArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BusStationInfo busStationInfo = busStationInfoArrayList.get(position);//获取公交站台
            BusItemViewHolder busItemViewHolder;//定义公交站台条目视图
            if (convertView == null) {//adapter 判断缓存中是否有可用的视图
                busItemViewHolder = new BusItemViewHolder();//创建公交站台视图
                busItemViewHolder.getRootItemView().setTag(busItemViewHolder);//把视图中的数据绑定到公交站台条目中的主视图
            } else {
                busItemViewHolder = (BusItemViewHolder) convertView.getTag();//获取公交站台绑定的数据
            }
            busItemViewHolder.bindData(busStationInfo);//把新的公交数据绑定到子视图并显示
            return busItemViewHolder.getRootItemView();//返回公交站台数据到listview上
        }
    }

    private class BusItemViewHolder {
        private RelativeLayout rootItemView;//公交站台主视图
        private LinearLayout main_bus_info_item;//公交车主视图容器
        private TextView tv_busStation_id;//公交车站ID

        private ArrayList<BusInfoViewHolder> busInfoViewHolders;//公交车集合

        public BusItemViewHolder() {
            busInfoViewHolders = new ArrayList<>();//
            rootItemView = (RelativeLayout) layoutInflater.inflate(R.layout.list_item_bus_main_layout, null);
            initView();
        }

        private void initView() {
            tv_busStation_id = (TextView) rootItemView.findViewById(R.id.tv_bus_station_id);//初始化公交站台编号显示组件
            main_bus_info_item = (LinearLayout) rootItemView.findViewById(R.id.ll_bus_info_item);//初始化公交车信息显示组件的主布局
        }

        /**
         * 绑定数据
         * @param busStationInfo
         */
        public void bindData(BusStationInfo busStationInfo) {
            if (busStationInfo == null) {//检查公交站台对象是否为空
                getRootItemView().setVisibility(View.GONE);//如果为空则隐藏本视图
                return;//返回
            }

            getRootItemView().setVisibility(View.VISIBLE);//设置试图为显示状态
            tv_busStation_id.setText(busStationInfo.busStationId + "号站台");//设置公交车站台
            ArrayList<BusInfo> busInfos = busStationInfo.getBusIds();//获取公交站台中所存在的公交车数据

            busInfoViewHolders.clear();//清空公交车视图
            main_bus_info_item.removeAllViews();//移除当前公交站台已显示的公交车视图组件

            for (int i = 0; i < busInfos.size(); i++) {
                busInfoViewHolders.add(new BusInfoViewHolder());//添加公交车显示组件
            }

            for (int i = 0; i < busInfos.size(); i++) {
                BusInfo busInfo = busInfos.get(i);//取得公交车数据
                if (busInfo == null) {
                    continue;
                }
                busInfoViewHolders.get(i).bingData(busInfo);//绑定数据到视图上
                this.main_bus_info_item.addView(busInfoViewHolders.get(i).getRootView());//添加到公交站台上并显示
            }
        }

        public RelativeLayout getRootItemView() {
            return rootItemView;
        }
    }

    private class BusInfoViewHolder {
        private TextView tv_busId;//公交车编号
        private TextView tv_distance;//距离
        private TextView tv_time;//到站剩余时间
        private View rootView;

        public View getRootView() {
            return rootView;
        }

        public BusInfoViewHolder() {
            if (rootView == null) {
                rootView = layoutInflater.inflate(R.layout.list_item_bus_info, null);
                initView();
            }
        }

        private void initView() {
            tv_busId = (TextView) rootView.findViewById(R.id.tv_bus_id);
            tv_distance = (TextView) rootView.findViewById(R.id.tv_bus_distance);
            tv_time = (TextView) rootView.findViewById(R.id.tv_bus_time);
        }

        private void bingData(BusInfo busInfo) {
            tv_busId.setText(busInfo.getBusId() + "号公交");
            tv_distance.setText("距离" + busInfo.getDistance() + "米");
            tv_time.setText("剩余" + busInfo.getTime() + "分");
        }
    }

    /**
     *公交站台信息请求线程
     */
    private class RquestBus implements Runnable {
        @Override
        public void run() {
            while (run) {//控制线程是否运行
                while (!pause) {//控制是否暂停网络请求
                    BusStationInfo[] busStationInfo = new BusStationInfo[2];//定义公交站站台信息数组
                    busStationInfo[0] = getBusInfo(1);
                    busStationInfo[1] = getBusInfo(2);
                    sendMsg(0, busStationInfo);//把数据发送到主线程并通知主线程更新界面
                    sleep(5000);//线程休眠5秒
                }
                sleep(5000);//线程休眠5秒
            }
        }


        /**
         * 线程休眠
         *
         * @param time 休眠时间
         */
        private void sleep(long time) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 从网络服务器上获取公交站台相关数据
         *
         * @param busId 公交站台编号
         * @return
         */
        private BusStationInfo getBusInfo(int busId) {
            BusStationInfo busStationInfo = null;//定义公交站台对象
            try {
                JSONObject jsonObject = UserManager.getUserManager().toJson();//获取当前登陆的用户并转成JSONOBJECT对象
                jsonObject.put(Cantast.BusKey.KEY_BUS_STATION_ID, busId);//设置公交站台编号
                String request = HttpOkUlits.Request(BusQueryActivity.this, AppNetParams.RequestAction.GET_BUSSTATION_INFO, jsonObject.toString());//开始网络请求
                busStationInfo = new BusStationInfo(busId, request);//解析请求的数据
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return busStationInfo;//返回公交站台信息
        }
    }


    /**
     * 公交站台数据
     */
    private class BusStationInfo {
        private int busStationId;//公交站台编号
        private ArrayList<BusInfo> busIds = new ArrayList<>();
        public int getBusStationId() {
            return busStationId;
        }
        public ArrayList<BusInfo> getBusIds() {
            return busIds;
        }
        public BusStationInfo(int busStationId, String busStation) {
            this.busStationId = busStationId;
            try {
                JSONArray jsonArray = new JSONArray(busStation);//将字符串解析成为Json数组
                busIds.clear();
                for (int i = 0; i < jsonArray.length(); i++) {//解析数据
                    busIds.add(new BusInfo(jsonArray.getJSONObject(i).toString()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 公交车信息
     */
    private class BusInfo {
        private int busId;//公交车编号
        private int distance;//距离（注：单位米）
        private static final int speed = 20;//速度（注：单位 km/h）

        public BusInfo(String busIngoString) throws JSONException {
            JSONObject jsonObject = new JSONObject(busIngoString);
            distance = jsonObject.getInt(AppNetParams.ParameterName.BUS_DISTANCE) * 10;
            busId = jsonObject.getInt(AppNetParams.ParameterName.BUS_ID);
        }

        public int getBusId() {
            return busId;
        }

        public int getDistance() {
            return distance;
        }
        //获取公交距离站台剩余时间（分钟）
        public int getTime() {
            int d = distance / 1000;
            d /= speed;
            return d * 60;
        }
    }
}
