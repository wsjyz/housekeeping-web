package com.eighth.housekeeping.dao.impl;

import com.eighth.housekeeping.dao.BaseDAO;
import com.eighth.housekeeping.dao.SignInfoDAO;
import com.eighth.housekeeping.domain.SignInfo;

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
 * Created by dam on 2014/7/30.
 */
@Repository("SignInfoDAO")
public class SignInfoDAOImpl extends BaseDAO implements SignInfoDAO {
	@Override
	public void saveSignInfo(final SignInfo info) {
		StringBuilder sql = new StringBuilder("insert into t_sign_info "
				+ "(sign_id,aunt_id,signYear,signDay,"
				+ "signMonth,sign_place_desc,sign_geographic,corp_id,opt_time)");
		sql.append("values(?,?,?,?,?,?,?,?,?)");
		getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, info.getSignId());
				ps.setString(2, info.getAuntId());
				ps.setString(3, info.getSignYear());
				ps.setString(4, info.getSignDay());
				ps.setString(5, info.getSignMonth());
				ps.setString(6, info.getSignPlaceDesc());
				ps.setString(7, info.getSignGeographic());
				ps.setString(8, info.getCorpId());
				ps.setString(9, info.getOptTime());
			}
		});
	}

	@Override
	public SignInfo findTodaySignInfoByAuntId(String auntId) {
		StringBuilder sql = new StringBuilder("");
		sql.append("select * from t_sign_info where aunt_id = ? and opt_time like ?");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayStr = sdf.format(new Date());
		List<SignInfo> signInfoList = getJdbcTemplate().query(sql.toString(),
				new String[] { auntId, todayStr + "%" },
				new SignInfoRowMapper());
		if (!CollectionUtils.isEmpty(signInfoList)) {
			SignInfo signInfo = signInfoList.get(0);
			return signInfo;
		}
		return null;
	}

	@Override
	public SignInfo findSignDetail(String auntId) {
		SignInfo signInfo = new SignInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String currentMonth = sdf.format(new Date());
		StringBuilder sql = new StringBuilder(
				"select count(*) from t_sign_info where aunt_id = ? and opt_time like ?");
		int counts = getJdbcTemplate().queryForObject(sql.toString(),
				new String[] { auntId, currentMonth }, Integer.class);

		StringBuilder selectSql = new StringBuilder(
				"SELECT max(opt_time) from t_sign_info where aunt_id = ? ");
		String lastSignDate = getJdbcTemplate().queryForObject(
				selectSql.toString(), new String[] { auntId }, String.class);

		signInfo.setLastSignDate(lastSignDate);
		signInfo.setSignCountsMonth(counts);
		return signInfo;
	}

	public class SignInfoRowMapper implements RowMapper<SignInfo> {

		@Override
		public SignInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			SignInfo info = new SignInfo();
			info.setCorpId(rs.getString("corp_id"));
			info.setAuntId(rs.getString("aunt_id"));
			info.setSignDay(rs.getString("signDay"));
			info.setSignGeographic("sign_geographic");
			info.setSignId(rs.getString("sign_id"));
			info.setSignMonth(rs.getString("signMonth"));
			info.setSignPlaceDesc(rs.getString("sign_place_desc"));
			info.setSignYear(rs.getString("signYear"));
			info.setOptTime(rs.getString("opt_time"));
			return info;
		}
	}

	@Override
	public List<SignInfo> getListByAuntId(String auntId) {
		StringBuilder sql = new StringBuilder("");
		sql.append("select * from t_sign_info where aunt_id = ?");
		List<SignInfo> signInfoList = getJdbcTemplate().query(sql.toString(),
				new String[] { auntId }, new SignInfoRowMapper());
		return signInfoList;
	}
}
