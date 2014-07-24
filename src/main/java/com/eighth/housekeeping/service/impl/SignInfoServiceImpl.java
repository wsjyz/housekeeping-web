package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.domain.SignInfo;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.SignInfoService;
import org.springframework.stereotype.Service;

/**
 * Created by dam on 2014/7/24.
 */
@Service("SignInfoService")
public class SignInfoServiceImpl implements SignInfoService {
    @Override
    public SignInfo sign(String auntId) throws RemoteInvokeException {
        return null;
    }
}
