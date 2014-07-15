package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

import java.util.List;

/**
 * Created by dam on 2014/7/3.
 */
@Table(name="t_aunt_work_case",comment = "阿姨|小时工案例")
public class AuntWorkCase extends BaseDomain {
    @Column(name="case_id",pk=true,length = 32)
    private String caseId;
    @Column(name="description",length = 100,comment = "简介")
    private String description;

    private List<ImageObj> images;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImageObj> getImages() {
        return images;
    }

    public void setImages(List<ImageObj> images) {
        this.images = images;
    }
}
