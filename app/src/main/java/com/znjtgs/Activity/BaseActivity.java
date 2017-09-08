package com.znjtgs.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.znjtgs.R;

/**
 * Created by JKX_GXL on 2017/5/11.
 * 基本Activity
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int initLayouotId();

    protected abstract void initComponent();

    private FrameLayout title_layout;
    private FrameLayout content_layout;
    protected LayoutInflater layoutInflater;
    private AppCompatTextView actv_bast_title;
    private AppCompatImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_base_layout);
        layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(initLayouotId(), null);
        content_layout = (FrameLayout) findViewById(R.id.fl_main_base_content_layout);
        title_layout = (FrameLayout) findViewById(R.id.fl_main_base_title_layout);
        actv_bast_title = (AppCompatTextView) findViewById(R.id.actv_bast_title);
        iv_back= (AppCompatImageView) findViewById(R.id.aciv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityBackClick();
            }
        });
        content_layout.addView(view);
        initComponent();
    }

    public void activityBackClick(){
        this.finish();
    }

    /**
     * 设置标题栏标题
     * @param resString string
     */
    public void setMainTitle(int resString){
        actv_bast_title.setText(resString);
    }
    /**
     * 设置标题栏标题
     * @param title 字符串
     */
    public void setMainTitle(String title){
        actv_bast_title.setText(title);
    }

    /**
     * 切换是否显示标题栏
     */
    protected void toggleTitleView() {
        title_layout.setVisibility(title_layout.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置自定义标题视图
     * @param layoutId 视图id
     */
    public void setTitleView(int layoutId) {
        View view = layoutInflater.inflate(layoutId, null);
        setTitleView(view);
    }
    /**
     * 设置自定义标题视图
     * @param titleView 视图
     */
    public void setTitleView(View titleView) {
        title_layout.addView(titleView);
        actv_bast_title = (AppCompatTextView) titleView.findViewById(R.id.actv_bast_title);
    }

    /**
     * 获取配置文件对象
     * @return
     */
    public SharedPreferences getSharedPreferebces() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

}
