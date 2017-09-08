package com.znjtgs.entity;


import com.znjtgs.R;

/**
 * Created by Administrator on 2017/5/30 0030.
 */

public class SensorinfoBySensorName {
    private String sensorName;
    private int curDate;
    private int maxDate;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public int getCurDate() {
        return curDate;
    }

    public void setCurDate(int curDate) {
        this.curDate = curDate;
    }

    public int getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(int maxDate) {
        this.maxDate = maxDate;
    }

    public int getStateDrawable() {
        if (curDate >= maxDate) {
            return R.drawable.jinggao;
        }
        return R.drawable.tongguo;
    }

    public SensorinfoBySensorName(){

    }

    public SensorinfoBySensorName(String sensorName, int curDate, int maxDate) {
        this.sensorName = sensorName;
        this.curDate = curDate;
        this.maxDate = maxDate;
    }
}
