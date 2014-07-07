package com.eighth.housekeeping.domain;

/**
 * Created by dam on 2014/7/6.
 */
public class CollectAunt extends BaseDomain {

    private String memberId;
    private String auntId;

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
}
