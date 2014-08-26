package com.eighth.housekeeping.service.impl;

import java.util.List;

import com.eighth.housekeeping.dao.AuntDAO;
import com.eighth.housekeeping.dao.OrderDAO;
import com.eighth.housekeeping.dao.ReviewDAO;
import com.eighth.housekeeping.dao.UserDAO;
import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.MemberInfo;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.ReviewService;
import com.eighth.housekeeping.proxy.service.UserService;
import com.eighth.housekeeping.utils.CommonStringUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Created by dam on 2014/7/24.
 */
@Service("ReviewService")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDAO reviewDAO;
    @Autowired
    UserDAO userDao;
    
    @Autowired
    OrderDAO orderDAO;
    @Override
    public Review addReview(Review review) throws RemoteInvokeException {
        review.setReviewId(CommonStringUtils.genPK());
        reviewDAO.saveReview(review);
        return review;
    }

	@Override
	public List<Review> getReviewByAuntId(String auntId) {
		return reviewDAO.getReviewByAuntId(auntId);
	}

	@Override
	public OpenPage<Review> findReviewByAuntIdByWeb(String userName,String auntId,
			OpenPage page) {
		OpenPage<Review> rePage= reviewDAO.findReviewByAuntIdByWeb(userName,auntId, page);
		if(!CollectionUtils.isEmpty(rePage.getRows())){
			for (Review review : rePage.getRows()) {
				if(StringUtils.isNotEmpty(review.getCreateUserId())){
					MemberInfo memberInfo = userDao.findMemberByMemberId(review.getCreateUserId());
					review.setCreateUserName(memberInfo.getUserName());
				}
			}
		}
		return rePage;
	}

	@Override
	public void deleteReview(String reviewId) {
		reviewDAO.deleteReview(reviewId);
	}
}
