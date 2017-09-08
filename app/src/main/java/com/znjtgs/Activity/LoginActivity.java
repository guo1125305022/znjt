package com.znjtgs.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.CompoundButton;

import com.znjtgs.Cantast;
import com.znjtgs.R;
import com.znjtgs.dialog.DialogNetSetting;
import com.znjtgs.request.BaseRequest;
import com.znjtgs.request.UserLoginRequest;
import com.znjtgs.ulits.NotifiactionUlits;
import com.znjtgs.ulits.ToastUlits;
import com.znjtgs.ulits.UserManager;

/**
 * Created by JKX_GXL on 2017/5/11.
 */

public class LoginActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, BaseRequest.OnResultDataListener {
    private AppCompatEditText et_account;
    private AppCompatEditText et_pwd;
    private AppCompatCheckBox accb_keep_password;
    private AppCompatCheckBox accb_auto_login;
    private UserLoginRequest userLoginRequest;

    /**
     * 初始化布局
     *
     * @return
     */
    @Override
    protected int initLayouotId() {
        return R.layout.activity_user_login_layout;
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initComponent() {
        setTitleView(R.layout.activity_user_login_title_layout);
        setMainTitle(R.string.txt_user_login_title);
        userLoginRequest = new UserLoginRequest(this);
        et_account = (AppCompatEditText) findViewById(R.id.acet_account);
        et_pwd = (AppCompatEditText) findViewById(R.id.acet_pwd);
        accb_auto_login = (AppCompatCheckBox) findViewById(R.id.accb_auto_login);
        accb_keep_password = (AppCompatCheckBox) findViewById(R.id.accb_keep_password);
        findViewById(R.id.ll_login_net_setting).setOnClickListener(this);
        initCompoentListener();
        initCompoentData();
    }

    /**
     * 初始化组件监听器
     */
    private void initCompoentListener() {
        accb_auto_login.setOnCheckedChangeListener(this);
        accb_keep_password.setOnCheckedChangeListener(this);
        findViewById(R.id.acb_login).setOnClickListener(this);
        findViewById(R.id.acb_register).setOnClickListener(this);
    }

    /**
     * 初始化组件数据
     */
    private void initCompoentData() {
        et_account.setText(getSharedPreferebces().getString(Cantast.LoginKey.KEY_USER_NAME, ""));
        boolean keep_pwd = getSharedPreferebces().getBoolean(Cantast.LoginKey.KEY_KEEP_PWD, false);
        boolean auto_login = getSharedPreferebces().getBoolean(Cantast.LoginKey.KEY_AUTO_LOGIN, false);
        accb_keep_password.setChecked(keep_pwd);
        accb_auto_login.setChecked(auto_login);
        if (keep_pwd) {
            et_pwd.setText(getSharedPreferebces().getString(Cantast.LoginKey.KEY_USER_PWD, ""));
        }
        if (auto_login) {
            logic();
        }
    }

    private void logic() {
        String account = et_account.getText().toString();
        String pwd = et_pwd.getText().toString();
        if (account.isEmpty()) {
            ToastUlits.ShowToastShort(this, R.string.text_acconut_isemty);
            return;
        }
        if (pwd.isEmpty()) {
            ToastUlits.ShowToastShort(this, R.string.text_pwd_isemty);
            return;
        }
        //登陆实际逻辑代码
        loginServer(account, pwd);
    }

    /**
     * 登陆服务器
     *
     * @param account
     * @param pwd
     */
    private void loginServer(String account, String pwd) {
        userLoginRequest.setUserName(account);
        userLoginRequest.setUserPwd(pwd);
        userLoginRequest.setListener(this);
        userLoginRequest.connection();
    }

    private void startMain() {
        //保存配置信息
        saveComponentData();
        NotifiactionUlits.ShowNotifiaction(this, "登陆提示", "登陆成功", MainActivity.class, 0x444);
        startActivity(new Intent(this, MainActivity.class));
        UserManager userManager = UserManager.getUserManager();
        String userName = et_account.getText().toString();
        userManager.setUserName(userName);
        userManager.setLoginState(true);
        this.finish();

    }

    /**
     * 保存信息
     */
    private void saveComponentData() {
        SharedPreferences.Editor editor = getSharedPreferebces().edit();
        editor.putString(Cantast.LoginKey.KEY_USER_PWD, et_pwd.getText().toString());
        editor.putString(Cantast.LoginKey.KEY_USER_NAME, et_account.getText().toString());
        editor.putBoolean(Cantast.LoginKey.KEY_KEEP_PWD, accb_keep_password.isChecked());
        editor.putBoolean(Cantast.LoginKey.KEY_AUTO_LOGIN, accb_auto_login.isChecked());
        editor.apply();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.accb_auto_login:
                if (isChecked) {
                    accb_keep_password.setChecked(true);
                }
                accb_auto_login.setChecked(isChecked);
                break;
            case R.id.accb_keep_password:
                if (accb_auto_login.isChecked()) {
                    accb_auto_login.setChecked(false);
                }
                accb_keep_password.setChecked(isChecked);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acb_login:
                logic();
                break;
            case R.id.acb_register:
                startActivity(new Intent(this, RegisterActivity.class));//打开用户注册
                break;
            case R.id.ll_login_net_setting:
                ShowNetSettingDialog();
                break;
        }
    }


    public void ShowNetSettingDialog() {
        Dialog dialog = new DialogNetSetting(this);
        dialog.show();
    }


    @Override
    public void onResultData(BaseRequest baseRequest, Object object) {
        if (baseRequest == userLoginRequest) {
            if (object == null) {
                return;
            }
            boolean b = (boolean) object;
            if (b) {
                startMain();
            }
        }
    }
}
