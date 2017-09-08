package com.znjtgs.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.znjtgs.R;

/**
 * Created by JKX_GXL on 2017/5/11.
 */

public abstract class BaseDialog extends Dialog {
    protected abstract int initLayout();//初始化对话框布局

    protected abstract void initComponten();//初始化相关组件

    private LinearLayout frameLayout;
    private LayoutInflater layoutInflater;
    private AppCompatButton btn_cancel;
    private AppCompatButton btn_igonre;
    private AppCompatButton btn_confirm;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_base_layout);
        layoutInflater = LayoutInflater.from(getContext());
        btn_cancel = (AppCompatButton) findViewById(R.id.acb_base_dialog_cancel);//取消按钮
        btn_igonre = (AppCompatButton) findViewById(R.id.acb_base_dialog_ignore);//忽略按钮
        btn_confirm = (AppCompatButton) findViewById(R.id.acb_base_dialog_confirm);//确定按钮
        frameLayout = (LinearLayout) findViewById(R.id.fl_dialog_base_main_content);//初始化主视图显示区

        View view = layoutInflater.inflate(initLayout(), null);//初始化视图到主显示区
        frameLayout.addView(view);//
        initComponten();//初始化组件
    }

    public void addButtongCancelClickListsener(String text, View.OnClickListener onClickListener) {
        btn_cancel.setVisibility(View.VISIBLE);
        btn_cancel.setText(text);
        btn_cancel.setOnClickListener(onClickListener);
    }

    public void addButtonConfirmClickListenre(String text, View.OnClickListener onClickListener) {
        btn_confirm.setVisibility(View.VISIBLE);
        btn_confirm.setText(text);
        btn_confirm.setOnClickListener(onClickListener);
    }

    public void addButtonIgonreClickListener(String text, View.OnClickListener onClickListener) {
        btn_igonre.setVisibility(View.VISIBLE);
        btn_igonre.setText(text);
        btn_igonre.setOnClickListener(onClickListener);
    }

    public void addButtongCancelClickListsener(int textRes, View.OnClickListener onClickListener) {
        addButtongCancelClickListsener(getStringRes(textRes), onClickListener);
    }

    public void addButtonConfirmClickListenre(int textRes, View.OnClickListener onClickListener) {
        addButtonConfirmClickListenre(getStringRes(textRes), onClickListener);
    }

    public void addButtonIgonreClickListener(int textRes, View.OnClickListener onClickListener) {
        addButtonIgonreClickListener(getStringRes(textRes), onClickListener);
    }

    /**
     * 通过 字符资源ID获取字符串
     *
     * @param res 字符串Id
     * @return
     */
    public String getStringRes(int res) {
        return getContext().getResources().getString(res);
    }


}
