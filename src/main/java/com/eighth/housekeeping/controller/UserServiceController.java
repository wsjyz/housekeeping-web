package com.eighth.housekeeping.controller;

import com.eighth.housekeeping.domain.MemberInfo;
import com.eighth.housekeeping.domain.VerifyCode;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.UserService;
import com.eighth.housekeeping.web.FastJson;
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

    @ResponseBody
    @RequestMapping(value = "/login")
    public MemberInfo login(@RequestParam String mobile) {
        MemberInfo memberInfo = null;
        try {
            memberInfo = userService.login(mobile);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return memberInfo;
    }

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
    public VerifyCode obtainVerifyCode(){
        VerifyCode verifyCode = null;
        try {
            verifyCode = userService.obtainVerifyCode();
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return verifyCode;
    }

    @ResponseBody
    @RequestMapping(value = "/checkVerifyCode")
    public String checkVerifyCode(@FastJson VerifyCode token) {
        String string = null;
        try {
            string = userService.checkVerifyCode(token);
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
}
