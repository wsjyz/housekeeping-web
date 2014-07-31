package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.AuntWorkCaseDAO;
import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.ImageObjDAO;
import com.eighth.housekeeping.domain.AuntWorkCase;
import com.eighth.housekeeping.domain.ImageObj;
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
            return workCase;
        }
    }
}
