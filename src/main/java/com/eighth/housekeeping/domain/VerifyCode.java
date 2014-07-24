package com.eighth.housekeeping.domain;

import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.domain.annotation.Table;

/**
 * Created by dam on 2014/7/2.
 */
@Table(name="t_verify_code",comment = "验证码")
public class VerifyCode extends BaseDomain{
    @Column(name="token_id",pk=true,length = 32,comment = "当前验证码ID")
    private String tokenId;//当前验证码ID
    @Column(name="token",length = 6,comment = "当前验证码值")
    private String token;//当前验证码值

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
