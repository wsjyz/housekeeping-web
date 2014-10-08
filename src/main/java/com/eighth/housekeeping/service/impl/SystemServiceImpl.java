package com.eighth.housekeeping.service.impl;

import java.util.List;

import com.eighth.housekeeping.dao.ImageObjDAO;
import com.eighth.housekeeping.dao.SystemDAO;
import com.eighth.housekeeping.domain.APKVersion;
import com.eighth.housekeeping.domain.FeedBack;
import com.eighth.housekeeping.domain.ImageObj;
import com.eighth.housekeeping.domain.MemberInfo;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.SystemManage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.SystemService;
import com.eighth.housekeeping.proxy.service.UserService;
import com.eighth.housekeeping.utils.CommonStringUtils;
import com.eighth.housekeeping.utils.Constants;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Created by dam on 2014/7/24.
 */
@Service("SystemService")
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemDAO systemDAO;
    @Autowired
    ImageObjDAO imageObjDAO;
    @Override
    public APKVersion updateAPK(String currentVersionCode) throws RemoteInvokeException {
        APKVersion version = systemDAO.findLastVersion();
//        Integer clientVersion = 0;
//        Integer serverVersion = 0;
//        if(currentVersionCode.indexOf(".") != -1 && version != null){
//            clientVersion = Integer.parseInt(currentVersionCode.replace(".",""));
//            serverVersion = Integer.parseInt(version.getLastVersionCode().replace(".",""));
//            if(serverVersion > clientVersion){
//                return version;
//            }
//        }
        return version;
    }

    @Override
    public SystemManage findSystemManage() throws RemoteInvokeException {
    	SystemManage SystemManage= systemDAO.findSystemManage();
		List<ImageObj> list2 = imageObjDAO.findImageObjByObjIdAndType(SystemManage.getSystemId(),Constants.APPLOGO);
		SystemManage.setImages(list2);
    	return SystemManage;
    }

    @Override
    public FeedBack saveFeedBack(FeedBack feedBack) throws RemoteInvokeException {
        feedBack.setFeedbackId(CommonStringUtils.genPK());
        systemDAO.saveFeedBack(feedBack);
        return feedBack;
    }

    @Override
    public String appLogout(String userId, String userType) throws RemoteInvokeException {
        return "SUCCESS";
    }

	@Override
	public void updateSystemManage(SystemManage systemManage) {
		systemDAO.updateSystemManage(systemManage);
		
	}

	@Override
	public OpenPage findFeedBack(String userName,OpenPage page) {
		return page= systemDAO.findFeedBack(userName,page);
	
	}

	@Override
	public void deleteFeedWeb(String feedId) {
		systemDAO.deleteFeedWeb(feedId);
	}
}
