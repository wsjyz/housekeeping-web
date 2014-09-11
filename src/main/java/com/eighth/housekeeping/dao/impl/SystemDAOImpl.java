package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.SystemDAO;
import com.eighth.housekeeping.domain.APKVersion;
import com.eighth.housekeeping.domain.FeedBack;
import com.eighth.housekeeping.domain.SystemManage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dam on 2014/7/30.
 */
@Repository("SystemDAO")
public class SystemDAOImpl extends BaseDAO implements SystemDAO {

    @Override
    public void saveFeedBack(final FeedBack feedBack) {
        StringBuilder sql = new StringBuilder("insert into t_feedback " +
                "(feedback_id,aunt_id,member_id,content," +
                "corp_id,opt_time)");
        sql.append("values(?,?,?,?,?,?)");
        getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,feedBack.getFeedbackId());
                ps.setString(2,feedBack.getAuntId());
                ps.setString(3,feedBack.getMemberId());
                ps.setString(4,feedBack.getContent());
                ps.setString(5,feedBack.getCorpId());
                ps.setString(6,feedBack.getOptTime());
            }
        });
    }

    @Override
    public APKVersion findLastVersion() {
        StringBuilder sql = new StringBuilder("");
        sql.append("select * from t_apk_version");
        List<APKVersion> versionList = getJdbcTemplate().query(sql.toString(),new APKVersionRowMapper());
        if(!CollectionUtils.isEmpty(versionList)){
            APKVersion version = versionList.get(0);
            return version;
        }
        return null;
    }

    @Override
    public SystemManage findSystemManage() {
        StringBuilder sql = new StringBuilder("");
        sql.append("select * from t_system_manage");
        List<SystemManage> dataList = getJdbcTemplate().query(sql.toString(),new SystemManageRowMapper());
        if(!CollectionUtils.isEmpty(dataList)){
            SystemManage systemManage = dataList.get(0);
            return systemManage;
        }
        return null;
    }

    public class APKVersionRowMapper implements RowMapper<APKVersion>{

        @Override
        public APKVersion mapRow(ResultSet rs, int rowNum) throws SQLException {
            APKVersion version = new APKVersion();
            version.setDownloadUrl(rs.getString("download_url"));
            version.setLastVersionCode(rs.getString("last_version_code"));
            return version;
        }
    }

    public class SystemManageRowMapper implements RowMapper<SystemManage>{

        @Override
        public SystemManage mapRow(ResultSet rs, int rowNum) throws SQLException {
            SystemManage systemManage = new SystemManage();
            systemManage.setAppLogo(rs.getString("app_logo"));
            systemManage.setAppPhone(rs.getString("app_phone"));
            systemManage.setHourlyUnitPrice(rs.getBigDecimal("hourly_unit_price"));
            systemManage.setMainPageTip(rs.getString("main_page_tip"));
            systemManage.setNewHouseUnitPrice(rs.getBigDecimal("new_house_unit_price"));
            systemManage.setPushInfo(rs.getString("push_info"));
            systemManage.setPushInfoInterval(rs.getInt("push_info_interval"));
            systemManage.setPushInfoTime(rs.getString("push_info_time"));
            systemManage.setSearchKey(rs.getString("search_key"));
            return systemManage;
        }
    }

	@Override
	public void updateSystemManage(SystemManage systemManage) {
		 StringBuilder sql = new StringBuilder("");
	        sql.append("update t_system_manage set ");
	    	if (StringUtils.isNotBlank(systemManage.getMainPageTip())) {
				sql.append("main_page_tip='" + systemManage.getMainPageTip() + "',");
			}
	    	if (systemManage.getHourlyUnitPrice()!=null) {
				sql.append("hourly_unit_price='" + systemManage.getHourlyUnitPrice() + "',");
			}
	    	if (systemManage.getNewHouseUnitPrice()!=null) {
				sql.append("new_house_unit_price='" + systemManage.getNewHouseUnitPrice() + "',");
			}
	    	if (StringUtils.isNotBlank(systemManage.getPushInfoTime())) {
				sql.append("push_info_time='" + systemManage.getPushInfoTime() + "',");
			}
	    	if (StringUtils.isNotBlank(systemManage.getPushInfo())) {
				sql.append("push_info='" + systemManage.getPushInfo() + "',");
			}
	    	
			sql.append("push_info_interval='" + systemManage.getPushInfoInterval() + "',");
	    	if (StringUtils.isNotBlank(systemManage.getSearchKey())) {
				sql.append("search_key='" + systemManage.getSearchKey() + "',");
			}
			if (sql.lastIndexOf(",") + 1 == sql.length()) {
				sql.delete(sql.lastIndexOf(","), sql.length());
			}
			getJdbcTemplate().update(sql.toString());
	}
}
