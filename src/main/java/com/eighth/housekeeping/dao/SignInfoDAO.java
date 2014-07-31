package com.eighth.housekeeping.dao;

import com.eighth.housekeeping.domain.SignInfo;

/**
 * Created by dam on 2014/7/30.
 */
public interface SignInfoDAO {

    void saveSignInfo(SignInfo info);

    SignInfo findTodaySignInfoByAuntId(String auntId);

    SignInfo findSignDetail(String auntId);
}
