package com.eighth.housekeeping.dao;

import com.eighth.housekeeping.domain.MemberInfo;
import com.eighth.housekeeping.domain.VerifyCode;

/**
 * Created by dam on 2014/7/24.
 */
public interface UserDAO {

    void saveVerifyCode(VerifyCode code);

    void deleteVerifyCode(String mobile);

    VerifyCode findVerifyCodeByMobile(String mobile);

    void saveMember(MemberInfo info);

    String modifyMemberInfo(MemberInfo userInfo);

    MemberInfo findMemberByMemberId(String memberId);

    MemberInfo findMemberByMobile(String mobile);
}
