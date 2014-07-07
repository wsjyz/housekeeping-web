package com.eighth.housekeeping.proxy.service;

import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.AuntWorkCase;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.proxy.annotation.RemoteMethod;

import java.util.List;

/**
 * Created by dam on 2014/7/4.
 */
public interface AuntService {

    /**
     * 阿姨详情 需要加载案例
     * @param auntId
     * @return
     */
    @RemoteMethod(methodVarNames={ "auntId" })
    AuntInfo findAuntById(String auntId);

    /**
     * 工作效果详情
     * @param caseId
     * @return
     */
    @RemoteMethod(methodVarNames={ "caseId" })
    AuntWorkCase findCaseById(String caseId);

    /**
     * 阿姨评论列表
     * @param reviewTag 见Review
     * @param auntId
     * @return
     */
    @RemoteMethod(methodVarNames={ "reviewTag","auntId","page" })
    List<Review> findReviewByAuntId(String reviewTag,String auntId,OpenPage page);


}
