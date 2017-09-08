package com.znjtgs.Activity;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.znjtgs.R;
import com.znjtgs.request.BaseRequest;
import com.znjtgs.request.UserRegisteRequest;
import com.znjtgs.ulits.ToastUlits;

/**
 * Created by JKX_GXL on 2017/5/11.
 * 用户注册 窗体
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener, BaseRequest.OnResultDataListener {
    private AppCompatEditText et_account;//用户账号编辑框
    private AppCompatEditText et_pwd;//密码编辑框
    private AppCompatEditText et_re_pwd;//再次密码编辑框
    private AppCompatEditText et_phone_number;//电话号码编辑框
    private UserRegisteRequest userRegisteRequest;//用户注册请求组件

    @Override
    protected int initLayouotId() {
        return R.layout.acticity_register_layout;
    }

    @Override
    protected void initComponent() {
        userRegisteRequest = new UserRegisteRequest(this);
        userRegisteRequest.setListener(this);
        et_account = (AppCompatEditText) findViewById(R.id.acet_account);
        et_phone_number = (AppCompatEditText) findViewById(R.id.acet_phone_number);
        et_re_pwd = (AppCompatEditText) findViewById(R.id.acet_re_pwd);
        et_pwd = (AppCompatEditText) findViewById(R.id.acet_pwd);
        setMainTitle("用户注册");
        findViewById(R.id.acb_cancel_resgister).setOnClickListener(this);
        findViewById(R.id.acb_confirm_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acb_cancel_resgister:
                this.finish();
                break;
            case R.id.acb_confirm_register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        if (!checkUserInfo()) {//用户信息检测
            return;
        }
        /*
        * 用户注册
        * */
        userRegisteRequest.setAuthority(true);
        userRegisteRequest.setUserName(et_account.getText().toString());
        userRegisteRequest.setUserPwd(et_pwd.getText().toString());
        userRegisteRequest.setUserPhoneNumber(et_phone_number.getText().toString());
        userRegisteRequest.connection();
    }

    private boolean checkUserInfo() {
        String account = et_account.getText().toString();
        String re_pwd = et_re_pwd.getText().toString();
        String pwd = et_pwd.getText().toString();
        String phoneMunber = et_phone_number.getText().toString();
        if (account.isEmpty()) {
            ToastUlits.ShowToastLong(this, R.string.text_acconut_isemty);
            return false;
        }
        if (pwd.isEmpty()) {
            ToastUlits.ShowToastLong(this, R.string.text_pwd_isemty);
            return false;
        }
        if (phoneMunber.isEmpty()) {
            ToastUlits.ShowToastLong(this, R.string.text_phone_number_isemty);
            return false;
        }
        if (!pwd.equals(re_pwd)) {
            ToastUlits.ShowToastShort(this, R.string.text_two_count_pwd_disaffinity);
            return false;
        }
        return true;
    }


    @Override
    public void onResultData(BaseRequest baseRequest, Object object) {
        Boolean bool = (Boolean) object;
        if (bool != null && bool) {
            ToastUlits.ShowToastShort(this, R.string.msg_user_register_ok);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    RegisterActivity.this.finish();
                }
            });

        } else {
            ToastUlits.ShowToastShort(this, R.string.msg_user_register_fail);
        }
    }
}
