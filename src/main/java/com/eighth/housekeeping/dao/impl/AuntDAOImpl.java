package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.AuntDAO;
import com.eighth.housekeeping.dao.AuntWorkCaseDAO;
import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.impl.CorpDAOImpl.CorpMapper;
import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.Corp;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.utils.CommonStringUtils;
import com.eighth.housekeeping.utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dam on 2014/7/28.
 */
@Repository("AuntDAO")
public class AuntDAOImpl extends BaseDAO implements AuntDAO {

	@Autowired
	private AuntWorkCaseDAO auntWorkCaseDAO;

	@Override
	public AuntInfo findAuntByIdForAunt(String auntId) {
		StringBuilder sql = new StringBuilder("");
		sql.append("select * from t_aunt_info where aunt_id = ?");
		List<AuntInfo> auntInfoList = getJdbcTemplate().query(sql.toString(),
				new String[] { auntId }, new AuntRowMapper());
		if (!CollectionUtils.isEmpty(auntInfoList)) {
			AuntInfo auntInfo = auntInfoList.get(0);
			return auntInfo;
		}
		return null;
	}

	@Override
	public AuntInfo findAuntByIdForMember(String auntId) {
		StringBuilder sql = new StringBuilder("");
		sql.append("select * from t_aunt_info where aunt_id = ?");
		List<AuntInfo> auntInfoList = getJdbcTemplate().query(sql.toString(),
				new String[] { auntId }, new AuntRowMapper());
		if (!CollectionUtils.isEmpty(auntInfoList)) {
			AuntInfo auntInfo = auntInfoList.get(0);
			auntInfo.setCaseList(auntWorkCaseDAO.findCaseByAuntId(auntId));
			return auntInfo;
		}
		return null;
	}

