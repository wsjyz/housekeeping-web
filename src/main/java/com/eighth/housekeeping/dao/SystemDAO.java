package com.eighth.housekeeping.dao;

import com.eighth.housekeeping.domain.APKVersion;
import com.eighth.housekeeping.domain.FeedBack;
import com.eighth.housekeeping.domain.SystemManage;

/**
 * Created by dam on 2014/7/30.
 */
public interface SystemDAO {

    void saveFeedBack(FeedBack feedBack);

    APKVersion findLastVersion();

    SystemManage findSystemManage();
}
