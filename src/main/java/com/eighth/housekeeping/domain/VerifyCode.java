package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

/**
 * Created by dam on 2014/7/2.
 */
@Table(name="t_verify_code",comment = "验证码")
public class VerifyCode extends BaseDomain{
    @Column(name="user_id",pk=true,length = 32,comment = "用户ID")
    private String memberId;//当前验证码ID
    @Column(name="token",length = 6,comment = "当前验证码值")
    private String token;//当前验证码值
    @Column(name="mobile",length = 11,comment = "手机号")
    private String mobile;//手机号

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
