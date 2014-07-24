package com.eighth.housekeeping.controller;

import com.eighth.housekeeping.domain.SignInfo;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.SignInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/SignInfoService")
public class SignInfoServiceController {
    @Autowired
    SignInfoService signInfoService;

    @ResponseBody
    @RequestMapping(value = "/sign")
    public SignInfo sign(@RequestParam String auntId) {
        SignInfo signInfo = null;
        try {
            signInfo = signInfoService.sign(auntId);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return signInfo;
    }
}
