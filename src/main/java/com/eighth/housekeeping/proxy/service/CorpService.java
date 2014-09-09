package com.eighth.housekeeping.proxy.service;

import java.util.List;

import com.eighth.housekeeping.domain.Corp;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

/**
 * 阿姨相关 Created by dam on 2014/7/4.
 */
public interface CorpService {
	OpenPage<Corp> findCorpPage(String corpName, String loginName, OpenPage page)
			throws RemoteInvokeException;

	void saveCorp(Corp corp) throws RemoteInvokeException;

	Corp findCorpId(String corpId) throws RemoteInvokeException;

	String updateCorp(Corp corp) throws RemoteInvokeException;

	String deleteCorp(String corpId) throws RemoteInvokeException;

	Corp login(String loginName, String password);

	List<Corp> corpList();

}
