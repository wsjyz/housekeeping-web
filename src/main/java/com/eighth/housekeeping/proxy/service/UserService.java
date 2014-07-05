package com.eighth.housekeeping.proxy.service;


import com.eighth.housekeeping.domain.MemberInfo;
import com.eighth.housekeeping.domain.VerifyCode;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

/**
 * Created by dam on 2014/6/27.
 */
public interface UserService {

    /**
     * 登录
     * @param mobile
     * @return
     * @throws RemoteInvokeException
     */
    MemberInfo login(String mobile) throws RemoteInvokeException;

    /**
     * 新增
     * 必填项mobile、loginName、password
     * @param userInfo
     * @return
     * @throws RemoteInvokeException
     */
    MemberInfo add(MemberInfo userInfo)throws RemoteInvokeException;

    /**
     * 获取注册验证码
     * @return
     * @throws RemoteInvokeException
     */
    VerifyCode obtainVerifyCode()throws RemoteInvokeException;

    /**
     * 验证注册码是否正确
     * @param tokenId 是obtainRegistCode方法返回的
     * @return RIGHT正确、FAULT错误、PAST过期
     * @throws RemoteInvokeException
     */
    String checkVerifyCode(String tokenId)throws RemoteInvokeException;

    /**
     * 修改个人信息
     * @param userInfo
     * @return SUCCESS成功，FAIL失败
     * @throws RemoteInvokeException
     */
    String modifyMemberInfo(MemberInfo userInfo)throws RemoteInvokeException;


}
