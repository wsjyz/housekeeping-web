package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.BaseDomain;
import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dam on 2014/8/4.
 */
@Table(name = "t_system_manage", comment = "系统设置")
public class SystemManage extends BaseDomain {

	@Column(name = "system_id", length = 300, comment = "首页提示信息，以$#分隔")
	private String systemId;
	@Column(name = "main_page_tip", length = 300, comment = "首页提示信息，以$#分隔")
	private String mainPageTip;
	@Column(name = "hourly_unit_price", length = 5, decimal = 2, comment = "小时工默认单价")
	private BigDecimal hourlyUnitPrice;
	@Column(name = "new_house_unit_price", length = 5, decimal = 2, comment = "新居开荒默认单价")
	private BigDecimal newHouseUnitPrice;
	@Column(name = "push_info_time", length = 5, comment = "推送时间")
	private String pushInfoTime;
	@Column(name = "push_info", length = 100, comment = "推送内容")
	private String pushInfo;
	@Column(name = "push_info_interval", length = 1, comment = "推送间隔")
	private int pushInfoInterval;
	@Column(name = "search_key", length = 100, comment = "搜索关键字以|分隔")
	private String searchKey;
	@Column(name = "app_phone", length = 11, comment = "APP的联系电话")
	private String appPhone;
	@Column(name = "app_logo", length = 100, comment = "app logo图片地址")
	private String appLogo;
	@Column(name = "coupon_unit_price", length = 5, decimal = 2, comment = "优惠券默认单价")
	private BigDecimal couponUnitPrice;

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemId() {
		return systemId;
	}

	private List<ImageObj> images;

	public void setImages(List<ImageObj> images) {
		this.images = images;
	}

	public List<ImageObj> getImages() {
		return images;
	}

	public BigDecimal getCouponUnitPrice() {
		return couponUnitPrice;
	}

	public void setCouponUnitPrice(BigDecimal couponUnitPrice) {
		this.couponUnitPrice = couponUnitPrice;
	}

	public String getAppPhone() {
		return appPhone;
	}

	public void setAppPhone(String appPhone) {
		this.appPhone = appPhone;
	}

	public String getAppLogo() {
		return appLogo;
	}

	public void setAppLogo(String appLogo) {
		this.appLogo = appLogo;
	}

	public String getMainPageTip() {
		return mainPageTip;
	}

	public void setMainPageTip(String mainPageTip) {
		this.mainPageTip = mainPageTip;
	}

	public BigDecimal getHourlyUnitPrice() {
		return hourlyUnitPrice;
	}

	public void setHourlyUnitPrice(BigDecimal hourlyUnitPrice) {
		this.hourlyUnitPrice = hourlyUnitPrice;
	}

	public BigDecimal getNewHouseUnitPrice() {
		return newHouseUnitPrice;
	}

	public void setNewHouseUnitPrice(BigDecimal newHouseUnitPrice) {
		this.newHouseUnitPrice = newHouseUnitPrice;
	}

	public String getPushInfoTime() {
		return pushInfoTime;
	}

	public void setPushInfoTime(String pushInfoTime) {
		this.pushInfoTime = pushInfoTime;
	}

	public String getPushInfo() {
		return pushInfo;
	}

	public void setPushInfo(String pushInfo) {
		this.pushInfo = pushInfo;
	}

	public int getPushInfoInterval() {
		return pushInfoInterval;
	}

	public void setPushInfoInterval(int pushInfoInterval) {
		this.pushInfoInterval = pushInfoInterval;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
}
