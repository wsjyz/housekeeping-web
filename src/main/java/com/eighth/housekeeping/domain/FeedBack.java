package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

/**
 * Created by dam on 2014/8/7.
 */
@Table(name = "t_feedback", comment = "意见反馈")
public class FeedBack extends BaseDomain {

	@Column(name = "feedback_id", pk = true, length = 32)
	private String feedbackId;
	@Column(name = "member_id", length = 32, comment = "所属会员")
	private String memberId;
	@Column(name = "aunt_id", length = 32, comment = "阿姨|小时工")
	private String auntId;
	@Column(name = "content", length = 32, comment = "反馈内容")
	private String content;

	private String userName;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getAuntId() {
		return auntId;
	}

	public void setAuntId(String auntId) {
		this.auntId = auntId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
