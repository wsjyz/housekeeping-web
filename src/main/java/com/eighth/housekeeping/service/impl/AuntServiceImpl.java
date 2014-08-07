package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.dao.AuntDAO;
import com.eighth.housekeeping.dao.AuntWorkCaseDAO;
import com.eighth.housekeeping.dao.ReviewDAO;
import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.AuntWorkCase;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.AuntService;
import com.eighth.housekeeping.utils.CommonStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dam on 2014/7/24.
 */
@Service("AuntService")
public class AuntServiceImpl implements AuntService {
    @Autowired
    private AuntDAO auntDAO;
    @Autowired
    private AuntWorkCaseDAO auntWorkCaseDAO;
    @Autowired
    private ReviewDAO reviewDAO;

    @Override
    public AuntInfo login(String mobile, String password) throws RemoteInvokeException {
        String md5Psw = CommonStringUtils.getMD5(password.getBytes());
        return auntDAO.findAuntByMobileAndPsw(mobile,md5Psw);
    }

    @Override
    public AuntInfo findAuntByIdForAunt(String auntId) throws RemoteInvokeException {
        return auntDAO.findAuntByIdForAunt(auntId);
    }

    @Override
    public AuntInfo findAuntByIdForMember(String auntId,String memberId) throws RemoteInvokeException {
        return auntDAO.findAuntByIdForMember(auntId,memberId);
    }

    @Override
    public AuntWorkCase findCaseById(String caseId) throws RemoteInvokeException {
        return auntWorkCaseDAO.findCaseById(caseId);
    }

    @Override
    public OpenPage<Review> findReviewByAuntId(String reviewTag, String auntId, OpenPage page) throws RemoteInvokeException {
        return reviewDAO.findReviewByAuntId(reviewTag,auntId,page);
    }

    @Override
    public String resetPassword(String auntId, String oldPassword, String newPassword) throws RemoteInvokeException {
        return null;
    }
}
