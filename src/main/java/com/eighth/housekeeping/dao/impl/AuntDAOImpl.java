package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.AuntDAO;
import com.eighth.housekeeping.dao.AuntWorkCaseDAO;
import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.domain.AuntInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        List<AuntInfo> auntInfoList = getJdbcTemplate().query(sql.toString(),new String[]{auntId},new AuntRowMapper());
        if(!CollectionUtils.isEmpty(auntInfoList)){
            AuntInfo auntInfo = auntInfoList.get(0);
            return auntInfo;
        }
        return null;
    }

    @Override
    public AuntInfo findAuntByIdForMember(String auntId) {
        StringBuilder sql = new StringBuilder("");
        sql.append("select * from t_aunt_info where aunt_id = ?");
        List<AuntInfo> auntInfoList = getJdbcTemplate().query(sql.toString(),new String[]{auntId},new AuntRowMapper());
        if(!CollectionUtils.isEmpty(auntInfoList) ){
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
        List<AuntInfo> auntInfoList = getJdbcTemplate().query(sql.toString(),new String[]{mobile,password},new AuntRowMapper());
        AuntInfo auntInfo = null;
        if(!CollectionUtils.isEmpty(auntInfoList)) {
            auntInfo = auntInfoList.get(0);
            auntInfo.setLoginResult("SUCCESS");
        }else{
            auntInfo = new AuntInfo();
            auntInfo.setLoginResult("ACOUNT_NOT_CORRECT");
        }
        return auntInfo;
    }

    public class AuntRowMapper implements RowMapper<AuntInfo>{

        @Override
        public AuntInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            AuntInfo auntInfo = new AuntInfo();
            auntInfo.setUserName(rs.getString("user_name"));
            auntInfo.setCorpId(rs.getString("corp_id"));
            auntInfo.setAbilityScore(rs.getInt("ability_score"));
            auntInfo.setAddress(rs.getString("address"));
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
            auntInfo.setMothOfIncome(rs.getInt("moth_of_income"));
            auntInfo.setVerySatisfyCounts(rs.getInt("very_satisfy_counts"));
            auntInfo.setSatisfyCounts(rs.getInt("satisfy_counts"));
            auntInfo.setNotSatisfyCounts(rs.getInt("not_satisfy_counts"));
            return auntInfo;
        }
    }
}
