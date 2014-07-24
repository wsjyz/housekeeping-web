package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.domain.CollectAunt;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.CollectAuntService;
import org.springframework.stereotype.Service;

/**
 * Created by dam on 2014/7/24.
 */
@Service("CollectAuntService")
public class CollectAuntServiceImpl implements CollectAuntService {
    @Override
    public CollectAunt addCollect(String auntId, String memberId) throws RemoteInvokeException {
        return null;
    }

    @Override
    public OpenPage<CollectAunt> findCollectAuntList(String userId) throws RemoteInvokeException {
        return null;
    }
}
