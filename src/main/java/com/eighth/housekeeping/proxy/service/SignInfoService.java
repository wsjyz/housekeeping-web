package com.eighth.housekeeping.proxy.service;

import java.util.List;

import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.SignInfo;
import com.eighth.housekeeping.proxy.annotation.RemoteMethod;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

/**
 * Created by dam on 2014/7/11.
 */
public interface SignInfoService {

    /**
     * 签到
     * @param signInfo
     * @return
     */
    @RemoteMethod(methodVarNames={ "signInfo" })
    SignInfo sign(SignInfo signInfo)throws RemoteInvokeException;

    /**
     * 获取阿姨签到详情
     * @param auntId
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "auntId" })
    SignInfo findSignDetail(String auntId)throws RemoteInvokeException;
    
    
    List<SignInfo> getListByAuntId(String auntId);

	OpenPage<SignInfo> searchAttendanceByWeb(String corpName, String auntName,
			OpenPage<SignInfo> page);

}
