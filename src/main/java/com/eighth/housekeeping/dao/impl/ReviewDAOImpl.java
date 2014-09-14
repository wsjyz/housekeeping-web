package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.ReviewDAO;
import com.eighth.housekeeping.dao.UserDAO;
import com.eighth.housekeeping.domain.ImageObj;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dam on 2014/7/28.
 */
@Repository("ReviewDAO")
public class ReviewDAOImpl extends BaseDAO implements ReviewDAO {
	@Override
	public OpenPage<Review> findReviewByAuntId(String reviewTag, String auntId,
			OpenPage page) {
		StringBuilder reviewSql = new StringBuilder("");
		reviewSql
				.append("select tr.*,tmi.nick_name from t_review tr,t_member_info tmi"
						+ " where tr.create_user_id = tmi.user_id  and tr.aunt_id =? ");
		StringBuilder countSql = new StringBuilder(
				"select count(*) from t_review tr where aunt_id =?");
		List<Object> params = new ArrayList<Object>();
		List<Object> countParams = new ArrayList<Object>();
		params.add(auntId);
		countParams.add(auntId);
		if (!reviewTag.equals("ALL")) {
			reviewSql.append(" and tr.review_tag = ? ");
			countSql.append(" and tr.review_tag = ? ");
			params.add(reviewTag);
			countParams.add(reviewTag);
		}
        reviewSql.append(" order by tmi.opt_time desc");
		reviewSql.append(" limit ?,? ");
		params.add(page.getPageSize() * (page.getPageNo() - 1));
		params.add(page.getPageSize());

		List<Review> reviewList = getJdbcTemplate().query(reviewSql.toString(),
				params.toArray(), new ReviewMapper());

		Integer count = getJdbcTemplate().queryForObject(countSql.toString(),
				countParams.toArray(), Integer.class);
		OpenPage<Review> reviewOpenPage = new OpenPage<Review>();
		reviewOpenPage.setTotal(count);
		reviewOpenPage.setRows(reviewList);
		return reviewOpenPage;
	}

