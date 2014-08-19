package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.AuntWorkCaseDAO;
import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.ImageObjDAO;
import com.eighth.housekeeping.domain.AuntWorkCase;
import com.eighth.housekeeping.domain.ImageObj;
import com.eighth.housekeeping.utils.CommonStringUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dam on 2014/7/28.
 */
@Repository("AuntWorkCaseDAO")
public class AuntWorkCaseDAOImpl extends BaseDAO implements AuntWorkCaseDAO {

    @Autowired
    private ImageObjDAO imageObjDAO;

    @Override
    public List<AuntWorkCase> findCaseByAuntId(String auntId) {
        StringBuilder caseSql = new StringBuilder("");
        caseSql.append("select * from t_aunt_work_case where aunt_id = ?");
        List<AuntWorkCase> caseList = getJdbcTemplate().query(caseSql.toString(),new String[]{auntId},new AuntWorkCaseMapper());

        return caseList;
    }

    @Override
    public AuntWorkCase findCaseById(String caseId) {
        StringBuilder caseSql = new StringBuilder("");
        caseSql.append("select * from t_aunt_work_case where case_id = ?");
        List<AuntWorkCase> caseList = getJdbcTemplate().query(caseSql.toString(),new String[]{caseId},new AuntWorkCaseMapper());
        if(!CollectionUtils.isEmpty(caseList)){
            AuntWorkCase workCase = caseList.get(0);
            List<ImageObj> objs = imageObjDAO.findImageObjByObjIdAndType(caseId, "WORKCASE");
            workCase.setImages(objs);
            return workCase;
        }
        return null;
    }

    public class AuntWorkCaseMapper implements RowMapper<AuntWorkCase>{

        @Override
        public AuntWorkCase mapRow(ResultSet rs, int rowNum) throws SQLException {
            AuntWorkCase workCase = new AuntWorkCase();
            workCase.setAuntId(rs.getString("aunt_id"));
            workCase.setCaseId(rs.getString("case_id"));
            workCase.setDescription(rs.getString("description"));
            if (StringUtils.isNotEmpty(workCase.getDescription())) {
				if (workCase.getDescription().length()>5) {
					workCase.setCaseName(workCase.getDescription().substring(0,5));
				}else{
					workCase.setCaseName(workCase.getDescription());
				}
			}
            return workCase;
        }
    }

	@Override
	public void addWorkCase(final AuntWorkCase auntWorkCase) {
	     StringBuilder caseSql = new StringBuilder("");
	        caseSql.append("insert into t_aunt_work_case(aunt_id,case_id,description,corp_id,opt_time) values(?,?,?,?,?)");
	        final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        getJdbcTemplate().update(caseSql.toString(),new PreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement ps) throws SQLException {
	                ps.setString(1,auntWorkCase.getAuntId());
	                ps.setString(2,CommonStringUtils.genPK());
	                ps.setString(3,auntWorkCase.getDescription());
	                ps.setString(4,auntWorkCase.getCorpId());
	                ps.setString(5,sdf.format(new Date()));
	            }
	        });
	}

	@Override
	public String updateWorkCase(AuntWorkCase auntWorkCase) {
		   StringBuilder caseSql = new StringBuilder("");
	        caseSql.append("update  t_aunt_work_case set ");
	        if (StringUtils.isNotEmpty(auntWorkCase.getDescription())) {
	        	caseSql.append("description='"+auntWorkCase.getDescription()+"',");
			}
	        caseSql.append("where case_id='"+auntWorkCase.getCaseId()+"'");
		return null;
	}
}
