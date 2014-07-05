package com.eighth.housekeeping.domain;

import java.math.BigDecimal;

/**
 * Created by dam on 2014/7/3.
 */
public class AuntOrder extends BaseDomain {

    private String orderId;//订单编号
    //用途:小时工HOURLY_WORKER 新居开荒NEW_HOUSE
    private String orderUse;
    private String workTime;//服务时间yyyy-MM-dd HH:mm
    private int workLength;//服务时长
    private String address;//服务地址
    private String description;//备注
    private BigDecimal unitPrice;//单价
    private BigDecimal totalPrice;//总价
    private BigDecimal actualPrice;//实付价格
    private int useCouponCount;//是否使用优惠券 0=没有使用
    private int floorSpace;//房屋面积
    //支付状态 ONLINE_PAYED线上已支付  NOT_PAY未支付
    private String orderStatus;
    private String specialNeed;//特殊要求
    private String contactWay;//联系方式

    //以下是关系

    private String corpId;//所属机构
    private String auntId;//阿姨
    private String userId;//提交人ID


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderUse() {
        return orderUse;
    }

    public void setOrderUse(String orderUse) {
        this.orderUse = orderUse;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public int getWorkLength() {
        return workLength;
    }

    public void setWorkLength(int workLength) {
        this.workLength = workLength;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public int getUseCouponCount() {
        return useCouponCount;
    }

    public void setUseCouponCount(int useCouponCount) {
        this.useCouponCount = useCouponCount;
    }

    public int getFloorSpace() {
        return floorSpace;
    }

    public void setFloorSpace(int floorSpace) {
        this.floorSpace = floorSpace;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSpecialNeed() {
        return specialNeed;
    }

    public void setSpecialNeed(String specialNeed) {
        this.specialNeed = specialNeed;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAuntId() {
        return auntId;
    }

    public void setAuntId(String auntId) {
        this.auntId = auntId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