	@Override
	public AuntInfo findAuntByMobileAndPsw(String mobile, String password) {
		StringBuilder sql = new StringBuilder("");
		sql.append("select * from t_aunt_info where mobile = ? and password = ?");
		List<AuntInfo> auntInfoList = getJdbcTemplate().query(sql.toString(),
				new String[] { mobile, password }, new AuntRowMapper());
		AuntInfo auntInfo = null;
		if (!CollectionUtils.isEmpty(auntInfoList)) {
			auntInfo = auntInfoList.get(0);
			auntInfo.setLoginResult("SUCCESS");
		} else {
			auntInfo = new AuntInfo();
			auntInfo.setLoginResult("ACOUNT_NOT_CORRECT");
		}
		return auntInfo;
	}
	   @Override
	    public OpenPage<AuntInfo> searchAuntByCondition(AuntInfo auntInfo, OpenPage<AuntInfo> page) {
	        StringBuilder auntSql = new StringBuilder("select tai.aunt_id,tai.user_name,tai.start,tai.identity_card,tai.integrity_auth," +
	                "fdistance(?,?,tai.longitude,tai.latitude) distance" +
	                " from t_aunt_info tai ");
	        StringBuilder countSql = new StringBuilder("select count(*) "+
	                " from t_aunt_info tai ");

	        List<Object> params = new ArrayList<Object>();
	        List<Object> countParams = new ArrayList<Object>();
	        params.add(auntInfo.getLongitude());
	        params.add(auntInfo.getLatitude());
	        StringBuilder auntAppendSql = new StringBuilder("");
	        StringBuilder countAppendSql = new StringBuilder("");
	        if(StringUtils.isNotBlank(auntInfo.getStart())){
	            auntAppendSql.append(" tai.start = ?");
	            countAppendSql.append(" start = ?");
	            params.add(auntInfo.getStart());
	            countParams.add(auntInfo.getStart());
	        }
	        if(StringUtils.isNotBlank(auntInfo.getAge()) ){
	            if(!auntAppendSql.toString().equals("")){
	                auntAppendSql.append(" and ");
	            }
	            if(!countAppendSql.toString().equals("")){
	                countAppendSql.append(" and ");
	            }
	            auntAppendSql.append(" age = ?");
	            countAppendSql.append(" age = ?");
	            params.add(auntInfo.getAge());
	            countParams.add(auntInfo.getAge());
	        }
	        if(auntInfo.getWorkYear() != 0){
	            if(!auntAppendSql.toString().equals("")){
	                auntAppendSql.append(" and ");
	            }
	            if(!countAppendSql.toString().equals("")){
	                countAppendSql.append(" and ");
	            }
	            auntAppendSql.append(" work_year = ?");
	            countAppendSql.append(" where work_year = ?");
	            params.add(auntInfo.getWorkYear());
	            countParams.add(auntInfo.getWorkYear());
	        }
	        if(StringUtils.isNotBlank(auntInfo.getConstellation())){
	            if(!auntAppendSql.toString().equals("")){
	                auntAppendSql.append(" and ");
	            }
	            if(!countAppendSql.toString().equals("")){
	                countAppendSql.append(" and ");
	            }
	            auntAppendSql.append(" constellation = ?");
	            countAppendSql.append(" constellation = ?");
	            params.add(auntInfo.getConstellation());
	            countParams.add(auntInfo.getConstellation());
	        }
	        if(StringUtils.isNotBlank(auntInfo.getBloodType())){
	            if(!auntAppendSql.toString().equals("")){
	                auntAppendSql.append(" and ");
	            }
	            if(!countAppendSql.toString().equals("")){
	                countAppendSql.append(" and ");
	            }
	            auntAppendSql.append(" blood_type = ?");
	            countAppendSql.append(" blood_type = ?");
	            params.add(auntInfo.getBloodType());
	            countParams.add(auntInfo.getBloodType());
	        }
	        if(StringUtils.isNotBlank(auntInfo.getDescription())){//关键字
	            if(!auntAppendSql.toString().equals("")){
	                auntAppendSql.append(" and ");
	            }
	            if(!countAppendSql.toString().equals("")){
	                countAppendSql.append(" and ");
	            }
	            auntAppendSql.append(" description like ?");
	            countAppendSql.append(" description like ?");
	            params.add("%"+auntInfo.getDescription()+"%");
	            countParams.add("%"+auntInfo.getDescription()+"%");
	        }
	        if(StringUtils.isNotBlank(auntInfo.getSelfEvaluate())){//性格,爱好
	            if(!auntAppendSql.toString().equals("")){
	                auntAppendSql.append(" and ");
	            }
	            if(!countAppendSql.toString().equals("")){
	                countAppendSql.append(" and ");
	            }
	            auntAppendSql.append(" self_evaluate like ?");
	            countAppendSql.append(" self_evaluate like ?");
	            params.add("%"+auntInfo.getSelfEvaluate()+"%");
	            countParams.add("%"+auntInfo.getSelfEvaluate()+"%");
	        }
	        if(!auntAppendSql.toString().equals("")){
	            auntSql.append(" where ").append(auntAppendSql);
	        }
	        if(!countAppendSql.toString().equals("")){
	            countSql.append(" where ").append(countAppendSql);
	        }
	        auntSql.append(" order by fdistance(?,?,tai.longitude,tai.latitude) limit ?,?");
	        Integer count = getJdbcTemplate().queryForObject(countSql.toString(),countParams.toArray(),Integer.class);



	        params.add(auntInfo.getLongitude());
	        params.add(auntInfo.getLatitude());
	        params.add(page.getPageSize() * (page.getPageNo() - 1));
	        params.add(page.getPageSize());
	        List<AuntInfo> auntList = getJdbcTemplate().query(auntSql.toString(),
	                params.toArray(),new RowMapper<AuntInfo>() {
	                    @Override
	                    public AuntInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
	                        AuntInfo aunt = new AuntInfo();
	                        aunt.setAuntId(rs.getString("aunt_id"));
	                        aunt.setUserName(rs.getString("user_name"));
	                        aunt.setStart(rs.getString("start"));
	                        aunt.setIdentityCard(rs.getString("identity_card"));
	                        aunt.setIntegrityAuth(rs.getBoolean("integrity_auth"));
	                        aunt.setDistanceMeter(new Double(rs.getDouble("distance") *1000).intValue());
	                        return aunt;
	                    }
	                });

