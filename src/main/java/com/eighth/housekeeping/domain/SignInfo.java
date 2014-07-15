package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

/**
 * 签到信息
 * Created by dam on 2014/7/4.
 */
@Table(name="t_sign_info",comment = "签到信息")
public class SignInfo extends BaseDomain {
    @Column(name="sign_id",pk=true,length = 32)
    private String signId;
    @Column(name="aunt_id",length = 32,comment = "小时工ID")
    private String auntId;
    @Column(name="signYear",length = 4,comment = "签到年 例如yyyy")
    private String signYear;//签到年 例如yyyy
    @Column(name="signDay",length = 2,comment = "签到日 dd")
    private String signDay;//签到日 dd
    @Column(name="signMonth",length = 2,comment = "签到月 MM")
    private String signMonth;//签到月 MM
    @Column(name="sign_place_desc",length = 20,comment = "签到地点")
    private String signPlaceDesc;//签到地点
    @Column(name="sign_geographic",length = 20,comment = "签到经纬度")
    private String signGeographic;//签到经纬度


    public String getAuntId() {
        return auntId;
    }

    public void setAuntId(String auntId) {
        this.auntId = auntId;
    }

    public String getSignYear() {
        return signYear;
    }

    public void setSignYear(String signYear) {
        this.signYear = signYear;
    }

    public String getSignDay() {
        return signDay;
    }

    public void setSignDay(String signDay) {
        this.signDay = signDay;
    }

    public String getSignMonth() {
        return signMonth;
    }

    public void setSignMonth(String signMonth) {
        this.signMonth = signMonth;
    }

    public String getSignPlaceDesc() {
        return signPlaceDesc;
    }

    public void setSignPlaceDesc(String signPlaceDesc) {
        this.signPlaceDesc = signPlaceDesc;
    }

    public String getSignGeographic() {
        return signGeographic;
    }

    public void setSignGeographic(String signGeographic) {
        this.signGeographic = signGeographic;
    }
}
