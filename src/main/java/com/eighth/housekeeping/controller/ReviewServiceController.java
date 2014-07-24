package com.eighth.housekeeping.controller;

import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.ReviewService;
import com.eighth.housekeeping.web.FastJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ReviewService")
public class ReviewServiceController {
    @Autowired
    ReviewService reviewService;

    @ResponseBody
    @RequestMapping(value = "/addReview")
    public Review addReview(@FastJson Review review) {
        try {
            Review rev = reviewService.addReview(review);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return review;
    }
}
