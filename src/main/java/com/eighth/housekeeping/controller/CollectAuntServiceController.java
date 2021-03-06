package com.eighth.housekeeping.controller;

import com.eighth.housekeeping.domain.CollectAunt;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.CollectAuntService;
import com.eighth.housekeeping.web.FastJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/CollectAuntService")
public class CollectAuntServiceController {
    @Autowired
    CollectAuntService collectAuntService;

    @ResponseBody
    @RequestMapping(value = "/addCollect")
    public CollectAunt addCollect(@RequestParam String auntId, @RequestParam String memberId) {
        CollectAunt collectAunt = null;
        try {
            collectAunt = collectAuntService.addCollect(auntId, memberId);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return collectAunt;
    }

    @ResponseBody
    @RequestMapping(value = "/findCollectAuntList")
    public OpenPage<CollectAunt> findCollectAuntList(@RequestParam String userId,@FastJson OpenPage page) {
        OpenPage<CollectAunt> openPage = null;
        try {
            openPage = collectAuntService.findCollectAuntList(userId,page);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return openPage;
    }
    @ResponseBody
	@RequestMapping(value = "/deleteCollectAunt")
	public String deleteAuntCollectAunt(@RequestParam  String collectId)  throws RemoteInvokeException{
    	return collectAuntService.deleteCollectAunt(collectId);
	}
}
