package com.eighth.housekeeping.proxy.service;

import com.eighth.housekeeping.domain.SignInfo;
import com.eighth.housekeeping.proxy.annotation.RemoteMethod;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

/**
 * Created by dam on 2014/7/11.
 */
public interface SignInfoService {

    /**
     * 签到
     * @param auntId
     * @return
     */
    @RemoteMethod(methodVarNames={ "auntId" })
    SignInfo sign(String auntId)throws RemoteInvokeException;


}
