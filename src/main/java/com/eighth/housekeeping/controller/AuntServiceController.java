package com.eighth.housekeeping.controller;

import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.AuntWorkCase;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.AuntService;
import com.eighth.housekeeping.web.FastJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/AuntService")
public class AuntServiceController {
    @Autowired
    AuntService auntService;

    @ResponseBody
    @RequestMapping(value = "/login")
    public AuntInfo login(@RequestParam String mobile, @RequestParam String password) {
        AuntInfo auntInfo = null;
        try {
            auntInfo = auntService.login(mobile, password);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return auntInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/findAuntByIdForAunt")
    public AuntInfo findAuntByIdForAunt(@RequestParam String auntId) {
        AuntInfo auntInfo = null;
        try {
            auntInfo = auntService.findAuntByIdForAunt(auntId);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return auntInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/findAuntByIdForMember")
    public AuntInfo findAuntByIdForMember(@RequestParam String auntId) {
        AuntInfo auntInfo = null;
        try {
            auntInfo = auntService.findAuntByIdForMember(auntId);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return auntInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/findCaseById")
    public AuntWorkCase findCaseById(@RequestParam String caseId) {
        AuntWorkCase auntWorkCase = null;
        try {
            auntWorkCase = auntService.findCaseById(caseId);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return auntWorkCase;
    }

    @ResponseBody
    @RequestMapping(value = "/findReviewByAuntId")
    public OpenPage<Review> findReviewByAuntId(@RequestParam String reviewTag, @RequestParam String auntId, @FastJson OpenPage page) {
        OpenPage<Review> openPage = null;
        try {
            openPage = auntService.findReviewByAuntId(reviewTag, auntId, page);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return openPage;
    }

    @ResponseBody
    @RequestMapping(value = "/resetPassword")
    public String resetPassword(@RequestParam String auntId, @RequestParam String oldPassword, @RequestParam String newPassword) {
        String string = null;
        try {
            string = auntService.resetPassword(auntId, oldPassword, newPassword);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return string;
    }
    
    @RequestMapping(value = "/toAunt")
   	public String toAunt()  throws RemoteInvokeException{
   		return "aunt/aunt";
   	}
}
