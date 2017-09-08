package com.znjtgs.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.znjtgs.Cantast;
import com.znjtgs.R;
import com.znjtgs.ulits.ToastUlits;

/**
 * Created by JKX_GXL on 2017/5/11.
 */

public class DialogNetSetting extends BaseDialog implements View.OnClickListener {
    private AppCompatEditText et_server_address;//服务器IP地址编辑组件
    private AppCompatEditText et_server_port;//服务器端口编辑组件

    public DialogNetSetting(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog_net_setting_layout;
    }

    @Override
    protected void initComponten() {
        setTitle(R.string.text_net_setting);
        et_server_address = (AppCompatEditText) findViewById(R.id.acet_server_address);
        et_server_port = (AppCompatEditText) findViewById(R.id.acet_server_port);
        addButtonConfirmClickListenre(R.string.text_confirm, this);
        addButtongCancelClickListsener(R.string.text_cancel, this);
        initData();
    }

    private void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acb_base_dialog_cancel:
                this.dismiss();
                break;
            case R.id.acb_base_dialog_confirm:
                setServerAdressAndPort();
                break;
        }
    }

    private void setServerAdressAndPort() {
        if (!checkServerAddress()) {
            return;
        }
        String addres = et_server_address.getText().toString();
        String strPort = et_server_port.getText().toString();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Cantast.NetKey.KEY_SERVER_ADDRESS, addres);
        edit.putString(Cantast.NetKey.KEY_SERVER_PORT, strPort);
        edit.putString(Cantast.NetKey.KEY_SERVER_IP,addres+":"+strPort);
        edit.apply();
        ToastUlits.ShowToastShort(getContext(), getContext().getString(R.string.text_save_server_succeed, new Object[]{addres, strPort}));
        this.dismiss();

    }

    private boolean checkServerAddress() {
        String addres = et_server_address.getText().toString();
        String strPort = et_server_port.getText().toString();
        boolean b= addres.contains(".");
        String[] ips = addres.split("\\.");
        if (ips.length != 4) {
            ToastUlits.ShowToastShort(getContext(), R.string.text_ip_fromat_error);
            return false;
        }
        int ip0;
        int ip1;
        int ip2;
        int ip3;
        int port;
        try {
            ip0 = Integer.parseInt(ips[0]);
            ip1 = Integer.parseInt(ips[1]);
            ip2 = Integer.parseInt(ips[2]);
            ip3 = Integer.parseInt(ips[3]);
            port = Integer.parseInt(strPort);

        } catch (Exception e) {
            ToastUlits.ShowToastShort(getContext(), R.string.text_server_ip_error);
            return false;
        }
        if (port < 0 && port > 65530) {
            ToastUlits.ShowToastShort(getContext(), R.string.text_server_port_error);
            return false;
        }
        if (ip0 < 1 && ip0 > 254) {
            ToastUlits.ShowToastShort(getContext(), R.string.text_server_ip_error);
            return false;
        }
        if (ip1 < 0 && ip1 > 254) {
            ToastUlits.ShowToastShort(getContext(), R.string.text_server_ip_error);
            return false;
        }
        if (ip2 < 0 && ip2 > 254) {
            ToastUlits.ShowToastShort(getContext(), R.string.text_server_ip_error);
            return false;
        }
        if (ip3 < 1 && ip3 > 254) {
            ToastUlits.ShowToastShort(getContext(), R.string.text_server_ip_error);
            return false;
        }

        return true;
    }
}
