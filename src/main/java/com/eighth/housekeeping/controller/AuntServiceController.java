package com.eighth.housekeeping.controller;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.AuntWorkCase;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.domain.SignInfo;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.AuntService;
import com.eighth.housekeeping.proxy.service.AuntWorkCaseService;
import com.eighth.housekeeping.proxy.service.SignInfoService;
import com.eighth.housekeeping.web.FastJson;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/AuntService")
public class AuntServiceController {
    @Autowired
    AuntService auntService;
    @Autowired
    AuntWorkCaseService auntWorkCaseService;
    
    @Autowired
    SignInfoService signInfoService;

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
    @ResponseBody
    @RequestMapping(value = "/searchAuntByCondition")
    public OpenPage<AuntInfo> searchAuntByCondition(@FastJson AuntInfo auntInfo,@FastJson OpenPage<AuntInfo> page){
        OpenPage<AuntInfo> auntPage = new OpenPage<AuntInfo>();
        try {
            page = auntService.searchAuntByCondition(auntInfo,page);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return page;
    }
    @ResponseBody
    @RequestMapping(value = "/modifyAuntGeo")
    public String modifyAuntGeo(String auntId,double longitude,double latitude){
        String result = null;
        try {
            result = auntService.modifyAuntGeo(auntId,longitude,latitude);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    @RequestMapping(value = "/toAunt")
   	public String toAunt(@RequestParam  String auntId)  throws RemoteInvokeException{
    	if(StringUtils.isNotEmpty(auntId) && !"back".equals(auntId)){
   		 	auntService.deleteAunt(auntId);
    	}
   		return "aunt/aunt";
   	}
    @RequestMapping(value = "/toAuntAdd")
   	public ModelAndView toAuntAdd()  throws RemoteInvokeException{
    	AuntInfo auntInfo=new AuntInfo();
   		String info = auntService.addAuntInfo(auntInfo);
   		ModelAndView view = new ModelAndView();
		view.setViewName("aunt/aunt-add");
		Map<String, Object> model = view.getModel();
		model.put("auntId",info);
		return view;
   	}
	@RequestMapping(value = "/toAuntView")
	public ModelAndView toAuntView(@RequestParam  String auntId)  throws RemoteInvokeException{
		  AuntInfo auntInfo = auntService.findAuntByIdByWeb(auntId);
	    ModelAndView view = new ModelAndView();
        view.setViewName("aunt/aunt-view");
        Map<String, Object> model = view.getModel();
        model.put("auntInfo",auntInfo);
		return view;
	}
	@RequestMapping(value = "/toAuntEdit")
	public ModelAndView toAuntEdit(@RequestParam  String auntId)  throws RemoteInvokeException{
		AuntInfo auntInfo = auntService.findAuntByIdByWeb(auntId);
		ModelAndView view = new ModelAndView();
		view.setViewName("aunt/aunt-modify");
		Map<String, Object> model = view.getModel();
		model.put("auntInfo",auntInfo);
		return view;
	}
    @ResponseBody
    @RequestMapping(value = "/saveAunt")
    public void saveAunt(@FastJson AuntInfo auntInfo) throws RemoteInvokeException{
   		if(StringUtils.isEmpty(auntInfo.getAuntId())){
   			auntService.addAuntInfo(auntInfo);
   		}else{
   			auntService.updateAuntInfo(auntInfo);
   		}
   	}
		@ResponseBody
	@RequestMapping(value = "/deleteAuntWeb")
	public void deleteAuntWeb(@RequestParam  String auntId)  throws RemoteInvokeException{
		 auntService.deleteAunt(auntId);
	}
	@ResponseBody
	@RequestMapping(value = "/disableAunt")
	public void disableAunt(@RequestParam  String auntId)  throws RemoteInvokeException{
		AuntInfo auntInfo = auntService.findAuntByIdForMember(auntId);
		auntInfo.setStatus("NOT_ACTIVE");
		auntService.updateAuntInfo(auntInfo);
	}
	@RequestMapping(value = "/toCaseView")
	public ModelAndView toCaseView(@RequestParam  String caseId,@RequestParam  String auntId)  throws RemoteInvokeException{
		AuntWorkCase auntWorkCase = auntWorkCaseService.findCaseById(caseId);
		ModelAndView view = new ModelAndView();
		view.setViewName("aunt/case-cleaning-view");
		Map<String, Object> model = view.getModel();
		model.put("auntWorkCase",auntWorkCase);
		model.put("auntId",auntId);
		return view;
	}
	@RequestMapping(value = "/toCaseEdit")
	public ModelAndView toCaseEdit(@RequestParam  String caseId,@RequestParam  String auntId)  throws RemoteInvokeException{
		AuntWorkCase auntWorkCase =new AuntWorkCase();
		ModelAndView view = new ModelAndView();
		if (StringUtils.isNotEmpty(caseId)) {
			auntWorkCase= auntWorkCaseService.findCaseById(caseId);
		}else{
			auntWorkCase.setAuntId(auntId);
		}
		Map<String, Object> model = view.getModel();
		model.put("auntWorkCase",auntWorkCase);
		view.setViewName("aunt/case-cleaning-modify");
		return view;
	}
	  @ResponseBody
    @RequestMapping(value = "/searchAuntByWeb")
    public OpenPage<AuntInfo> searchAuntByWeb(@RequestParam String userName,@RequestParam String mobile, @FastJson OpenPage<AuntInfo> page){
        try {
        	page = auntService.searchAuntByWeb(userName,mobile,page);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return page;
    }
	  
	@ResponseBody
    @RequestMapping(value = "/addCase")
    public void addCase(@FastJson AuntWorkCase auntWorkCase) throws RemoteInvokeException{
		if(StringUtils.isEmpty(auntWorkCase.getCaseId())){
			auntWorkCaseService.addWorkCase(auntWorkCase);
		}else{
			auntWorkCaseService.updateWorkCase(auntWorkCase);
		}
   	}
	
	@RequestMapping(value = "/toOrder")
	public ModelAndView toOrder(@RequestParam  String auntId)  throws RemoteInvokeException{
		ModelAndView view =new ModelAndView();
		Map<String, Object> model = view.getModel();
		model.put("auntId",auntId);
		view.setViewName("aunt/aunt-bill-record");
		return view;
	}
	@RequestMapping(value = "/toDiscuss")
	public ModelAndView toDiscuss(@RequestParam  String auntId)  throws RemoteInvokeException{
		ModelAndView view =new ModelAndView();
		Map<String, Object> model = view.getModel();
		model.put("auntId",auntId);
		view.setViewName("aunt/aunt-comments");
		return view;
	}
	@RequestMapping(value = "/toSignManagement")
	public ModelAndView toSignManagement(@RequestParam  String auntId)  throws RemoteInvokeException{
		List<SignInfo> list = signInfoService.getListByAuntId(auntId);
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat year=new SimpleDateFormat("yyyy");
		SimpleDateFormat month=new SimpleDateFormat("MM");
		ModelAndView view =new ModelAndView();
		Map<String, Object> model = view.getModel();
		model.put("auntId",auntId);
		view.setViewName("aunt/aunt-registration");
		return view;
	}
}
