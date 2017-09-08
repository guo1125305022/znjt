package com.znjtgs.entity;

import android.app.Activity;

/**
 * Created by JKX_GXL on 2017/5/13.
 */

public class SlidingMenuListAdapterItem {
    private Class<? extends Activity> activity;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public Class<? extends Activity> getActivity() {
        return activity;
    }

    public void setActivity(Class<? extends Activity> activity) {
        this.activity = activity;
    }

    public SlidingMenuListAdapterItem(Class<? extends Activity> activity, String title) {
        this.activity = activity;
        this.title = title;
    }
}
