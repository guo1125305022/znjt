package com.znjtgs.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.znjtgs.Activity.BaseActivity;

/**
 * Created by JKX_GXL on 2017/5/11.
 */

public abstract class BaseFragment extends Fragment {
    protected abstract int initLayout();
    private View rootView;
    protected abstract void initComponent(View root);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(initLayout(), null);
        rootView=view;
        initComponent(view);
        return view;
    }

    public SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    public void setTitle(String text){
        BaseActivity baseActivity= (BaseActivity) this.getActivity();
        baseActivity.setMainTitle(text);
    }

    protected View findViewById(int resId){
        return rootView.findViewById(resId);
    }
}
