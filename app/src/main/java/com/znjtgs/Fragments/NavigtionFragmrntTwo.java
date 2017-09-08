package com.znjtgs.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.znjtgs.Activity.LoginActivity;
import com.znjtgs.Cantast;
import com.znjtgs.R;

/**
 * Created by JKX_GXL on 2017/5/11.
 * 导航界面2
 */

public class NavigtionFragmrntTwo extends BaseFragment implements View.OnClickListener {
    @Override
    protected int initLayout() {
        return R.layout.fragment_navigaction_two;
    }

    @Override
    protected void initComponent(View root) {
        root.findViewById(R.id.acb_navigation_bring_into_use).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acb_navigation_bring_into_use:
                getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                SharedPreferences sharedPreferences = getSharedPreferences();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Cantast.SharedKey.KEY_FIRST_START, true);
                editor.apply();
                break;
        }
    }
}
