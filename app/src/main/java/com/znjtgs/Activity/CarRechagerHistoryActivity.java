package com.znjtgs.Activity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.znjtgs.R;
import com.znjtgs.db.CarRechargeDb;

/**
 * Created by lenovo on 2017/7/8.
 */

public class CarRechagerHistoryActivity extends BaseActivity implements View.OnClickListener {
    private ListView listView;
    private ArrayAdapter<String> spAdapter;
    private Spinner spinner;
    private CarRechargeDb carRechargeDb;
    private SimpleCursorAdapter cursorAdapter;
    private int[] bindViewsId = {
            R.id.tv_car_rechager_number,
            R.id.tv_car_reachager_id,
            R.id.tv_car_rechager_money,
            R.id.tv_car_rechager_user,
            R.id.tv_car_rechager_time
    };

    @Override
    protected int initLayouotId() {
        return R.layout.activity_car_recharge_history;
    }

    @Override
    protected void initComponent() {
        setMainTitle("充值历史");
        spinner = (Spinner) findViewById(R.id.sp_car_rechare_sort);
        listView= (ListView) findViewById(R.id.lv_car_rechager_history);
        spAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item);
        spAdapter.add("时间升序");
        spAdapter.add("时间降序");
        spinner.setAdapter(spAdapter);
        carRechargeDb = CarRechargeDb.get(this);
        findViewById(R.id.btn_car_rechager_history_recory).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        cursorAdapter = carRechargeDb.queryAllData(
                R.layout.list_item_car_rechager_history_layout, bindViewsId,
                spinner.getSelectedItemPosition() == 0);
        listView.setAdapter(cursorAdapter);
    }
}
