package com.eighth.housekeeping.domain;

/**
 * 评论
 * Created by dam on 2014/7/4.
 */
public class Review extends BaseDomain {

    private String reviewId;
    private String reviewTag;//VERY_SATISFY非常满意|SATISFY满意|NOT_SATISFY不满意
    private String reviewContent;//评论内容
    private String createUserId;//创建人
    private String auntId;//被评论人

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
