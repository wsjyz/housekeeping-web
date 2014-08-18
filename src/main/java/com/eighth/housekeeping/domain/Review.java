package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

/**
 * 评论
 * Created by dam on 2014/7/4.
 */
@Table(name="t_review",comment = "评论")
public class Review extends BaseDomain {
    @Column(name="review_id",pk=true,length = 32)
    private String reviewId;
    @Column(name="review_tag",length = 32,comment = "VERY_SATISFY非常满意|SATISFY满意|NOT_SATISFY不满意")
    private String reviewTag;//VERY_SATISFY非常满意|SATISFY满意|NOT_SATISFY不满意
    @Column(name="review_content",length = 100,comment = "评论内容")
    private String reviewContent;//评论内容
    @Column(name="create_user_id",length = 32,comment = "创建人")
    private String createUserId;//创建人
    @Column(name="aunt_id",length = 32,comment = "被评论人")
    private String auntId;//被评论人
    private String createUserName;//创建人用户名

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getAuntId() {
        return auntId;
    }

    public void setAuntId(String auntId) {
        this.auntId = auntId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewTag() {
        return reviewTag;
    }

    public void setReviewTag(String reviewTag) {
        this.reviewTag = reviewTag;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
}
