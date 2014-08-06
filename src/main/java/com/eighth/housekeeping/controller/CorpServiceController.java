package com.eighth.housekeeping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eighth.housekeeping.domain.Corp;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.CorpService;
import com.eighth.housekeeping.web.FastJson;

@Controller
@RequestMapping(value = "/CorpService")
public class CorpServiceController {
    @Autowired
   CorpService corpService;
    
    @ResponseBody
    @RequestMapping(value = "/findCorpPage")
	public OpenPage<Corp> findCorpPage(@RequestParam String corpName,@RequestParam String loginName,
			@FastJson OpenPage page)  throws RemoteInvokeException{
		return corpService.findCorpPage(corpName, loginName, page);
	}

	@RequestMapping(value = "/saveCorp")
	public void saveCorp(@FastJson Corp corp)  throws RemoteInvokeException{
		corpService.saveCorp(corp);
	}
	@ResponseBody
	@RequestMapping(value = "/findCorpId")
	public Corp findCorpId(@FastJson String corpId)  throws RemoteInvokeException{
		return corpService.findCorpId(corpId);
	}

	@RequestMapping(value = "/updateCorp")
	public String updateCorp(@FastJson Corp corp)  throws RemoteInvokeException{
		return corpService.updateCorp(corp);
	}

	@RequestMapping(value = "/deleteCorp")
	public String deleteCorp(@RequestParam  String corpId)  throws RemoteInvokeException{
		return corpService.deleteCorp(corpId);
	}
	@RequestMapping(value = "/toInstitution")
	public String toInstitution()  throws RemoteInvokeException{
		return "institution/institution";
	}
	
}
