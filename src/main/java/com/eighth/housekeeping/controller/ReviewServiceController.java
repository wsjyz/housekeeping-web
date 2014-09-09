package com.eighth.housekeeping.controller;

import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.ReviewService;
import com.eighth.housekeeping.web.FastJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @ResponseBody
    @RequestMapping(value = "/searchReviewByWeb")
    public OpenPage<Review> searchReviewByWeb(@RequestParam String userName,@RequestParam String  auntId, @FastJson OpenPage<Review> page){
        try {
        	page = reviewService.findReviewByAuntIdByWeb(userName,auntId, page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }
	@ResponseBody
	@RequestMapping(value = "/deleteReviewWeb")
	public void deleteReviewWeb(@RequestParam  String reviewId)  throws RemoteInvokeException{
		reviewService.deleteReview(reviewId);
	}
	
    @ResponseBody
    @RequestMapping(value = "/pageReviewByWeb")
    public OpenPage<Review> pageReviewByWeb(@RequestParam String userName,@RequestParam String  auntName, @FastJson OpenPage<Review> page){
        try {
        	page = reviewService.pageReviewByWeb(userName,auntName, page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }
}
