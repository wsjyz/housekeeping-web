package com.eighth.housekeeping.domain;

/**
 * 机构
 * Created by dam on 2014/7/3.
 */
public class Corp extends BaseDomain {

    private String corpId;
    private String corpSimpleName;//机构简称
    private String corpName;//全称
    private String loginName;//登录名
    private String password;//密码
    private String description;//备注
    private String status;//状态 ACTIVE激活 NOT_ACTIVE禁用 DELETE删除

}
