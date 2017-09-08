package com.znjtgs.config;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.znjtgs.Cantast;

public class AppNetParams {



    public class RequestAction {
        public static final String GET_BUS_CAPACITY="GetBusCapacit.do";
        //获取小车速度
        public static final String GET_CAR_SPEED = "GetCarSpeed.do";
        //设置小车移动状态
        public static final String SET_CAR_MOVE = "SetCarMove.do";
        //获取小车停车余额
        public static final String GET_CAR_ACCOUNT_BALANCE = "GetCarAccountBalance.do";
        //小车停车充值
        public static final String SET_CAR_ACCOUNT_RECHARGE = "SetCarAccountRecharge.do";
        //红绿等查询
        public static final String GET_TRAFFIC_LINGT_CONFIG_ACTION = "GetTrafficLightConfigAction.do";
        //查询红绿灯
        public static final String GET_TRAFFIC_LIGHT_NOW_STATUS="GetTrafficLightNowStatus.do";

        //停车场费率设置
        public static final String SET_PARK_RATE = "SetParkRate.do";
        //获取停车场费率
        public static final String GET_PARK_RATE = "GetParkRate.do";
        //获取停车场空闲车位
        public static final String GET_PARK_FREE = "GetParkFree.do";
        //获取所有传感器
        public static final String GET_ALL_SENSE = "GetAllSense.do";
        //获取单个传感器数据
        public static final String GET_SEMSOR_BY_SENSOR_NAME="GetSenseByName.do";
        //获取光线传感器的值
        public static final String GET_LIGHT_SENSE_VALVE = "GetLightSenseValve.do";

        //查询公交车站台信息
        public static final String GET_BUSSTATION_INFO = "GetBusStationInfo.do";
        //获取道路环境信息
        public static final String GET_ROAD_STATUS = "GetRoadStatus.do";
        //用户登陆
        public static final String USER_LOGIN="UserLoginRequst.do";
        //用户注册
        public static final String USER_REGISTER="UserRegister.do";

    }



    public class ParameterName {
        public static final String PARK_FREE_ID="ParkFreeId";

        public static final String KEY_BUS_CAPACITY="BusCapacity";
        //小车ID
        public static final String CAR_ID = "CarId";
        public static final String CAR_BALANCE="Balance";
        //money
        public static final String MONEY = "Money";
        //光照传感器ID
        public static final String TRAFFIC_LIGHT_ID = "TrafficLightID";
        //类型
        public static final String RATE_TYPE = "RateType";
        //公交站台ID
        public static final String BUSS_STATION_ID = "BusStationId";
        //公交车ID
        public static final String BUS_ID="BusId";
        //公交距离
        public static final String BUS_DISTANCE="Distance";
        //道路ID
        public static final String ROAD_ID = "RoadId";
        public static final String ROAD_STATUS="Status";//道路状态
        public static final String CAR_SPEED ="CarSpeed" ;
        public static final String KEY_TRAFFIC_LIGHT_ID = "TrafficLightId";//红绿灯Id
        public static final String KEY_TRAFFIC_TYPE = "traffic_type";//红绿灯的灯类型
        public static final String KEY_RED_TIME = "RedTime";//红灯时间
        public static final String KEY_YELLOW_TIME = "YellowTime";//黄时间
        public static final String KEY_GREEN_TIME = "GreenTime";//绿灯时间
        public static final String KEY_TIME="Time";

        public static final String SENSE_NAME="SenseName";
        public static final String SENSE_NAME_PM="pm2.5";
        public static final String CAR_ACTION = "CarAction";
        public static final String CAR_ACTION_START="Start";
        public static final String CAR_ACTION_STOP="Stop";

    }


}