	        OpenPage<AuntInfo> reviewOpenPage = new OpenPage<AuntInfo>();
	        reviewOpenPage.setTotal(count);
	        reviewOpenPage.setRows(auntList);
	        return reviewOpenPage;
	    }

	    @Override
	    public void modifyAuntGeo(final String auntId, final double longitude, final double latitude) {
	        StringBuilder sql = new StringBuilder("update t_aunt_info set longitude=?,latitude=? where aunt_id=?");
	        getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps) throws SQLException {
	                ps.setDouble(1,longitude);
	                ps.setDouble(2,latitude);
	                ps.setString(3,auntId);
	            }
	        });
	    }
	public class AuntRowMapper implements RowMapper<AuntInfo> {

		@Override
		public AuntInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			AuntInfo auntInfo = new AuntInfo();
			auntInfo.setUserName(rs.getString("user_name"));
			auntInfo.setCorpId(rs.getString("corp_id"));
			auntInfo.setAbilityScore(rs.getInt("ability_score"));
			auntInfo.setAddress(rs.getString("address"));
			auntInfo.setTelephone(rs.getString("telephone"));
			auntInfo.setPassword(rs.getString("password"));
			auntInfo.setAge(rs.getString("age"));
			auntInfo.setAuntId(rs.getString("aunt_id"));
			auntInfo.setBloodType(rs.getString("blood_type"));
			auntInfo.setBusiDesc(rs.getString("busi_desc"));
			auntInfo.setConstellation(rs.getString("constellation"));
			auntInfo.setDescription(rs.getString("description"));
			auntInfo.setIdentityCard(rs.getString("identity_card"));
			auntInfo.setIntegrityAuth(rs.getBoolean("integrity_auth"));
			auntInfo.setNativePlace(rs.getString("native_place"));
			auntInfo.setMobile(rs.getString("mobile"));
			auntInfo.setPrice(rs.getBigDecimal("price"));
			auntInfo.setSelfEvaluate(rs.getString("self_evaluate"));
			auntInfo.setSex(rs.getString("sex"));
			auntInfo.setWorkYear(rs.getInt("work_year"));
			auntInfo.setStart(rs.getString("start"));
			auntInfo.setYearOfIncome(rs.getBigDecimal("year_of_income"));
			auntInfo.setMonthOfOrderCounts(rs.getInt("month_of_order_counts"));
			auntInfo.setTotalOrderCounts(rs.getInt("total_order_counts"));
			auntInfo.setMonthOfSignCounts(rs.getInt("month_of_sign_counts"));
			auntInfo.setMothOfIncome(rs.getBigDecimal("moth_of_income"));
			auntInfo.setVerySatisfyCounts(rs.getInt("very_satisfy_counts"));
			auntInfo.setSatisfyCounts(rs.getInt("satisfy_counts"));
			auntInfo.setNotSatisfyCounts(rs.getInt("not_satisfy_counts"));
			auntInfo.setStatus(rs.getString("status"));
			auntInfo.setWorkType(rs.getString("work_type"));
			auntInfo.setWorkCleanKeeping(rs.getBoolean("work_clean_keeping"));
			auntInfo.setWorkLaundry(rs.getBoolean("work_laundry"));
			auntInfo.setWorkCook(rs.getBoolean("work_cook"));
            auntInfo.setBrowseCounts(rs.getInt("browse_counts"));
			return auntInfo;
		}
	}

	@Override
	public String addAuntInfo(final AuntInfo auntInfo) {
		final String auntId= auntInfo.getAuntId();
		StringBuilder sql = new StringBuilder(
				"INSERT INTO t_aunt_info (aunt_id, password, mobile, user_name, identity_card, sex, age, address, "
						+ "price, telephone, work_year, ability_score, integrity_auth, description, native_place, busi_desc, blood_type, self_evaluate, "
						+ "constellation, work_type, start, year_of_income, month_of_order_counts, total_order_counts, moth_of_income, very_satisfy_counts, "
						+ "satisfy_counts, not_satisfy_counts, month_of_sign_counts, corp_id, opt_time,status,work_clean_keeping,work_laundry,work_cook) ");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int update = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1,auntId);
						if(auntInfo.getPassword()!=null){
							ps.setString(2, CommonStringUtils.getMD5(auntInfo.getPassword().getBytes()));
						}else{
							ps.setString(2, null);
						}
						ps.setString(3, auntInfo.getMobile());
						ps.setString(4, auntInfo.getUserName());
						ps.setString(5, auntInfo.getIdentityCard());
						ps.setString(6, "FAMALE");
						ps.setString(7, auntInfo.getAge());
						ps.setString(8, auntInfo.getAddress());
						ps.setBigDecimal(9, auntInfo.getPrice());
						ps.setString(10, auntInfo.getTelephone());
						ps.setInt(11, auntInfo.getWorkYear());
						ps.setInt(12, auntInfo.getAbilityScore());
						ps.setBoolean(13, auntInfo.isIntegrityAuth());
						ps.setString(14, auntInfo.getDescription());
						ps.setString(15, auntInfo.getNativePlace());
						ps.setString(16, auntInfo.getBusiDesc());
						ps.setString(17, auntInfo.getBloodType());
						ps.setString(18, auntInfo.getSelfEvaluate());
						ps.setString(19, auntInfo.getConstellation());
						ps.setString(20, auntInfo.getWorkType());
						ps.setString(21, auntInfo.getStart());
						ps.setBigDecimal(22, auntInfo.getYearOfIncome());
						ps.setInt(23, auntInfo.getMonthOfOrderCounts());
						ps.setInt(24, auntInfo.getTotalOrderCounts());
						ps.setBigDecimal(25, auntInfo.getMothOfIncome());
						ps.setInt(26, auntInfo.getVerySatisfyCounts());
						ps.setInt(27, auntInfo.getSatisfyCounts());
						ps.setInt(28, auntInfo.getNotSatisfyCounts());
						ps.setInt(29, auntInfo.getMonthOfSignCounts());
						ps.setString(30, auntInfo.getCorpId());
						ps.setString(31, sdf.format(new Date()));
						ps.setString(32, "ACTIVE");
						ps.setBoolean(33, auntInfo.isWorkCleanKeeping());
						ps.setBoolean(34, auntInfo.isWorkLaundry());
						ps.setBoolean(35, auntInfo.isWorkCook());
					}
				});
		if (update > 0) {
			return auntId;
		} else {
			return "FAILED";
		}
	}

	@Override
	public String deleteAunt(String auntId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_aunt_info where aunt_id='" + auntId + "'");
		int update = getJdbcTemplate().update(sql.toString());
		if (update > 0) {
			return "SUCCESS";
		} else {
			return "FAIL";
		}
	}

	@Override
	public String updateAuntInfo(AuntInfo auntInfo) {
		if (StringUtils.isEmpty(auntInfo.getAuntId())) {
			return "FAILED";
		}
		StringBuilder sql = new StringBuilder("update t_aunt_info set ");
//		if (StringUtils.isNotBlank(auntInfo.getPassword())) {
//			sql.append("password='" + CommonStringUtils.getMD5(auntInfo.getPassword().getBytes()) + "',");
//		}
		if (StringUtils.isNotBlank(auntInfo.getMobile())) {
			sql.append("address='" + auntInfo.getMobile() + "',");
		}
		if (StringUtils.isNotBlank(auntInfo.getUserName())) {
			sql.append("user_name='" + auntInfo.getUserName() + "',");
		}
		if (StringUtils.isNotBlank(auntInfo.getMobile())) {
			sql.append("mobile='" + auntInfo.getMobile() + "',");
		}
		if (StringUtils.isNotBlank(auntInfo.getIdentityCard())) {
			sql.append("identity_card='" + auntInfo.getIdentityCard() + "',");
		}
		if (StringUtils.isNotBlank(auntInfo.getSex())) {
			sql.append("status='" + auntInfo.getSex() + "',");
		}
		if (StringUtils.isNotEmpty(auntInfo.getAge())) {
			sql.append("age='" + auntInfo.getAge() + "',");

		}
		if (StringUtils.isNotEmpty(auntInfo.getAddress())) {
			sql.append("address='" + auntInfo.getAddress() + "',");

		}
		if (auntInfo.getPrice() != null) {
			sql.append("price='" + auntInfo.getPrice() + "',");

		}
		if (StringUtils.isNotEmpty(auntInfo.getTelephone())) {
			sql.append("telephone='" + auntInfo.getTelephone() + "',");

		}
		if (auntInfo.getWorkYear() > 0) {
			sql.append("work_year='" + auntInfo.getWorkYear() + "',");

		}
		if (auntInfo.getAbilityScore() > 0) {
			sql.append("ability_score='" + auntInfo.getAbilityScore() + "',");

		}
		if (auntInfo.isIntegrityAuth()) {
			sql.append("integrity_auth=" + auntInfo.isIntegrityAuth() + ",");

		}
		if (StringUtils.isNotEmpty(auntInfo.getDescription())) {
			sql.append("description='" + auntInfo.getDescription() + "',");

		}
		if (StringUtils.isNotEmpty(auntInfo.getNativePlace())) {
			sql.append("native_place='" + auntInfo.getNativePlace() + "',");

		}
		if (StringUtils.isNotEmpty(auntInfo.getBusiDesc())) {
			sql.append("busi_desc='" + auntInfo.getBusiDesc() + "',");

		}
		if (StringUtils.isNotEmpty(auntInfo.getBloodType())) {
			sql.append("blood_type='" + auntInfo.getBloodType() + "',");
		}
		if (StringUtils.isNotEmpty(auntInfo.getSelfEvaluate())) {
			sql.append("self_evaluate='" + auntInfo.getSelfEvaluate() + "',");

		}
		if (StringUtils.isNotEmpty(auntInfo.getConstellation())) {
			sql.append("constellation='" + auntInfo.getConstellation() + "',");

		}
		if (StringUtils.isNotEmpty(auntInfo.getWorkType())) {
			sql.append("work_type='" + auntInfo.getWorkType() + "',");

		}
		if (StringUtils.isNotEmpty(auntInfo.getStart())) {
			sql.append("start='" + auntInfo.getStart() + "',");

		}
		if (auntInfo.getYearOfIncome() != null) {
			sql.append("year_of_income='" + auntInfo.getYearOfIncome() + "',");

		}
		if (auntInfo.getMonthOfOrderCounts() > 0) {
			sql.append("month_of_order_counts='" + auntInfo.getMonthOfOrderCounts()
					+ "',");

		}
		if (auntInfo.getTotalOrderCounts() > 0) {
			sql.append("total_order_counts='" + auntInfo.getTotalOrderCounts()
					+ "',");

		}
		if (auntInfo.getMothOfIncome()!=null) {
			sql.append("moth_of_income='" + auntInfo.getMothOfIncome() + "',");

		}
		if (auntInfo.getVerySatisfyCounts() > 0) {
			sql.append("very_satisfy_counts='" + auntInfo.getVerySatisfyCounts()
					+ "',");

		}
		if (auntInfo.getSatisfyCounts() > 0) {
			sql.append("satisfy_counts='" + auntInfo.getSatisfyCounts() + "',");

		}
		if (auntInfo.getNotSatisfyCounts() > 0) {
			sql.append("not_satisfy_counts='" + auntInfo.getNotSatisfyCounts()
					+ "',");

		}
		if (auntInfo.getMonthOfSignCounts() > 0) {
			sql.append("month_of_sign_counts='" + auntInfo.getMonthOfSignCounts()
					+ "',");

		}
		if (StringUtils.isNotEmpty(auntInfo.getStatus())) {
			sql.append("status='" + auntInfo.getStatus() + "',");

		}
		if (StringUtils.isNotEmpty(auntInfo.getCorpId())) {
			sql.append("corp_id='" + auntInfo.getCorpId() + "',");

		}
		if (auntInfo.isWorkCleanKeeping()) {
			sql.append("work_clean_keeping=" + auntInfo.isWorkCleanKeeping() + ",");

		}else{
			sql.append("work_clean_keeping=false,");
		}
		if (auntInfo.isWorkCook()) {
			sql.append("work_cook=" + auntInfo.isWorkCook() + ",");
		}else{
			sql.append("work_cook=false,");
		}
		if (auntInfo.isWorkLaundry()) {
			sql.append("work_laundry=" + auntInfo.isWorkLaundry() + ",");

		}else{
			sql.append("work_laundry=false,");
		}
		if (sql.lastIndexOf(",") + 1 == sql.length()) {
			sql.delete(sql.lastIndexOf(","), sql.length());
		}
		sql.append(" where aunt_id='" + auntInfo.getAuntId() + "'");
		getJdbcTemplate().update(sql.toString());
		return "SUCCESS";
	}

	@Override
	public OpenPage<AuntInfo> searchAuntByWeb(String corpId,String userName, String mobile,
			OpenPage<AuntInfo> page) {
		   StringBuilder reviewSql = new StringBuilder("");
		   reviewSql.append("select * from t_aunt_info where 1=1");
		   if (StringUtils.isNotEmpty(corpId)) {
			   reviewSql.append(" and corp_id ='"+corpId+"' ");
		   }
		   if (StringUtils.isNotEmpty(userName)) {
			   reviewSql.append(" and user_name like '%"+userName+"%' ");
		   }
		   if (StringUtils.isNotEmpty(mobile)) {
			   reviewSql.append("  and mobile  like '%"+mobile+"%' ");
		   }
		   reviewSql.append(" and status ='ACTIVE' ");
		   reviewSql.append(" limit ?,?");
		   List<AuntInfo> reviewList = getJdbcTemplate().query(reviewSql.toString(),
	                new Object[]{page.getPageSize() * (page.getPageNo()-1),page.getPageSize()},new AuntRowMapper());

		   StringBuilder countSql = new StringBuilder("");
		   countSql.append("select count(*) from t_aunt_info where 1=1");
		   if (StringUtils.isNotEmpty(userName)) {
			   countSql.append(" and user_name like '%"+userName+"%' ");
		   }
		   if (StringUtils.isNotEmpty(mobile)) {
			   countSql.append("  and mobile  like '%"+mobile+"%' ");
		   }
		   if (StringUtils.isNotEmpty(corpId)) {
			   countSql.append(" and corp_id ='"+corpId+"' ");
		   }
		   countSql.append(" and status ='ACTIVE' ");

	        Integer count = getJdbcTemplate().queryForObject(countSql.toString(),Integer.class);
	        page.setTotal(count);
	        page.setRows(reviewList);
	        return page;
	}

	@Override
	public Boolean checkIdentityCard(String identityCard) {
		  StringBuilder reviewSql = new StringBuilder("");
		   reviewSql.append("select * from t_aunt_info where identity_card='"+identityCard+"'");
		   
		   List<AuntInfo> reviewList = getJdbcTemplate().query(reviewSql.toString(),new AuntRowMapper());
		   if(CollectionUtils.isEmpty(reviewList)){
			   return true;
		   }else{
			   return false;
		   }
	}

	@Override
	public AuntInfo checkIdentityByCardAndMobile(String card, String mobile) {
		  StringBuilder reviewSql = new StringBuilder("");
		   reviewSql.append("select * from t_aunt_info where identity_card='"+card+"' and mobile='"+mobile+"'");
		   List<AuntInfo> reviewList = getJdbcTemplate().query(reviewSql.toString(),new AuntRowMapper());
		   if(CollectionUtils.isEmpty(reviewList)){
			   return null;
		   }
		   return reviewList.get(0);
	}

	@Override
	public String resetPassword(String auntId, String newPassword) {
		if(StringUtils.isEmpty(auntId)|| StringUtils.isEmpty(newPassword)){
			return "FAILED";
		}
		StringBuilder sql = new StringBuilder("update t_aunt_info set ");
		if (StringUtils.isNotBlank(newPassword)) {
			sql.append("password='" + CommonStringUtils.getMD5(newPassword.getBytes()) + "' ");
		}	
		sql.append(" where aunt_id='" + auntId + "'");
		getJdbcTemplate().update(sql.toString());
		return "SUCCESS";
	}
}