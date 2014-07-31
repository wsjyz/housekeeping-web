package com.eighth.housekeeping.dao;

import com.eighth.housekeeping.domain.CollectAunt;
import com.eighth.housekeeping.domain.OpenPage;

/**
 * Created by dam on 2014/7/28.
 */
public interface CollectAuntDAO {

    void addCollect(CollectAunt collectAunt);

    OpenPage<CollectAunt> findCollectAuntList(String userId,OpenPage page);

    CollectAunt findCollectAuntByMemberIdAndAuntId(String memberId,String auntId);
}
