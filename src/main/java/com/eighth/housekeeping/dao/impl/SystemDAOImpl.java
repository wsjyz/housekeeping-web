package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.SystemDAO;
import com.eighth.housekeeping.domain.APKVersion;
import com.eighth.housekeeping.domain.SystemManage;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dam on 2014/7/30.
 */
@Repository("SystemDAO")
public class SystemDAOImpl extends BaseDAO implements SystemDAO {
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
}
