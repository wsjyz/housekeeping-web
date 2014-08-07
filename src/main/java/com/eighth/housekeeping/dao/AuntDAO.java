package com.eighth.housekeeping.dao;

import com.eighth.housekeeping.domain.AuntInfo;

/**
 * Created by dam on 2014/7/28.
 */
public interface AuntDAO {

    AuntInfo findAuntByIdForAunt(String auntId);

    AuntInfo findAuntByIdForMember(String auntId,String memberId);

    AuntInfo findAuntByMobileAndPsw(String mobile,String password);
}
