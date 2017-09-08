package com.znjtgs.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.znjtgs.Cantast;
import com.znjtgs.R;

/**
 * Created by JKX_GXL on 2017/5/14.
 */

public class LogoActivity extends BaseActivity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            StartIntent();
        }
    };


    private void StartIntent() {
        if (getSharedPreferebces().getBoolean(Cantast.SharedKey.KEY_FIRST_START, false)) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startActivity(new Intent(LogoActivity.this, NavigationActivity.class));
        }
        this.finish();
    }


    @Override
    protected int initLayouotId() {
        return R.layout.activity_logo_layout;
    }

    @Override
    protected void initComponent() {

        toggleTitleView();
        new StartThread().start();
    }

    private class StartThread extends Thread {
        @Override
        public void run() {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendMessage(new Message());
        }
    }
}
