/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.znjtgs.entity;

public class Envir {
    private int co2;
    private int humi;
    private int light;
    private int pm;
    private int temp;
    private String warningMsg = "";

    public Envir() {
    }

    public Envir(int n, int n2, int n3, int n4, int n5) {
        this.pm = n;
        this.co2 = n2;
        this.temp = n3;
        this.humi = n4;
        this.light = n5;
    }

    public int getCo2() {
        return this.co2;
    }

    public int getHumi() {
        return this.humi;
    }

    public int getLight() {
        return this.light;
    }

    public int getPm() {
        return this.pm;
    }

    public int getTemp() {
        return this.temp;
    }

    public String getWarningMsg() {
        return this.warningMsg;
    }

    public void setCo2(int n) {
        this.co2 = n;
    }

    public void setHumi(int n) {
        this.humi = n;
    }

    public void setLight(int n) {
        this.light = n;
    }

    public void setPm(int n) {
        this.pm = n;
    }

    public void setTemp(int n) {
        this.temp = n;
    }

    public void setWarningMsg(String string2) {
        this.warningMsg = string2;
    }
}

