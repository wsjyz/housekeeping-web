package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.AuntWorkCase;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.AuntService;
import org.springframework.stereotype.Service;

/**
 * Created by dam on 2014/7/24.
 */
@Service("AuntService")
public class AuntServiceImpl implements AuntService {
    @Override
    public AuntInfo login(String mobile, String password) throws RemoteInvokeException {
        return null;
    }

    @Override
    public AuntInfo findAuntByIdForAunt(String auntId) throws RemoteInvokeException {
        return null;
    }

    @Override
    public AuntInfo findAuntByIdForMember(String auntId) throws RemoteInvokeException {
        return null;
    }

    @Override
    public AuntWorkCase findCaseById(String caseId) throws RemoteInvokeException {
        return null;
    }

    @Override
    public OpenPage<Review> findReviewByAuntId(String reviewTag, String auntId, OpenPage page) throws RemoteInvokeException {
        return null;
    }

    @Override
    public String resetPassword(String auntId, String oldPassword, String newPassword) throws RemoteInvokeException {
        return null;
    }
}
