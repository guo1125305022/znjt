package com.znjtgs.entity;

import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TrafficLightInfo {
    private int trrfficLightId;//红绿灯编号
    private int roadId;//道路编号
    private int souresGreedTime;//原绿灯时间
    private int roadStatus = 0;//道路拥挤程度

    public void setRoadStatus(int roadStatus) {
        this.roadStatus = roadStatus;
        setAlert(roadStatus > 3);
    }

    public void setSouresGreedTime(int souresGreedTime) {
        this.souresGreedTime = souresGreedTime;
    }

    public int getRoadStatus() {
        return roadStatus;
    }

    private String alertTime = "";
    private boolean alert = false;

    public String getAlertTime() {
        return alertTime;
    }

    public int getRoadId() {
        return roadId;
    }

    public int getTrrfficLightId() {
        return trrfficLightId;
    }

    public int getItemBackGroundColor() {
        return (roadStatus < 4 ? Color.WHITE : Color.RED);
    }

    public int getSouresGreedTime() {
        return souresGreedTime;
    }

    //当前路灯时间
    public int getNowGreedTime() {
        if (!alert) {
            return souresGreedTime;
        }
        return souresGreedTime + 30;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
        alertTime = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
    }

    public TrafficLightInfo(int trrfficLightId, int roadId, int souresGreedTime) {
        this.trrfficLightId = trrfficLightId;
        this.roadId = roadId;
        this.souresGreedTime = souresGreedTime;
    }
}
