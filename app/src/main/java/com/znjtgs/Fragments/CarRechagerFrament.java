package com.znjtgs.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.znjtgs.Adapter.CarRechagerAdapter;
import com.znjtgs.Adapter.CarRechagerInfo;
import com.znjtgs.R;
import com.znjtgs.config.AppNetParams;
import com.znjtgs.db.CarRechargeDb;
import com.znjtgs.dialog.CarRechagerDialog;
import com.znjtgs.file.FileTools;
import com.znjtgs.request.BaseRequest;
import com.znjtgs.request.HttpOkUlits;
import com.znjtgs.request.RequestCarUserMoney;
import com.znjtgs.ulits.UserManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * 小车充值fragment
 * 主要实现小车充值功能 和小车批量充值
 */
public class CarRechagerFrament extends BaseFragment implements  BaseRequest.OnResultDataListener, CarRechagerAdapter.ListViewChildButtonClick {
    private String loadDataPath;
    private CarRechagerAdapter adapter;
    private ArrayList<CarRechagerInfo> rechagerInfos = new ArrayList<>();
    private ListView listView;
    private boolean run = false;
    private boolean pause = false;
    private int cardId;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };
    private Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            while (run) {
                while (!pause) {
                    logic();
                    threadSleep(3000);
                }
                threadSleep(3000);
            }
        }
    };

    private void logic() {
        for (CarRechagerInfo info : rechagerInfos) {
            requestCarCurrMoney(info);
        }
        handler.sendMessage(new Message());
    }

    private void requestCarCurrMoney(CarRechagerInfo rechagerInfo) {
        JSONObject jsonObject = UserManager.getUserManager().toJson();
        try {
            jsonObject.put(AppNetParams.ParameterName.CAR_ID, rechagerInfo.getCarId());
            String request = HttpOkUlits.Request(this.getContext(), AppNetParams.RequestAction.GET_CAR_ACCOUNT_BALANCE, jsonObject.toString());
            jsonObject = new JSONObject(request);
            rechagerInfo.setMoney(jsonObject.getInt(AppNetParams.ParameterName.CAR_BALANCE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void threadSleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDataPath = this.getContext().getFilesDir() + File.separator + "carRechager.data";
        initAdapter();
        run = true;
        thread.start();
    }

    private void initAdapter() {
        rechagerInfos.clear();
        if (!initLocadData()) {
            for (int i = 0; i < 9; i++) {
                rechagerInfos.add(new CarRechagerInfo(i + 1, 0));
            }
        }
        adapter = new CarRechagerAdapter(this.getContext(), rechagerInfos);
        adapter.setListViewChildButtonClick(this);
    }

    private boolean initLocadData() {
        String data = FileTools.openFileToString(loadDataPath);
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int cardId = jsonObject.getInt(CarRechagerInfo.KEY_CAR_ID);
                int money = jsonObject.getInt(CarRechagerInfo.KEY_CAR_CURR_MONEY);
                rechagerInfos.add(new CarRechagerInfo(cardId, money));
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected int initLayout() {
        return R.layout.frament_car_rechager;
    }

    @Override
    protected void initComponent(View root) {
        setTitle("小车充值");
        listView = (ListView) findViewById(R.id.lv_car_rechager);
        listView.setAdapter(adapter);
        pause = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        pause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        pause = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        run = false;
        pause = false;
    }


    /**
     * 小车充值
     * @param carIds
     */
    private void ShowTopUp(final ArrayList<Integer> carIds) {
        final CarRechagerDialog dialog=new CarRechagerDialog(this.getContext(),carIds);//创建充值对话框
        dialog.show();
        dialog.setTitle("小车充值");
        dialog.addButtonConfirmClickListenre("确定", new OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer money=dialog.getMoney();//获取充值的金额
                if(money==null){
                    ShowToast("充值金额不能为空");
                    return;
                }
                if(money<1){
                    ShowToast("充值金额不能小于1");
                    return;
                }
                for (int i : carIds) {//遍历需要充值的小车
                    AccountTopUp(i, money);//充值
                }
                adapter.setAllSelect(false);//取消所有的勾选状态
                adapter.notifyDataSetChanged();//刷新listview
                dialog.dismiss();//关闭对话框
            }
        });
    }

    private void AccountTopUp(final int cardId, int money) {
        // 充值
        RequestCarUserMoney requestCarUserMoney = new RequestCarUserMoney(this.getContext());
        requestCarUserMoney.setListener(this);
        requestCarUserMoney.setRequestMethod(RequestCarUserMoney.SET_CAR_MONEY);
        requestCarUserMoney.setCarId(cardId);
        requestCarUserMoney.setMoney(money);
        requestCarUserMoney.connection();

    }

    private void updatetvQueryShowMoney(RequestCarUserMoney requestCarUserMoney,boolean state) {
        ShowToast("小车" + requestCarUserMoney.getCarId() + "充值"+(state?"成功":"失败")+"充值金额；" + requestCarUserMoney.getMoney() + ".");
       //添加数据到数据库中
        CarRechargeDb.get(this.getContext()).insertData(
                requestCarUserMoney.getCarId(),
                requestCarUserMoney.getMoney(),
                UserManager.getUserManager().getUserName());
    }

    private void ShowToast(String text) {
        Toast.makeText(this.getActivity(), text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResultData(BaseRequest baseRequest, Object object) {
        if (object == null) return;
        RequestCarUserMoney requestCarUserMoney = (RequestCarUserMoney) baseRequest;
        boolean state=(object!=null&&object.toString().equals("Ok"));
        updatetvQueryShowMoney(requestCarUserMoney,state);
    }

    @Override
    public void onButtonClick(View v, int position) {
        Boolean[] selects = adapter.getSelects();
        ArrayList<Integer> integers = new ArrayList<>();
        int i = 0;
        for (boolean select : selects) {
            if (select) {
                integers.add(i+1);
            }
            i++;
        }
        if (integers.size() == 0) {
            integers.add(position+1);
        }
        ShowTopUp(integers);
    }
}
