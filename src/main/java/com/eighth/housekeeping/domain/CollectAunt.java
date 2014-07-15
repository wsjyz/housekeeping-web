package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

/**
 * Created by dam on 2014/7/6.
 */
@Table(name="t_collect_aunt",comment = "会员收藏的小时工")
public class CollectAunt extends BaseDomain {
    @Column(name="member_id",length = 32,comment = "所属会员")
    private String memberId;
    @Column(name="aunt_id",length = 32,comment = "阿姨|小时工")
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
