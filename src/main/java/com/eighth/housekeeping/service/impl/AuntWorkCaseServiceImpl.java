package com.eighth.housekeeping.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.eighth.housekeeping.dao.AuntWorkCaseDAO;
import com.eighth.housekeeping.dao.ImageObjDAO;
import com.eighth.housekeeping.domain.AuntWorkCase;
import com.eighth.housekeeping.domain.ImageObj;
import com.eighth.housekeeping.proxy.service.AuntWorkCaseService;
import com.eighth.housekeeping.utils.Constants;

/**
 * Created by dam on 2014/7/28.
 */
@Repository("AuntWorkCaseService")
public class AuntWorkCaseServiceImpl  implements AuntWorkCaseService {

    @Autowired
    private AuntWorkCaseDAO auntWorkCaseDAO;

    @Autowired
    ImageObjDAO imageObjDAO;
	@Override
	public List<AuntWorkCase> findCaseByAuntId(String auntId) {
		 List<AuntWorkCase> list = auntWorkCaseDAO.findCaseByAuntId(auntId);
		 return list;
	}

	@Override
	public AuntWorkCase findCaseById(String caseId) {
		AuntWorkCase auntWorkCase= auntWorkCaseDAO.findCaseById(caseId);
		List<ImageObj> list2 = imageObjDAO.findImageObjByObjIdAndType(auntWorkCase.getCaseId(),Constants.WORKCASE);
		auntWorkCase.setImages(list2);
		return auntWorkCase;
	}

	@Override
	public void addWorkCase(AuntWorkCase auntWorkCase) {
		auntWorkCaseDAO.addWorkCase(auntWorkCase);		
	}

	@Override
	public String updateWorkCase(AuntWorkCase auntWorkCase) {
		return auntWorkCaseDAO.updateWorkCase(auntWorkCase);
	}

	@Override
	public void deleteWorkCase(String auntId) {
		 auntWorkCaseDAO.deleteWorkCase(auntId);
	}

}
