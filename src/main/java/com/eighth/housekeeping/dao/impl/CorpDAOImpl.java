package com.eighth.housekeeping.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.CorpDAO;
import com.eighth.housekeeping.domain.Corp;
import com.eighth.housekeeping.domain.OpenPage;

/**
 * Created by dam on 2014/7/28.
 */
@Repository("CorpDAO")
public class CorpDAOImpl extends BaseDAO implements CorpDAO {

	@Override
	public OpenPage<Corp> findCorpPage(String corpName, String loginName,
			OpenPage page) {
		   StringBuilder reviewSql = new StringBuilder("");
		   reviewSql.append("select * from t_corp where");
		   if (StringUtils.isNotEmpty(corpName)) {
			   reviewSql.append("corp_name like '%"+corpName+"%' ");
		   }
		   if (StringUtils.isNotEmpty(loginName)) {
			   reviewSql.append("  and login_name  like '%"+corpName+"%' ");
		   }
		   reviewSql.append(" limit ?,?");
		   List<Corp> reviewList = getJdbcTemplate().query(reviewSql.toString(),
	                new Object[]{page.getPageSize() * (page.getPageNo()-1),page.getPageSize()},new CorpMapper());

		   StringBuilder countSql = new StringBuilder("");
		   countSql.append("select * from t_corp where");
		   if (StringUtils.isNotEmpty(corpName)) {
			   countSql.append("corp_name like '%"+corpName+"%' ");
		   }
		   if (StringUtils.isNotEmpty(loginName)) {
			   countSql.append("  and login_name  like '%"+corpName+"%' ");
		   }
	        Integer count = getJdbcTemplate().queryForObject(countSql.toString(),Integer.class);
	        OpenPage<Corp> reviewOpenPage = new OpenPage<Corp>();
	        reviewOpenPage.setTotal(count);
	        reviewOpenPage.setRows(reviewList);
	        return reviewOpenPage;
	}

	@Override
	public void saveCorp(final Corp corp) {
		   StringBuilder sql = new StringBuilder("insert into t_corp " +
	                "(corp_id,corp_simple_name,corp_name,login_name," +
	                "password,description,status,opt_time)");
	        sql.append("values(?,?,?,?,?,?,?,?)");
	        getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps) throws SQLException {
	                ps.setString(1,corp.getCorpId());
	                ps.setString(2,corp.getCorpSimpleName());
	                ps.setString(3,corp.getCorpName());
	                ps.setString(4,corp.getLoginName());
	                ps.setString(5,corp.getPassword());
	                ps.setString(6,corp.getDescription());
	                ps.setString(7,corp.getStatus());
	                ps.setString(8,corp.getOptTime());
	            }
	        });
	}

	@Override
	public Corp findCorpId(String corpId) {
		  StringBuilder sql = new StringBuilder("");
	        sql.append("select * from t_corp where corp_id = ?");
	        List<Corp> signInfoList = getJdbcTemplate().query(sql.toString(),new String[]{corpId},new CorpMapper());
	        if(!CollectionUtils.isEmpty(signInfoList)){
	        	Corp corp = signInfoList.get(0);
	            return corp;
	        }
	        return null;
	}

	@Override
	public String updateCorp(Corp corp) {
		StringBuffer sql=new StringBuffer();
		sql.append("update t_corp set corp_name='"+corp.getCorpName()+"',login_name='"+corp.getLoginName()+"',password='"+corp.getPassword()
				+"',status='"+corp.getStatus()+"',corp_simple_name='"+corp.getCorpSimpleName()+"',description='"+corp.getDescription()
				+"',opt_time='"+corp.getOptTime()+"' where corp_id='"+corp.getCorpId()+"'");
		int count=getJdbcTemplate().update(sql.toString());
		String status="FAIL";
		if (count>0) {
			status="SUCCESS";
		}
		return status;
	}

	@Override
	public String deleteCorp(String corpId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_corp where corp_id='"
				+ corpId + "'");
		int update = getJdbcTemplate().update(sql.toString());
		if (update > 0) {
			return "SUCCESS";
		} else {
			return "FAIL";
		}
	}

	public class CorpMapper implements RowMapper<Corp> {

		@Override
		public Corp mapRow(ResultSet rs, int rowNum) throws SQLException {
			Corp corp = new Corp();
			corp.setCorpId(rs.getString("corp_id"));
			corp.setCorpSimpleName(rs.getString("corp_simple_name"));// 机构简称
			corp.setCorpName(rs.getString("corp_name"));// 全称
			corp.setLoginName(rs.getString("login_name"));// 登录名
			corp.setPassword(rs.getString("password"));// 密码
			corp.setDescription(rs.getString("description"));// 备注
			corp.setStatus(rs.getString("status"));// 状态 ACTIVE激活 NOT_ACTIVE禁用
													// DELETE删除
			return corp;
		}
	}

}
