package com.znjtgs;

/**
 * Created by JKX_GXL on 2017/5/11.
 */

public class Cantast {
    public class SharedKey {
        public static final String KEY_FIRST_START = "first_start";
    }

    public class LoginKey {
        public static final String KEY_USER_NAME = "UserName";
        public static final String KEY_USER_PWD = "UserPwd";
        public static final String KEY_AUTO_LOGIN = "auto_logic";
        public static final String KEY_KEEP_PWD = "keep_pwd";
    }

    public class NetKey {
        public static final String KEY_SERVER_ADDRESS = "address";
        public static final String KEY_SERVER_PORT = "port";
        public static final String KEY_SERVER_IP="ip";

        public static final String KEY_SERVER_IP_DEFAULT_VALUES="192.168.1.102";
        public static final String KEY_SERVER_PORT_DEFAULT_VALUE="8080";
    }
    public class UserKey{
        public static final String KEY_EMAIL = "userAuthority";
        public static final String KEY_USER_NAME = "userName";
        public static final String KEY_USER_PWD = "userPassword";
        public static final String KEY_PHONE_NUMBER = "userPhone";
    }

    public class NetResultKey{
        public static final String KEY_RESULT = "Result";//返回
        public static final String KEY_OK = "Ok";//成功
        public static final String KEY_FAILED = "Failed";//失败
        public static final String KEY_MSG="Msg";//返回的消息
    }


    public class BusKey{
        public static final String KEY_BUS_STATION_ID="BusStationId";
    }
}
