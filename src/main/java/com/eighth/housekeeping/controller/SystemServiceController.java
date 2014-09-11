package com.eighth.housekeeping.controller;

import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

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
    @ResponseBody
	@RequestMapping(value = "/toDataManager")
	public ModelAndView toDataManager()  throws RemoteInvokeException{
		ModelAndView view =new ModelAndView();
		Map<String, Object> model = view.getModel();
		SystemManage systemManage = systemService.findSystemManage();
		model.put("systemManage", systemManage);
		view.setViewName("data-Manage/data-management");
		return view;
	}
    
    @ResponseBody
   	@RequestMapping(value = "/toDataManagerEdit")
   	public ModelAndView toDataManagerEdit()  throws RemoteInvokeException{
   		ModelAndView view =new ModelAndView();
   		Map<String, Object> model = view.getModel();
   		SystemManage systemManage = systemService.findSystemManage();
   		model.put("systemManage", systemManage);
   		view.setViewName("data-Manage/data-management-modify");
   		return view;
   	}
    
    @ResponseBody
   	@RequestMapping(value = "/updateManage")
   	public void updateManage(@FastJson SystemManage systemManage)  throws RemoteInvokeException{
    	systemService.updateSystemManage(systemManage);
   	}
}
