package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.ReviewDAO;
import com.eighth.housekeeping.domain.ImageObj;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dam on 2014/7/28.
 */
@Repository("ReviewDAO")
public class ReviewDAOImpl extends BaseDAO implements ReviewDAO {
    @Override
    public OpenPage<Review> findReviewByAuntId(String reviewTag, String auntId, OpenPage page) {
        StringBuilder reviewSql = new StringBuilder("");
        reviewSql.append("select * from t_review where review_tag = ? and aunt_id =? limit ?,?");
        List<Review> reviewList = getJdbcTemplate().query(reviewSql.toString(),
                new Object[]{reviewTag,auntId,page.getPageSize() * (page.getPageNo()-1),page.getPageSize()},new ReviewMapper());

        StringBuilder countSql = new StringBuilder("select count(*) from t_review where review_tag = ? and aunt_id =?");
        Integer count = getJdbcTemplate().queryForObject(countSql.toString(),new String[]{reviewTag,auntId},Integer.class);
        OpenPage<Review> reviewOpenPage = new OpenPage<Review>();
        reviewOpenPage.setTotal(count);
        reviewOpenPage.setRows(reviewList);
        return reviewOpenPage;
    }

    @Override
    public void saveReview(final Review review) {
        StringBuilder sql = new StringBuilder("insert into t_review " +
                "(review_id,review_tag,review_content,create_user_id," +
                "aunt_id,corp_id,opt_time)");
        sql.append("values(?,?,?,?,?,?,?)");
        getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,review.getReviewId());
                ps.setString(2,review.getReviewTag());
                ps.setString(3,review.getReviewContent());
                ps.setString(4,review.getCreateUserId());
                ps.setString(5,review.getAuntId());
                ps.setString(6,review.getCorpId());
                ps.setString(7,review.getOptTime());
            }
        });
    }

    public class ReviewMapper implements RowMapper<Review>{

        @Override
        public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
            Review review = new Review();
            review.setAuntId(rs.getString("aunt_id"));
            review.setCreateUserId(rs.getString("create_user_id"));
            review.setReviewContent(rs.getString("review_content"));
            review.setReviewId(rs.getString("review_id"));
            review.setReviewTag(rs.getString("review_tag"));
            review.setOptTime(rs.getString("opt_time"));
            return review;
        }
    }
}
