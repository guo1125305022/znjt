package com.znjtgs.entity;

import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyRoadTrafficLightInfo {
    private int trrfficLightId;//红绿灯编号
    private int readTime;
    private int greenTime;
    private int yellowTime;

    public MyRoadTrafficLightInfo() {
            }

    public MyRoadTrafficLightInfo(int trrfficLightId, int readTime, int greenTime, int yellowTime) {
        this.trrfficLightId = trrfficLightId;
        this.readTime = readTime;
        this.greenTime = greenTime;
        this.yellowTime = yellowTime;
    }

    public int getTrrfficLightId() {
        return trrfficLightId;
    }

    public void setTrrfficLightId(int trrfficLightId) {
        this.trrfficLightId = trrfficLightId;
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public int getGreenTime() {
        return greenTime;
    }

    public void setGreenTime(int greenTime) {
        this.greenTime = greenTime;
    }

    public int getYellowTime() {
        return yellowTime;
    }

    public void setYellowTime(int yellowTime) {
        this.yellowTime = yellowTime;
    }


}
