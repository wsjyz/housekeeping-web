package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.domain.APKVersion;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.SystemService;
import org.springframework.stereotype.Service;

/**
 * Created by dam on 2014/7/24.
 */
@Service("SystemService")
public class SystemServiceImpl implements SystemService {
    @Override
    public APKVersion updateAPK(String currentVersionCode) throws RemoteInvokeException {
        return null;
    }
}
