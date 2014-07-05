package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.BaseDomain;

/**
 * 会员
 * Created by dam on 2014/7/3.
 */
public class MemberInfo extends BaseDomain {

    private String userId;
    private String mobile;//手机号
    private String userName;//姓名
    private String nickName;//昵称
    private String address;//住址
    private String card;//权限 COMMON普通 GOLD金卡 WHITE_GOLD白金 DIAMOND钻石
    private String status;//状态 ACTIVE激活 NOT_ACTIVE禁用 DELETE删除
    private String couponCounts;//优惠券数量
    private String couponEndTime;//优惠券到期时间yyyy-MM-dd


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCouponCounts() {
        return couponCounts;
    }

    public void setCouponCounts(String couponCounts) {
        this.couponCounts = couponCounts;
    }

    public String getCouponEndTime() {
        return couponEndTime;
    }

    public void setCouponEndTime(String couponEndTime) {
        this.couponEndTime = couponEndTime;
    }
}
