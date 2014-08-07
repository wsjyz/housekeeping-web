package com.eighth.housekeeping.controller;

import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.MemberInfo;
import com.eighth.housekeeping.domain.VerifyCode;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.AuntService;
import com.eighth.housekeeping.proxy.service.UserService;
import com.eighth.housekeeping.utils.JsonStatus;
import com.eighth.housekeeping.web.FastJson;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/UserService")
public class UserServiceController {
    @Autowired
    UserService userService;

    @Autowired
    AuntService auntService;
    @ResponseBody
    @RequestMapping(value = "/add")
    public MemberInfo add(@FastJson MemberInfo userInfo) {
        MemberInfo memberInfo = null;
        try {
            memberInfo = userService.add(userInfo);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return memberInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/obtainVerifyCode")
    public VerifyCode obtainVerifyCode(@RequestParam String mobile){
        VerifyCode verifyCode = null;
        try {
            verifyCode = userService.obtainVerifyCode(mobile);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return verifyCode;
    }

    @ResponseBody
    @RequestMapping(value = "/checkVerifyCode")
    public String checkVerifyCode(@FastJson VerifyCode code) {
        String string = null;
        try {
            string = userService.checkVerifyCode(code);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return string;
    }

    @ResponseBody
    @RequestMapping(value = "/modifyMemberInfo")
    public String modifyMemberInfo(@FastJson MemberInfo userInfo) {
        String string = null;
        try {
            string = userService.modifyMemberInfo(userInfo);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return string;
    }

    @ResponseBody
    @RequestMapping(value = "/modifyPushAuntInfo")
    public MemberInfo modifyPushAuntInfo(@RequestParam String memberId, @RequestParam int days) {
        MemberInfo memberInfo = null;
        try {
            memberInfo = userService.modifyPushAuntInfo(memberId, days);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return memberInfo;
    }
    @ResponseBody
    @RequestMapping(value = "/findMemberByMemberId")
    public MemberInfo findMemberByMemberId(String memberId)throws RemoteInvokeException{
        if(StringUtils.isNotBlank(memberId)){
            return  userService.findMemberByMemberId(memberId);
        }
        return null;
    }
    @RequestMapping(value = "/toLogin")
    public String toLogin(){
    	return "login";
    }
    @ResponseBody
    @RequestMapping(value = "/loginIng")
    public JsonStatus loginIng(@RequestParam String mobile,@RequestParam String password){
    	JsonStatus jsonStatus=new JsonStatus();
    	if("ADMIN".equals(mobile) && "hw".equals(password)){
    		jsonStatus.setSeccuss(true);
    		jsonStatus.setUrl("${request.contextPath}/UserService/toIndex");
    	}else{
    		AuntInfo auntInfo=new AuntInfo();  
    		 try {
    			 auntInfo = auntService.login(mobile, password);
             } catch (RemoteInvokeException e) {
                 e.printStackTrace();
             }
			jsonStatus.setSeccuss(false);
    		jsonStatus.setUrl("${request.contextPath}/UserService/toLogin");
    	}
    	return jsonStatus;
    }
    
    @RequestMapping(value = "/toIndex")
    public String toIndex(){
    	return "index";
    }
    @RequestMapping(value = "/toInstitution")
	public String toInstitution()  throws RemoteInvokeException{
		return "institution/institution";
	}
    @RequestMapping(value = "/toMember")
   	public String toMember()  throws RemoteInvokeException{
   		return "member/member";
   	}
   	
}
