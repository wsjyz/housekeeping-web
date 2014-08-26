package com.eighth.housekeeping.proxy.service;

import java.util.List;

import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.proxy.annotation.RemoteMethod;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

/**
 * 评论服务
 * Created by dam on 2014/7/4.
 */
public interface ReviewService {

    /**
     * 添加评论
     * @param review
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "review" })
    Review addReview(Review review)throws RemoteInvokeException;
    List<Review> getReviewByAuntId(String auntId);
    
    OpenPage<Review> findReviewByAuntIdByWeb(String userName,String auntId,OpenPage page);
    void deleteReview(String reviewId);
}
