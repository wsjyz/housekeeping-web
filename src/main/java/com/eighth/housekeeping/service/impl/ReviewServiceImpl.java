package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.dao.ReviewDAO;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.ReviewService;
import com.eighth.housekeeping.utils.CommonStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dam on 2014/7/24.
 */
@Service("ReviewService")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;

    @Override
    public Review addReview(Review review) throws RemoteInvokeException {
        review.setReviewId(CommonStringUtils.genPK());
        reviewDAO.saveReview(review);
        return review;
    }
}
