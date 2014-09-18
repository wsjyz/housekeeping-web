package com.eighth.housekeeping.proxy.service;


import java.util.List;

import com.eighth.housekeeping.domain.Corp;
import com.eighth.housekeeping.domain.ImageObj;
import com.eighth.housekeeping.domain.MemberInfo;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.VerifyCode;
import com.eighth.housekeeping.proxy.annotation.RemoteMethod;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

/**
 * Created by dam on 2014/6/27.
 */
public interface UserService {


    /**
     * 新增
     * 必填项mobile、userName、password
     * @param userInfo
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "userInfo" })
    MemberInfo add(MemberInfo userInfo)throws RemoteInvokeException;

    /**
     * 获取注册验证码
     * @param mobile 手机号
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "mobile" })
    VerifyCode obtainVerifyCode(String mobile)throws RemoteInvokeException;

    /**
     * 验证注册码是否正确
     * 必填项 mobile、token 是obtainRegistCode方法返回的
     * @return 如果是32位UUID则代表登录成功返回值为memberId、FAULT错误、PAST过期
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "code" })
    String checkVerifyCode(VerifyCode code)throws RemoteInvokeException;

    /**
     * 修改个人信息
     * @param userInfo
     * @return SUCCESS成功，FAIL失败
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "userInfo" })
    String modifyMemberInfo(MemberInfo userInfo)throws RemoteInvokeException;

    /**
     * 设置阿姨信息推送频率
     * @param memberId
     * @param days
     * @return MemberInfo中有pushAuntInfo字段，memberId字段
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "memberId", "days" })
    MemberInfo modifyPushAuntInfo(String memberId,int days)throws RemoteInvokeException;

    /**
     * 获取会员信息
     * @param memberId
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "memberId"})
    MemberInfo findMemberByMemberId(String memberId)throws RemoteInvokeException;

    /**
     * 删除某个会员信息
     */
    @RemoteMethod(methodVarNames={"memberId"})
    String deleteByMemberId(String memberId) throws RemoteInvokeException;

    /**
     * 获取会员信息
     * @param memberId
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "memberId"})
    MemberInfo findMemberByMemberIdWeb(String memberId)throws RemoteInvokeException;
    
    
	OpenPage<MemberInfo> findUserPage(String mobile, String nickName, OpenPage page) throws RemoteInvokeException;
	String saveImageObj(ImageObj imageObj);
	void deleteImageObj(String objId,String objType);
	
	void deleteImageObjByImageId(String imageId);


}
