package com.eighth.housekeeping.domain;

/**
 * 签到信息
 * Created by dam on 2014/7/4.
 */
public class SignInfo extends BaseDomain {

    private String auntId;
    private String signYear;//签到年 例如yyyy
    private String signDay;//签到日 dd
    private String signMonth;//签到月 MM
    private String signPlaceDesc;//签到地点
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
