package com.eighth.housekeeping.domain;

/**
 * Created by dam on 2014/7/3.
 */
public class ImageObj extends BaseDomain {

    private String imageId;
    private String type;//用途 PORTRAIT头像 WORKCASE案例
    private String objId;//所属对象ID
    private int hpixel;//图片高像素值
    private int wpixel;//图片宽像素值

    public int getHpixel() {
        return hpixel;
    }

    public void setHpixel(int hpixel) {
        this.hpixel = hpixel;
    }

    public int getWpixel() {
        return wpixel;
    }

    public void setWpixel(int wpixel) {
        this.wpixel = wpixel;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }
}
