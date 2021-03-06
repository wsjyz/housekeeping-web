package com.eighth.housekeeping.controller;

import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.SignInfo;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.SignInfoService;
import com.eighth.housekeeping.web.FastJson;

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
    public SignInfo sign(@FastJson SignInfo signInfo) {
        SignInfo result = null;
        try {
            result = signInfoService.sign(signInfo);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/findSignDetail")
    public SignInfo findSignDetail(@RequestParam String auntId){
        SignInfo result = null;
        try {
            result = signInfoService.findSignDetail(auntId);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value = "/toAuntAttendance")
   	public String toAuntAttendance()  throws RemoteInvokeException{
   		return "aunt/aunt-attendance";
   	}
    
	  @ResponseBody
    @RequestMapping(value = "/searchAttendanceByWeb")
    public OpenPage<SignInfo> searchAttendanceByWeb(@RequestParam String corpName,@RequestParam String auntName, @FastJson OpenPage<SignInfo> page){
        try {
        	page = signInfoService.searchAttendanceByWeb(corpName,auntName,page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }
		  
}
