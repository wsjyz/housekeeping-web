package com.eighth.housekeeping.domain;

/**
 * Created by dam on 2014/6/30.
 */
public class BaseDomain implements java.io.Serializable {
    String optTime;//操作时间

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }
}
