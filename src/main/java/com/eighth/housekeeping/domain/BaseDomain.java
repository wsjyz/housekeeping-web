package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dam on 2014/6/30.
 */
public class BaseDomain implements java.io.Serializable {

    @Column(name="corp_id",length = 32,comment = "所属机构")
    private String corpId;//所属机构
    @Column(name="opt_time",length = 20,comment = "操作时间yyyy-MM-dd HH:mm:ss")
    String optTime;//操作时间

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getOptTime() {
        if(optTime == null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            optTime = sdf.format(new Date());
        }
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }
}
