package com.eighth.housekeeping.proxy.service;

import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.AuntWorkCase;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.proxy.annotation.RemoteMethod;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

import java.util.List;

/**
 * 阿姨相关
 * Created by dam on 2014/7/4.
 */
public interface AuntService {

    /**
     * 阿姨登录
     * @param mobile 手机号
     * @param password 密码
     * @return AuntInfo.loginResult SUCCESS成功|ACOUNT_NOT_CORRECT用户名或密码错
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "mobile","password" })
    AuntInfo login(String mobile,String password)throws RemoteInvokeException;

    /**
     * 阿姨详情 需要加载统计信息（阿姨端）
     * @param auntId
     * @return
     */
    @RemoteMethod(methodVarNames={ "auntId" })
    AuntInfo findAuntByIdForAunt(String auntId)throws RemoteInvokeException;

    /**
     * 阿姨详情 需要加载案例（会员端）
     * @param auntId
     * @return
     */
    @RemoteMethod(methodVarNames={ "auntId" })
    AuntInfo findAuntByIdForMember(String auntId)throws RemoteInvokeException;

    /**
     * 工作效果详情
     * @param caseId
     * @return
     */
    @RemoteMethod(methodVarNames={ "caseId" })
    AuntWorkCase findCaseById(String caseId)throws RemoteInvokeException;

    /**
     * 阿姨评论列表
     * @param reviewTag 见Review,ALL全部
     * @param auntId
     * @return
     */
    @RemoteMethod(methodVarNames={ "reviewTag","auntId","page" })
    OpenPage<Review> findReviewByAuntId(String reviewTag,String auntId,OpenPage page)throws RemoteInvokeException;

    /**
     * 重置密码
     * @return SUCCESS成功|OLD_PSW_UNCORRECT旧密码错误|FAIL重置失败
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "auntId","oldPassword","newPassword" })
    String resetPassword(String auntId,String oldPassword,String newPassword)throws RemoteInvokeException;
    
    String addAuntInfo(AuntInfo auntInfo);
	String updateAuntInfo(AuntInfo auntInfo);
	String deleteAunt(String auntId);

    /**
     * 修改阿姨的经纬度
     * @param auntId
     * @param longitude
     * @param latitude
     * @return SUCCESS成功|FAIL失败
     */
    @RemoteMethod(methodVarNames={ "auntId","longitude","latitude" })
    String modifyAuntGeo(String auntId,double longitude,double latitude)throws RemoteInvokeException;

    /**
     * 根据条件查询阿姨列表
     * @param auntInfo
     * @param page
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "auntInfo","page" })
    OpenPage<AuntInfo> searchAuntByCondition(AuntInfo auntInfo,OpenPage<AuntInfo> page)throws RemoteInvokeException;
    
    
    /**
     * 阿姨详情 需要加载案例（平台端）
     * @param auntId
     * @return
     */
    @RemoteMethod(methodVarNames={ "auntId" })
    AuntInfo findAuntByIdByWeb(String auntId)throws RemoteInvokeException;
    @RemoteMethod(methodVarNames={ "card","mobile" })
	AuntInfo checkIdentityByCardAndMobile(String card,String mobile);

    OpenPage<AuntInfo> searchAuntByWeb(String corpId,String userName,String mobile,OpenPage<AuntInfo> page)throws RemoteInvokeException;

	Boolean checkIdentityCard(String identityCard);

}