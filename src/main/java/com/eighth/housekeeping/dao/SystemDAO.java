package com.eighth.housekeeping.dao;

import com.eighth.housekeeping.domain.APKVersion;

/**
 * Created by dam on 2014/7/30.
 */
public interface SystemDAO {

    APKVersion findLastVersion();
}
