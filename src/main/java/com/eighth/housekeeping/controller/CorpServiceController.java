package com.eighth.housekeeping.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	@RequestMapping(value = "/toInstitutionAdd")
	public String toInstitutionAdd()  throws RemoteInvokeException{
		return "institution/institution-add";
	}
	@RequestMapping(value = "/toInstitutionView")
	public ModelAndView toInstitutionView(@RequestParam  String corpId)  throws RemoteInvokeException{
		Corp corp = corpService.findCorpId(corpId);
	    ModelAndView view = new ModelAndView();
        view.setViewName("institution/institution-view");
        Map<String, Object> model = view.getModel();
        model.put("corp",corp);
		return view;
	}
	@RequestMapping(value = "/toInstitutionEdit")
	public ModelAndView toInstitutionEdit(@RequestParam  String corpId)  throws RemoteInvokeException{
		Corp corp = corpService.findCorpId(corpId);
	    ModelAndView view = new ModelAndView();
        view.setViewName("institution/institution-edit");
        Map<String, Object> model = view.getModel();
        model.put("corp",corp);
		return view;
	}
	@ResponseBody
	@RequestMapping(value = "/saveCorpWeb")
	public void saveCorpWeb(@FastJson Corp corp)  throws RemoteInvokeException{
			if(StringUtils.isEmpty(corp.getCorpId())){
				corpService.saveCorp(corp);
			}else{
				corpService.updateCorp(corp);

			}
	}
	@ResponseBody
	@RequestMapping(value = "/deleteCorpWeb")
	public void deleteCorpWeb(@RequestParam  String corpId)  throws RemoteInvokeException{
		 corpService.deleteCorp(corpId);
	}
	@ResponseBody
	@RequestMapping(value = "/disableCorp")
	public void disableCorp(@RequestParam  String corpId)  throws RemoteInvokeException{
		Corp corp = corpService.findCorpId(corpId);
		corp.setStatus("NOT_ACTIVE");
		corpService.updateCorp(corp);
	}
}
