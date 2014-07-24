package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.dao.UserDAO;
import com.eighth.housekeeping.domain.MemberInfo;
import com.eighth.housekeeping.domain.VerifyCode;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.UserService;
import com.eighth.housekeeping.utils.CommonStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dam on 2014/7/24.
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;
    @Override
    public MemberInfo login(String mobile) throws RemoteInvokeException {

        return userDAO.findMemberByMobile(mobile);
    }

    @Override
    public MemberInfo add(MemberInfo userInfo) throws RemoteInvokeException {
        userInfo.setUserId(CommonStringUtils.genPK());
        userDAO.saveMember(userInfo);
        return userInfo;
    }

    @Override
    public VerifyCode obtainVerifyCode() throws RemoteInvokeException {
        VerifyCode code = new VerifyCode();
        code.setTokenId(CommonStringUtils.genPK());
        code.setToken(CommonStringUtils.gen4RandomKey());
        userDAO.saveVerifyCode(code);
        return code;
    }

    @Override
    public String checkVerifyCode(VerifyCode token) throws RemoteInvokeException {
        String result = "";
        if(token == null || StringUtils.isBlank(token.getTokenId())
                || StringUtils.isBlank(token.getToken())){
            result = "FAULT";
        }else {
            VerifyCode code = userDAO.findVerifyCodeById(token.getTokenId());
            if(code == null){
                result = "FAULT";
            }else{
                Date currentDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date codeGenDate = null;
                try {
                    codeGenDate = sdf.parse(code.getOptTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long diffResult = (currentDate.getTime() - codeGenDate.getTime())/1000;
                if(diffResult > 30){
                    result = "PAST";
                }else if(token.getToken().equals(code.getToken())){
                    result = "RIGHT";
                }else{
                    result = "FAULT";
                }
            }

        }

        return result;
    }

    @Override
    public String modifyMemberInfo(MemberInfo userInfo) throws RemoteInvokeException {
        if(StringUtils.isNotBlank(userInfo.getUserId())){
            return userDAO.modifyMemberInfo(userInfo);
        }
        return "FAIL";
    }

    @Override
    public MemberInfo modifyPushAuntInfo(String memberId, int days) throws RemoteInvokeException {
        if(StringUtils.isNotBlank(memberId)){
            MemberInfo memberInfo = userDAO.findMemberByMemberId(memberId);
            memberInfo.setPushAuntInfo(days);
            userDAO.modifyMemberInfo(memberInfo);
            return memberInfo;
        }
        return null;
    }
}
