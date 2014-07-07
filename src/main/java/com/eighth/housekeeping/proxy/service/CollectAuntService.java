package com.eighth.housekeeping.proxy.service;

import com.eighth.housekeeping.domain.CollectAunt;
import com.eighth.housekeeping.proxy.annotation.RemoteMethod;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

/**
 * Created by dam on 2014/7/6.
 */
public interface CollectAuntService {

    /**
     * 收藏阿姨
     * @param auntId
     * @param memberId
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "auntId","memberId" })
    CollectAunt addCollect(String auntId,String memberId)throws RemoteInvokeException;
}
