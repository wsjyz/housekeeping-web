package com.eighth.housekeeping.dao;

import java.util.List;

import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;

/**
 * Created by dam on 2014/7/28.
 */
public interface ReviewDAO {

    OpenPage<Review> findReviewByAuntId(String reviewTag,String auntId,OpenPage page);

    void saveReview(Review review);

    List<Review> getReviewByAuntId(String auntId);
    void deleteReview(String reviewId);
    OpenPage<Review> findReviewByAuntIdByWeb(String userName,String auntId,OpenPage page);

	OpenPage<Review> pageReviewByWeb(String userId, String auntId,
			OpenPage<Review> page);

}
