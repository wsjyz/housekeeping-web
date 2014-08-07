package com.eighth.housekeeping.controller;

import com.eighth.housekeeping.domain.APKVersion;
import com.eighth.housekeeping.domain.FeedBack;
import com.eighth.housekeeping.domain.SystemManage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.SystemService;
import com.eighth.housekeeping.web.FastJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/SystemService")
public class SystemServiceController {
    @Autowired
    SystemService systemService;

    @ResponseBody
    @RequestMapping(value = "/updateAPK")
    public APKVersion updateAPK(@RequestParam String currentVersionCode) {
        APKVersion aPKVersion = null;
        try {
            aPKVersion = systemService.updateAPK(currentVersionCode);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return aPKVersion;
    }
    @ResponseBody
    @RequestMapping(value = "/findSystemManage")
    public SystemManage findSystemManage(){
        SystemManage systemManage = null;
        try {
            systemManage = systemService.findSystemManage();
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return systemManage;
    }
    @ResponseBody
    @RequestMapping(value = "/saveFeedBack")
    FeedBack saveFeedBack(@FastJson FeedBack feedBack){
        FeedBack newFeed = null;
        try {
            newFeed = systemService.saveFeedBack(feedBack);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return newFeed;
    }
    @ResponseBody
    @RequestMapping(value = "/appLogout")
    String appLogout(@RequestParam String userId,@RequestParam String userType){
        try {
            return systemService.appLogout(userId,userType);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return "FAIL";
    }
}