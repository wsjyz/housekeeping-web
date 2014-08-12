package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.AuntDAO;
import com.eighth.housekeeping.dao.AuntWorkCaseDAO;
import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.CollectAuntDAO;
import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.CollectAunt;
import com.eighth.housekeeping.domain.OpenPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dam on 2014/7/28.
 */
@Repository("AuntDAO")
public class AuntDAOImpl extends BaseDAO implements AuntDAO {

    @Autowired
    private AuntWorkCaseDAO auntWorkCaseDAO;

    @Autowired
    private CollectAuntDAO collectAuntDAO;

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
    public AuntInfo findAuntByIdForMember(String auntId,String memberId) {
        StringBuilder sql = new StringBuilder("");
        sql.append("select * from t_aunt_info where aunt_id = ?");
        List<AuntInfo> auntInfoList = getJdbcTemplate().query(sql.toString(),new String[]{auntId},new AuntRowMapper());
        if(!CollectionUtils.isEmpty(auntInfoList) ){
            AuntInfo auntInfo = auntInfoList.get(0);
            auntInfo.setCaseList(auntWorkCaseDAO.findCaseByAuntId(auntId));
            CollectAunt collectAunt = collectAuntDAO.findCollectAuntByMemberIdAndAuntId(memberId,auntId);
            if(collectAunt != null){
                auntInfo.setUserCollected(true);
            }else{
                auntInfo.setUserCollected(false);
            }

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

    @Override
    public OpenPage<AuntInfo> searchAuntByCondition(AuntInfo auntInfo, OpenPage<AuntInfo> page) {
        StringBuilder auntSql = new StringBuilder("select tai.aunt_id,tai.user_name,tai.start," +
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
        if(auntInfo.getAge() != 0){
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

    public class AuntRowMapper implements RowMapper<AuntInfo>{

        @Override
        public AuntInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            AuntInfo auntInfo = new AuntInfo();
            auntInfo.setUserName(rs.getString("user_name"));
            auntInfo.setCorpId(rs.getString("corp_id"));
            auntInfo.setAbilityScore(rs.getInt("ability_score"));
            auntInfo.setAddress(rs.getString("address"));
            auntInfo.setAge(rs.getInt("age"));
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
