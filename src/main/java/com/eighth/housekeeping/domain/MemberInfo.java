package com.eighth.housekeeping.domain;

import java.math.BigDecimal;

import com.eighth.housekeeping.domain.BaseDomain;
import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

/**
 * 会员 Created by dam on 2014/7/3.
 */
@Table(name = "t_member_info", comment = "会员")
public class MemberInfo extends BaseDomain {
	@Column(name = "user_id", pk = true, length = 32)
	private String userId;
	@Column(name = "mobile", length = 15, comment = "手机号")
	private String mobile;// 手机号
	@Column(name = "user_name", length = 20, comment = "姓名")
	private String userName;// 姓名
	@Column(name = "nick_name", length = 20, comment = "昵称")
	private String nickName;// 昵称
	@Column(name = "address", length = 50, comment = "住址")
	private String address;// 住址
	@Column(name = "card", length = 10, comment = "权限 COMMON普通 GOLD金卡 WHITE_GOLD白金 DIAMOND钻石")
	private String card;// 权限 COMMON普通 GOLD金卡 WHITE_GOLD白金 DIAMOND钻石
	@Column(name = "status", length = 15, comment = "状态 ACTIVE激活 NOT_ACTIVE禁用 DELETE删除")
	private String status;// 状态 ACTIVE激活 NOT_ACTIVE禁用 DELETE删除
	@Column(name = "coupon_counts", length = 5, comment = "优惠券数量")
	private String couponCounts;// 优惠券数量
	@Column(name = "coupon_end_time", length = 15, comment = "优惠券到期时间yyyy-MM-dd")
	private String couponEndTime;// 优惠券到期时间yyyy-MM-dd
	@Column(name = "sessionId", length = 32, comment = "登录sessionid")
	private String sessionId;// 登录sessionid
	@Column(name = "push_aunt_info", length = 10, comment = "设置多长时间需要推送服务的天数")
	private int pushAuntInfo;// 设置多长时间需要推送服务的天数

	/**
	 * 头像
	 */
	private ImageObj imageObj;
	/**
	 * 月度消费
	 */
	private BigDecimal monthMoney;
	/**
	 * 年度消费
	 */
	private BigDecimal yearMoney;
	/**
	 * 共消费
	 */
	private BigDecimal sumMoney;
	/**
	 * 订单数
	 */
	private int orderCount;
	/**
	 * 已支付订单数
	 */
	private int payedOrderCount;
	/**
	 * 未支付订单数
	 */
	private int notPayedOrderCount;
	/**
	 * 未支付订单金额
	 */
	private BigDecimal notPayedOrderMoney;
	/**
	 * 优惠劵使用次数
	 */
	private int couponUseCounts;

	
	public ImageObj getImageObj() {
		return imageObj;
	}

	public void setImageObj(ImageObj imageObj) {
		this.imageObj = imageObj;
	}

	public BigDecimal getMonthMoney() {
		return monthMoney;
	}

	public void setMonthMoney(BigDecimal monthMoney) {
		this.monthMoney = monthMoney;
	}

	public BigDecimal getYearMoney() {
		return yearMoney;
	}

	public void setYearMoney(BigDecimal yearMoney) {
		this.yearMoney = yearMoney;
	}

	public BigDecimal getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getPayedOrderCount() {
		return payedOrderCount;
	}

	public void setPayedOrderCount(int payedOrderCount) {
		this.payedOrderCount = payedOrderCount;
	}

	public int getNotPayedOrderCount() {
		return notPayedOrderCount;
	}

	public void setNotPayedOrderCount(int notPayedOrderCount) {
		this.notPayedOrderCount = notPayedOrderCount;
	}

	public BigDecimal getNotPayedOrderMoney() {
		return notPayedOrderMoney;
	}

	public void setNotPayedOrderMoney(BigDecimal notPayedOrderMoney) {
		this.notPayedOrderMoney = notPayedOrderMoney;
	}

	public void setCouponUseCounts(int couponUseCounts) {
		this.couponUseCounts = couponUseCounts;
	}

	public int getCouponUseCounts() {
		return couponUseCounts;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getPushAuntInfo() {
		return pushAuntInfo;
	}

	public void setPushAuntInfo(int pushAuntInfo) {
		this.pushAuntInfo = pushAuntInfo;
	}

	private OpenPage<AuntInfo> auntList;// 收藏的小时工

	public OpenPage<AuntInfo> getAuntList() {
		return auntList;
	}

	public void setAuntList(OpenPage<AuntInfo> auntList) {
		this.auntList = auntList;
	}

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
