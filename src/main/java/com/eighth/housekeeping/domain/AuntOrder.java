package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

import java.math.BigDecimal;

/**
 * Created by dam on 2014/7/3.
 */
@Table(name = "t_aunt_order", comment = "小时工订单")
public class AuntOrder extends BaseDomain {
	@Column(name = "order_id", pk = true, length = 32)
	private String orderId;
	@Column(name = "order_use", length = 13, comment = "用途:小时工HOURLY_WORKER 新居开荒NEW_HOUSE")
	private String orderUse;
	@Column(name = "work_time", length = 16, comment = "服务时间yyyy-MM-dd HH:mm")
	private String workTime;
	@Column(name = "work_length", length = 3, comment = "服务时长")
	private int workLength;
	@Column(name = "address", length = 50, comment = "服务地址")
	private String address;
	@Column(name = "description", length = 100, comment = "备注")
	private String description;// 备注
	@Column(name = "unit_price", length = 5, decimal = 2, comment = "单价")
	private BigDecimal unitPrice;// 单价
	@Column(name = "total_price", length = 5, decimal = 2, comment = "总价")
	private BigDecimal totalPrice;// 总价
	@Column(name = "actual_price", length = 5, decimal = 2, comment = "实付价格")
	private BigDecimal actualPrice;// 实付价格
	@Column(name = "use_coupon_count", length = 1, comment = "是否使用优惠券 0=没有使用")
	private int useCouponCount;// 是否使用优惠券 0=没有使用
	@Column(name = "floor_space", length = 5, comment = "房屋面积")
	private int floorSpace;// 房屋面积
	@Column(name = "order_status", length = 12, comment = "支付状态 ONLINE_PAYED线上已支付  NOT_PAY未支付")
	private String orderStatus;
	@Column(name = "special_need", length = 100, comment = "特殊要求")
	private String specialNeed;
	@Column(name = "contact_way", length = 50, comment = "联系方式")
	private String contactWay;
	@Column(name = "order_no", length = 50, comment = "订单号")
	private String orderNo;
	// 以下是关系
	@Column(name = "aunt_id", length = 32, comment = "所属阿姨")
	private String auntId;// 阿姨
	@Column(name = "user_id", length = 32, comment = "提交人ID")
	private String userId;// 提交人ID

	private AuntInfo auntInfo;

	private String auntName;
	private String corpName;
	private String profit;

	public String getAuntName() {
		return auntName;
	}

	public void setAuntName(String auntName) {
		this.auntName = auntName;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public void setAuntInfo(AuntInfo auntInfo) {
		this.auntInfo = auntInfo;
	}

	public AuntInfo getAuntInfo() {
		return auntInfo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

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
