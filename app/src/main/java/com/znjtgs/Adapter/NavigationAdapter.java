package com.znjtgs.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by JKX_GXL on 2017/5/11.
 */

public class NavigationAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public NavigationAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return (fragments == null ? 0 : fragments.size());
    }
}
