package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

/**
 * Created by dam on 2014/7/6.
 */
@Table(name="t_collect_aunt",comment = "会员收藏的小时工")
public class CollectAunt extends BaseDomain {
    @Column(name="collect_id",pk=true,length = 32)
    private String collectId;
    @Column(name="member_id",length = 32,comment = "所属会员")
    private String memberId;
    @Column(name="aunt_id",length = 32,comment = "阿姨|小时工")
    private String auntId;

    private String collectResult;//收藏结果，传值用 SUCCESS收藏成功|COLLECTED已经收藏

    public String getCollectResult() {
        return collectResult;
    }

    public void setCollectResult(String collectResult) {
        this.collectResult = collectResult;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
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
}
