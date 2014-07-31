package com.eighth.housekeeping.dao;

import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;

/**
 * Created by dam on 2014/7/28.
 */
public interface ReviewDAO {

    OpenPage<Review> findReviewByAuntId(String reviewTag,String auntId,OpenPage page);

    void saveReview(Review review);


}
