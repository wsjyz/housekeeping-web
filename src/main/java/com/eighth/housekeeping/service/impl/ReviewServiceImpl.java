package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.ReviewService;
import org.springframework.stereotype.Service;

/**
 * Created by dam on 2014/7/24.
 */
@Service("ReviewService")
public class ReviewServiceImpl implements ReviewService {
    @Override
    public Review addReview(Review review) throws RemoteInvokeException {
        return null;
    }
}
