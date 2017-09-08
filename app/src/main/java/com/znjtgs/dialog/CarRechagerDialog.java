package com.znjtgs.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.znjtgs.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/7/8.
 * 小车充值对话框
 */

public class CarRechagerDialog extends BaseDialog {
    private ArrayList<Integer> integers;//需要充值的小车编号集合
    private TextView tv_showCarIds;//
    private EditText editMoney;//充值金额
    private ClickListener clickListener;

    public CarRechagerDialog(@NonNull Context context, ArrayList<Integer> integers) {
        super(context);
        this.integers = integers;
        clickListener = new ClickListener();
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog_car_rechager;
    }

    @Override
    protected void initComponten() {
        tv_showCarIds = (TextView) findViewById(R.id.tv_show_dialog_msg);
        editMoney = (EditText) findViewById(R.id.et_car_rechageer);

        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (int i : integers) {
            if (index != integers.size() - 1) {
                sb.append(i + ",");
            } else {
                sb.append(i);
            }
            index++;
        }
        tv_showCarIds.setText(String.format("是否给 %s 小车充值", sb));
        addButtongCancelClickListsener("取消", clickListener);
        addButtonIgonreClickListener("忽略", clickListener);
    }

    public Integer getMoney() {
        String money = editMoney.getText().toString();
        if (money.isEmpty()) {
            return null;
        }
        return Integer.parseInt(editMoney.getText().toString());
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            CarRechagerDialog.this.dismiss();
        }
    }
}
