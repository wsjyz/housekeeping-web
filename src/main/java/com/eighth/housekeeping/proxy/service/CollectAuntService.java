package com.eighth.housekeeping.proxy.service;

import com.eighth.housekeeping.domain.CollectAunt;
import com.eighth.housekeeping.domain.OpenPage;
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
     * @return CollectAunt.collectResult SUCCESS收藏成功|COLLECTED已经收藏
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "auntId","memberId" })
    CollectAunt addCollect(String auntId,String memberId)throws RemoteInvokeException;

    /**
     * 我收藏的阿姨
     * @param userId
     * @return 分页列表
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "userId","page"})
    OpenPage<CollectAunt> findCollectAuntList(String userId,OpenPage<CollectAunt> page)throws RemoteInvokeException;

    /**
     * 删除收藏的阿姨
     * @param collectId
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "collectId"})
	void deleteCollectAunt(String collectId)throws RemoteInvokeException;


}
