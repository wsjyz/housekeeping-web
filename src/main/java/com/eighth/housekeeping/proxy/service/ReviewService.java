package com.eighth.housekeeping.proxy.service;

import com.eighth.housekeeping.domain.Review;
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
    Review addReview(Review review)throws RemoteInvokeException;
}
