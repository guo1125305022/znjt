package com.znjtgs.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.znjtgs.Adapter.NavigationAdapter;
import com.znjtgs.Fragments.NavigtionFragmrntOne;
import com.znjtgs.Fragments.NavigtionFragmrntTwo;
import com.znjtgs.R;

import java.util.ArrayList;

/**
 * Created by JKX_GXL on 2017/5/11.
 * 导航界面
 * 用于用户第一次启动App时显示
 */

public class NavigationActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private NavigationAdapter navigationAdapter;

    @Override
    protected int initLayouotId() {
        return R.layout.activity_navigation_layout;
    }

    @Override
    protected void initComponent() {
        toggleTitleView();
        viewPager = (ViewPager) findViewById(R.id.vp_navigationg);
        navigationAdapter = new NavigationAdapter(getSupportFragmentManager(), getFragmentS());
        viewPager.setAdapter(navigationAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
    }



    public ArrayList<Fragment> getFragmentS() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new NavigtionFragmrntOne());
        fragments.add(new NavigtionFragmrntTwo());
        return fragments;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
