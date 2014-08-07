package com.eighth.housekeeping.proxy.service;

import com.eighth.housekeeping.domain.APKVersion;
import com.eighth.housekeeping.domain.FeedBack;
import com.eighth.housekeeping.domain.SystemManage;
import com.eighth.housekeeping.proxy.annotation.RemoteMethod;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

/**
 * Created by dam on 2014/7/4.
 */
public interface SystemService {
    /**
     * 更新apk
     * @param currentVersionCode 当前客户端的版本
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "currentVersionCode" })
    APKVersion updateAPK(String currentVersionCode)throws RemoteInvokeException;

    /**
     * 获取系统设置信息（会员端首页）
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod()
    SystemManage findSystemManage()throws RemoteInvokeException;

    /**
     * 保存意见反馈
     * @param feedBack
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "feedBack" })
    FeedBack saveFeedBack(FeedBack feedBack)throws RemoteInvokeException;

    /**
     * 移动端退出登录 (阿姨端、会员端)
     * @param userId
     * @param userType AUNT阿姨（小时工）|MEMBER会员
     * @return SUCCESS成功|FAIL失败
     * @throws RemoteInvokeException
     */
    @RemoteMethod()
    String appLogout(String userId,String userType)throws RemoteInvokeException;


}
