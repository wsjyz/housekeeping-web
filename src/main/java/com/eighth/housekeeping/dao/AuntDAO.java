package com.eighth.housekeeping.dao;

import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.OpenPage;

/**
 * Created by dam on 2014/7/28.
 */
public interface AuntDAO {

	AuntInfo findAuntByIdForAunt(String auntId);

	AuntInfo findAuntByIdForMember(String auntId);

	AuntInfo findAuntByMobileAndPsw(String mobile, String password);

	OpenPage<AuntInfo> searchAuntByCondition(AuntInfo auntInfo,
			OpenPage<AuntInfo> page);

	void modifyAuntGeo(String auntId, double longitude, double latitude);

	String addAuntInfo(AuntInfo auntInfo);

	String updateAuntInfo(AuntInfo auntInfo);

	String deleteAunt(String auntId);

	OpenPage<AuntInfo> searchAuntByWeb(String corpId,String userName, String mobile,
			OpenPage<AuntInfo> page);
}