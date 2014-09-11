package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.dao.CollectAuntDAO;
import com.eighth.housekeeping.domain.CollectAunt;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.CollectAuntService;
import com.eighth.housekeeping.utils.CommonStringUtils;
import com.eighth.housekeeping.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dam on 2014/7/24.
 */
@Service("CollectAuntService")
public class CollectAuntServiceImpl implements CollectAuntService {

    @Autowired
    private CollectAuntDAO collectAuntDAO;
    @Override
    public CollectAunt addCollect(String auntId, String memberId) throws RemoteInvokeException {
        CollectAunt collectAunt = collectAuntDAO.findCollectAuntByMemberIdAndAuntId(memberId,auntId);
        if(collectAunt == null){
            collectAunt = new CollectAunt();
            collectAunt.setAuntId(auntId);
            collectAunt.setMemberId(memberId);
            collectAunt.setCollectId(CommonStringUtils.genPK());
            collectAuntDAO.addCollect(collectAunt);
            collectAunt.setCollectResult(Constants.SUCCESS);
        }else{
            collectAunt.setCollectResult(Constants.COLLECTED);
        }
        return collectAunt;
    }

    @Override
    public OpenPage<CollectAunt> findCollectAuntList(String userId,OpenPage<CollectAunt> page) throws RemoteInvokeException {
        return collectAuntDAO.findCollectAuntList(userId,page);
    }


}
