package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

/**
 * 机构
 * Created by dam on 2014/7/3.
 */
@Table(name="t_corp",comment = "机构")
public class Corp extends BaseDomain {
    @Column(name="corp_id",pk=true,length = 32)
    private String corpId;
    @Column(name="corp_simple_name",comment = "机构简称",length = 20)
    private String corpSimpleName;//机构简称
    @Column(name="corp_name",comment = "全称",length = 50)
    private String corpName;//全称
    @Column(name="login_name",comment = "登录名",length = 20)
    private String loginName;//登录名
    @Column(name="password",comment = "密码",length = 32)
    private String password;//密码
    @Column(name="description",comment = "备注",length = 100)
    private String description;//备注
    @Column(name="status",comment = "状态 ACTIVE激活 NOT_ACTIVE禁用 DELETE删除",length = 15)
    private String status;//状态 ACTIVE激活 NOT_ACTIVE禁用 DELETE删除

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpSimpleName() {
        return corpSimpleName;
    }

    public void setCorpSimpleName(String corpSimpleName) {
        this.corpSimpleName = corpSimpleName;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
