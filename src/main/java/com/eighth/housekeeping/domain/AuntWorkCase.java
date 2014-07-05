package com.eighth.housekeeping.domain;

import java.util.List;

/**
 * Created by dam on 2014/7/3.
 */
public class AuntWorkCase extends BaseDomain {

    private String caseId;
    private String description;//描述
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