	@Override
	public void saveReview(final Review review) {
		StringBuilder sql = new StringBuilder("insert into t_review "
				+ "(review_id,review_tag,review_content,create_user_id,"
				+ "aunt_id,corp_id,opt_time)");
		sql.append("values(?,?,?,?,?,?,?)");
		getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, review.getReviewId());
				ps.setString(2, review.getReviewTag());
				ps.setString(3, review.getReviewContent());
				ps.setString(4, review.getCreateUserId());
				ps.setString(5, review.getAuntId());
				ps.setString(6, review.getCorpId());
				ps.setString(7, review.getOptTime());
			}
		});
	}

	public class ReviewMapper implements RowMapper<Review> {

		@Override
		public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
			Review review = new Review();
			review.setAuntId(rs.getString("aunt_id"));
			review.setCreateUserId(rs.getString("create_user_id"));
			review.setReviewContent(rs.getString("review_content"));
			review.setReviewId(rs.getString("review_id"));
			review.setReviewTag(rs.getString("review_tag"));
			review.setOptTime(rs.getString("opt_time"));
			review.setCreateUserName(rs.getString("nick_name"));
			return review;
		}
	}

	@Override
	public List<Review> getReviewByAuntId(String auntId) {
		StringBuilder reviewSql = new StringBuilder("");
		reviewSql.append("select * from t_review where aunt_id =?");
		List<Review> reviewList = getJdbcTemplate().query(reviewSql.toString(),
				new Object[] { auntId },new RowMapper<Review>(){
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
        });
		return reviewList;
	}

	@Override
	public void deleteReview(String reviewId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_review where review_id='" + reviewId + "'");
		getJdbcTemplate().update(sql.toString());
	}

	@Override
	public OpenPage<Review> findReviewByAuntIdByWeb(String userName,String auntId, OpenPage page) {
		  StringBuilder reviewSql = new StringBuilder("");
		  
	        reviewSql.append("select tr.*,mi.user_name as createUserName from t_review tr left join t_member_info mi on mi.user_id=tr.create_user_id where  tr.aunt_id =? ");
	        
	        StringBuilder countSql = new StringBuilder("select count(*) from t_review tr left join t_member_info mi on mi.user_id=tr.create_user_id  where aunt_id =?");
	        List<Object> params = new ArrayList<Object>();
	        List<Object> countParams = new ArrayList<Object>();
	        if(StringUtils.isNotEmpty(userName)){
	        	countSql.append(" and mi.user_name like '%"+userName+"%'");
	        	reviewSql.append(" and mi.user_name like '%"+userName+"%'");
	        }
	        reviewSql.append(" limit ?,? ");
	        params.add(auntId);
	        params.add(page.getPageSize() * (page.getPageNo()-1));
	        params.add(page.getPageSize());


	        List<Review> reviewList = getJdbcTemplate().query(reviewSql.toString(),params.toArray(),new RowMapper<Review>(){
                @Override
                public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Review review = new Review();
                    review.setAuntId(rs.getString("aunt_id"));
                    review.setCreateUserId(rs.getString("create_user_id"));
                    review.setReviewContent(rs.getString("review_content"));
                    review.setReviewId(rs.getString("review_id"));
                    review.setReviewTag(rs.getString("review_tag"));
                    review.setOptTime(rs.getString("opt_time"));
                    review.setCreateUserName(rs.getString("createUserName"));
                    return review;
                }
            });
	        countParams.add(auntId);
	        Integer count = getJdbcTemplate().queryForObject(countSql.toString(),countParams.toArray(),Integer.class);
	        page.setTotal(count);
	        page.setRows(reviewList);
	        return page;
	}

	@Override
	public OpenPage<Review> pageReviewByWeb(String userName, String auntName,
			OpenPage<Review> page) {
		  StringBuilder reviewSql = new StringBuilder("");
	        reviewSql.append("select tr.*,mi.user_name as createUserName,ai.user_name as auntName  from t_review tr " +
	                " left join t_member_info mi on tr.create_user_id = mi.user_id"
	                + "  left join t_aunt_info ai on tr.aunt_id = ai.aunt_id where  1=1");
	        
	        StringBuilder countSql = new StringBuilder("select count(*)  left join t_member_info mi on tr.create_user_id = mi.user_id"
	                + "  left join t_aunt_info ai on tr.aunt_id = ai.aunt_id  where 1=1 ");
	        List<Object> params = new ArrayList<Object>();
	        List<Object> countParams = new ArrayList<Object>();
	        if(StringUtils.isNotEmpty(userName)){
	        	reviewSql.append(" and mi.user_name like '%"+userName+"%'");
	        	countSql.append(" and mi.user_name like '%"+userName+"%'");
	        }
	        if(StringUtils.isNotEmpty(auntName)){
	        	reviewSql.append(" and ai.user_name like '%"+auntName+"%'");
	        	countSql.append(" and ai.user_name like '%"+auntName+"%'");
	        }
	        reviewSql.append(" limit ?,? ");
	        params.add(page.getPageSize() * (page.getPageNo()-1));
	        params.add(page.getPageSize());


	        List<Review> reviewList = getJdbcTemplate().query(reviewSql.toString(),params.toArray(),new RowMapper<Review>(){
              @Override
              public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
                  Review review = new Review();
                  review.setAuntId(rs.getString("aunt_id"));
                  review.setCreateUserId(rs.getString("create_user_id"));
                  review.setReviewContent(rs.getString("review_content"));
                  review.setReviewId(rs.getString("review_id"));
                  review.setReviewTag(rs.getString("review_tag"));
                  review.setOptTime(rs.getString("opt_time"));
                  review.setCreateUserName(rs.getString("createUserName"));
                  review.setAuntName(rs.getString("auntName"));
                  return review;
              }
          });
	        Integer count = getJdbcTemplate().queryForObject(countSql.toString(),countParams.toArray(),Integer.class);
	        page.setTotal(count);
	        page.setRows(reviewList);
	        return page;
	}
}
