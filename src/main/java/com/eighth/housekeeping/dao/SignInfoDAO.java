package com.eighth.housekeeping.dao;

import java.util.List;

import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.SignInfo;

/**
 * Created by dam on 2014/7/30.
 */
public interface SignInfoDAO {

    void saveSignInfo(SignInfo info);

    SignInfo findTodaySignInfoByAuntId(String auntId);

    SignInfo findSignDetail(String auntId);
    List<SignInfo> getListByAuntId(String auntId);

	OpenPage<SignInfo> searchAttendanceByWeb(String corpName, String auntName,
			OpenPage<SignInfo> page);
}
