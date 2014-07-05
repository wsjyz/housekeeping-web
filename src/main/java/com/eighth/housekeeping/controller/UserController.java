package com.eighth.housekeeping.controller;

import com.eighth.housekeeping.domain.MemberInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dam on 2014/7/5.
 */
@Controller
@RequestMapping(value = "UserService")
public class UserController {


    @RequestMapping(value = "login")
    @ResponseBody
    public MemberInfo login(@RequestParam String mobile){
        MemberInfo memberInfo = new MemberInfo();
        //Exception
        memberInfo = null;
        memberInfo.setNickName("baby");
        System.out.println("mobile:"+mobile);

        return memberInfo;
    }
}
