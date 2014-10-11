package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.dao.SignInfoDAO;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.SignInfo;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.SignInfoService;
import com.eighth.housekeeping.utils.CommonStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by dam on 2014/7/24.
 */
@Service("SignInfoService")
public class SignInfoServiceImpl implements SignInfoService {
    @Autowired
    private SignInfoDAO signInfoDAO;
    @Override
    public SignInfo sign(SignInfo signInfo) throws RemoteInvokeException {
        signInfo.setSignId(CommonStringUtils.genPK());
        String currentTime = signInfo.getOptTime();
        String currentDate = currentTime.substring(0,currentTime.indexOf(" "));
        String[] dateArray = currentDate.split("-");

        signInfo.setSignYear(dateArray[0]);
        signInfo.setSignMonth(dateArray[1]);
        signInfo.setSignDay(dateArray[2]);
        signInfoDAO.saveSignInfo(signInfo);
        Integer count = signInfoDAO.findAuntMonthSignCount(signInfo.getAuntId(),dateArray[0],dateArray[1]);
        signInfo.setSignCountsMonth(count);
        return signInfo;
    }

    @Override
    public SignInfo findSignDetail(String auntId) throws RemoteInvokeException {

        return signInfoDAO.findSignDetail(auntId);
    }

	@Override
	public List<SignInfo> getListByAuntId(String auntId) {
		List<SignInfo> list = signInfoDAO.getListByAuntId(auntId);
		return list;
	}

	@Override
	public OpenPage<SignInfo> searchAttendanceByWeb(String corpName,
			String auntName, OpenPage<SignInfo> page) {
		return signInfoDAO.searchAttendanceByWeb(corpName,auntName,page);
	}
}
