package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.CollectAuntDAO;
import com.eighth.housekeeping.domain.CollectAunt;
import com.eighth.housekeeping.domain.OpenPage;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dam on 2014/7/28.
 */
@Repository("CollectAuntDAO")
public class CollectAuntDAOImp extends BaseDAO implements CollectAuntDAO {
    @Override
    public void addCollect(final CollectAunt collectAunt) {
        StringBuilder sql = new StringBuilder("");
        sql.append("insert into t_collect_aunt (collect_id,member_id,aunt_id,corp_id," +
                "opt_time)");
        sql.append("values(?,?,?,?,?)");
        getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,collectAunt.getCollectId());
                ps.setString(2,collectAunt.getMemberId());
                ps.setString(3,collectAunt.getAuntId());
                ps.setString(4,collectAunt.getCorpId());
                ps.setString(5,collectAunt.getOptTime());
            }
        });
    }

    @Override
    public OpenPage<CollectAunt> findCollectAuntList(String userId,OpenPage<CollectAunt> page) {
        StringBuilder collectSql = new StringBuilder("");
        collectSql.append("select * from t_collect_aunt where member_id = ? limit ?,?");
        System.out.println(page == null);
        List<CollectAunt> reviewList = getJdbcTemplate().query(collectSql.toString(),
                new Object[]{userId, page.getPageSize() * (page.getPageNo() - 1), page.getPageSize()}, new CollectAuntMapper());

        StringBuilder countSql = new StringBuilder("select count(*) from t_collect_aunt where member_id = ?");
        Integer count = getJdbcTemplate().queryForObject(countSql.toString(),new String[]{userId},Integer.class);
        OpenPage<CollectAunt> collectAuntOpenPage = new OpenPage<CollectAunt>();
        collectAuntOpenPage.setTotal(count);
        collectAuntOpenPage.setRows(reviewList);
        return collectAuntOpenPage;
    }

    @Override
    public CollectAunt findCollectAuntByMemberIdAndAuntId(String memberId, String auntId) {
        StringBuilder collectSql = new StringBuilder("");
        collectSql.append("select * from t_collect_aunt where member_id = ? and aunt_id=? ");
        List<CollectAunt> collectAuntListList = getJdbcTemplate().query(collectSql.toString(),
                new Object[]{memberId, auntId}, new CollectAuntMapper());
        if(!CollectionUtils.isEmpty(collectAuntListList)){
            return collectAuntListList.get(0);
        }
        return null;
    }

    public class CollectAuntMapper implements RowMapper<CollectAunt>{

        @Override
        public CollectAunt mapRow(ResultSet rs, int rowNum) throws SQLException {
            CollectAunt collectAunt = new CollectAunt();
            collectAunt.setOptTime(rs.getString("opt_time"));
            collectAunt.setAuntId(rs.getString("aunt_id"));
            collectAunt.setCollectId(rs.getString("collect_id"));
            collectAunt.setMemberId(rs.getString("member_id"));
            collectAunt.setCorpId(rs.getString("corp_id"));
            return collectAunt;
        }
    }

	@Override
	public void deleteCollectAunt(String collectId) {
		StringBuilder collectSql = new StringBuilder("");
        collectSql.append("select * from t_collect_aunt where collect_id='"+collectId+"'");
        getJdbcTemplate().update(collectSql.toString());
	}
}
