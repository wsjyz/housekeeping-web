package com.eighth.housekeeping.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.UserDAO;
import com.eighth.housekeeping.domain.MemberInfo;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.VerifyCode;

/**
 * Created by dam on 2014/7/24.
 */
@Repository("UserDAO")
public class UserDAOImpl extends BaseDAO implements UserDAO {
    @Override
    public void saveVerifyCode(final VerifyCode code) {
        StringBuilder sql = new StringBuilder("");
        sql.append("insert into t_verify_code (mobile,token,opt_time)");
        sql.append("values(?,?,?)");
        getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,code.getMobile());
                ps.setString(2,code.getToken());
                ps.setString(3,code.getOptTime());
            }
        });
    }

    @Override
    public void deleteVerifyCode(String mobile) {
        StringBuilder sql = new StringBuilder("");
        sql.append("delete from t_verify_code where mobile = ?");
        getJdbcTemplate().update(sql.toString(),new String[]{mobile});
    }

    @Override
    public VerifyCode findVerifyCodeByMobile(String mobile) {
        StringBuilder sql = new StringBuilder("");
        sql.append("select * from t_verify_code where mobile = ?");
        List<VerifyCode> list = getJdbcTemplate().query(sql.toString(),new String[]{mobile}, new RowMapper<VerifyCode>() {
            @Override
            public VerifyCode mapRow(ResultSet rs, int rowNum) throws SQLException {
                VerifyCode code = new VerifyCode();
                code.setToken(rs.getString("token"));
                code.setOptTime(rs.getString("opt_time"));
                return code;
            }
        });
        if(list != null){
            return list.get(0);
        }
        return null;
    }



    @Override
    public void saveMember(final MemberInfo info) {
        StringBuilder sql = new StringBuilder("");
        sql.append("insert into t_member_info (user_id,mobile,user_name,nick_name," +
                "address,card,status,coupon_counts,coupon_end_time,push_aunt_info,corp_id,opt_time)");
        sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?)");
        getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,info.getUserId());
                ps.setString(2,info.getMobile());
                ps.setString(3,info.getUserName());
                ps.setString(4,info.getNickName());
                ps.setString(5,info.getAddress());
                ps.setString(6,info.getCard());
                ps.setString(7,"ACTIVE");
                ps.setString(8,info.getCouponCounts());
                ps.setString(9,info.getCouponEndTime());
                ps.setInt(10,info.getPushAuntInfo());
                ps.setString(11,info.getCorpId());
                ps.setString(12,info.getOptTime());
            }
        });
    }

    @Override
    public String modifyMemberInfo(MemberInfo userInfo) {
        StringBuilder sql = new StringBuilder("update t_member_info set ");
        if(StringUtils.isNotBlank(userInfo.getUserName())){
            sql.append("user_name='"+userInfo.getUserName()+"',");
        }
        if(StringUtils.isNotBlank(userInfo.getAddress())){
            sql.append("address='"+userInfo.getAddress()+"',");
        }
        if(StringUtils.isNotBlank(userInfo.getCard())){
            sql.append("card='"+userInfo.getCard()+"',");
        }
        if(StringUtils.isNotBlank(userInfo.getNickName())){
            sql.append("nick_name='"+userInfo.getNickName()+"',");
        }
        if(StringUtils.isNotBlank(userInfo.getStatus())){
            sql.append("status='"+userInfo.getStatus()+"',");
        }
        if(userInfo.getPushAuntInfo() != 0){
            sql.append("push_aunt_info='"+userInfo.getPushAuntInfo()+"',");
        }
        if(StringUtils.isNotEmpty(userInfo.getCouponCounts())){
            sql.append("coupon_counts='"+userInfo.getCouponCounts()+"',");

        }
        if(StringUtils.isNotEmpty(userInfo.getCorpId())){
            sql.append("corp_id='"+userInfo.getCorpId()+"',");
        }
        if(StringUtils.isNotEmpty(userInfo.getCouponEndTime())){
            sql.append("coupon_end_time='"+userInfo.getCouponEndTime()+"',");

        }
        if(sql.lastIndexOf(",") + 1 == sql.length()){
            sql.delete(sql.lastIndexOf(","),sql.length());
        }
        sql.append(" where user_id='"+userInfo.getUserId()+"'");
        getJdbcTemplate().update(sql.toString());
        return "SUCCESS";
    }

    @Override
    public MemberInfo findMemberByMemberId(String memberId) {
        StringBuilder sql = new StringBuilder("");
        sql.append("select * from t_member_info where user_id = ?");
        List<MemberInfo> list = getJdbcTemplate().query(sql.toString(),
                new String[]{memberId},new MemberInfoRowMapper());
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public MemberInfo findMemberByMobile(String mobile) {
        StringBuilder sql = new StringBuilder("");
        sql.append("select * from t_member_info where mobile = ?");
        List<MemberInfo> list = getJdbcTemplate().query(sql.toString(),
                new String[]{mobile},new MemberInfoRowMapper());
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public class MemberInfoRowMapper implements RowMapper<MemberInfo>{

        @Override
        public MemberInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            MemberInfo info = new MemberInfo();
            info.setNickName(rs.getString("nick_name"));
            info.setOptTime(rs.getString("opt_time"));
            info.setAddress(rs.getString("address"));
            info.setCard(rs.getString("card"));
            info.setCouponCounts(rs.getString("coupon_counts"));
            info.setCouponEndTime(rs.getString("coupon_end_time"));
            info.setMobile(rs.getString("mobile"));
            info.setPushAuntInfo(rs.getInt("push_aunt_info"));
            info.setStatus(rs.getString("status"));
            info.setUserId(rs.getString("user_id"));
            info.setUserName(rs.getString("user_name"));
            info.setCorpId(rs.getString("corp_id"));
            return info;
        }
    }

	@Override
	public String deleteByMemberId(String memberId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_member_info where user_id='"
				+ memberId + "'");
		int update = getJdbcTemplate().update(sql.toString());
		if (update > 0) {
			return "SUCCESS";
		} else {
			return "FAIL";
		}
	}

	@Override
	public OpenPage<MemberInfo> findUserPage(String mobile, String nickName,
			OpenPage page) {
		   StringBuilder userSql = new StringBuilder("");
		   userSql.append("select * from t_member_info where 1=1");
		   if (StringUtils.isNotEmpty(mobile)) {
			   userSql.append(" and mobile like '%"+mobile+"%' ");
		   }
		   if (StringUtils.isNotEmpty(nickName)) {
			   userSql.append("  and nick_name  like '%"+nickName+"%' ");
		   }
		   userSql.append(" and status='ACTIVE'");
		   userSql.append(" limit ?,?");
		   List<MemberInfo> reviewList = getJdbcTemplate().query(userSql.toString(),
	                new Object[]{page.getPageSize() * (page.getPageNo()-1),page.getPageSize()},new MemberInfoRowMapper());

		   StringBuilder countSql = new StringBuilder("");
		   countSql.append("select count(*) from t_member_info where 1=1");
		   if (StringUtils.isNotEmpty(mobile)) {
			   countSql.append(" and mobile like '%"+mobile+"%' ");
		   }
		   if (StringUtils.isNotEmpty(nickName)) {
			   countSql.append("  and nick_name  like '%"+nickName+"%' ");
		   }
		   countSql.append(" and status='ACTIVE'");
	       Integer count = getJdbcTemplate().queryForObject(countSql.toString(),Integer.class);
	       page.setTotal(count);
	       page.setRows(reviewList);
	       return page;
	}
}
