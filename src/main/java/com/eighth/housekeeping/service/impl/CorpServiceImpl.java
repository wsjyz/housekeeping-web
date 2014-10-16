package com.eighth.housekeeping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eighth.housekeeping.dao.CorpDAO;
import com.eighth.housekeeping.domain.Corp;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.CorpService;
import com.eighth.housekeeping.utils.CommonStringUtils;

/**
 * Created by dam on 2014/7/24.
 */
@Service("CorpService")
public class CorpServiceImpl implements CorpService {
	@Autowired
	CorpDAO corpDao;
	@Override
	public OpenPage<Corp> findCorpPage(String corpName, String loginName,
			OpenPage page)  throws RemoteInvokeException{
		return corpDao.findCorpPage(corpName, loginName, page);
	}

	@Override
	public void saveCorp(Corp corp)  throws RemoteInvokeException{
		corpDao.saveCorp(corp);
	}

	@Override
	public Corp findCorpId(String corpId)  throws RemoteInvokeException{
		return corpDao.findCorpId(corpId);
	}

	@Override
	public String updateCorp(Corp corp)  throws RemoteInvokeException{
		return corpDao.updateCorp(corp);
	}

	@Override
	public String deleteCorp(String corpId)  throws RemoteInvokeException{
		return corpDao.deleteCorp(corpId);
	}

	@Override
	public Corp login(String loginName, String password) {
		return corpDao.login(loginName,password);
	}

	@Override
	public List<Corp> corpList() {
		return corpDao.corpList();
	}

	@Override
	public Corp getCorpByName(String corpName) {
		return corpDao.getCorpByName(corpName);
	}
 
}
