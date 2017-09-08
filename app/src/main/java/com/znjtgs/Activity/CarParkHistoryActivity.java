package com.znjtgs.Activity;

import android.widget.ListView;

import com.znjtgs.Adapter.CarPayHistoryAdapter;
import com.znjtgs.R;
import com.znjtgs.entity.CarParkPayEtcPay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/9.
 */

public class CarParkHistoryActivity extends BaseActivity {

    private ArrayList<CarParkPayEtcPay> pays=new ArrayList<>();
    private ListView listView;
    private CarPayHistoryAdapter adapter;
    @Override
    protected int initLayouotId() {
        return R.layout.activity_car_pay_history_layout;
    }

    @Override
    protected void initComponent() {
        setMainTitle("停车和ETC缴费记录");
        String parkData=initParkData();//初始化停车数据
        analysisData(parkData);
        adapter=new CarPayHistoryAdapter(this,pays);
        listView= (ListView) findViewById(R.id.lv_car_pay_history);
        listView.setAdapter(adapter);
    }

    private void analysisData(String parkData) {
        try {
            JSONObject obj=new JSONObject(parkData);
            JSONArray jsonArray=obj.getJSONArray("ROWS_DETAIL");
            for (int i=0;i<jsonArray.length();i++){
                pays.add(new CarParkPayEtcPay(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化停车数据
     * @return
     */
    private String initParkData() {
        StringBuilder data=new StringBuilder();
        try {
            InputStream open = this.getAssets().open("carParkHistory.data");//打开停车数据文件
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(open));//
            String line;
            while ((line=bufferedReader.readLine())!=null){//读取数据
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

}
