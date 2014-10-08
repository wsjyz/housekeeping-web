package com.eighth.housekeeping.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.eighth.housekeeping.domain.APKVersion;
import com.eighth.housekeeping.domain.FeedBack;
import com.eighth.housekeeping.domain.ImageObj;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.domain.SystemManage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.SystemService;
import com.eighth.housekeeping.proxy.service.UserService;
import com.eighth.housekeeping.utils.CommonStringUtils;
import com.eighth.housekeeping.web.FastJson;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/SystemService")
public class SystemServiceController {
    @Autowired
    SystemService systemService;
    @Autowired
    UserService userService;
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
    @ResponseBody
    @RequestMapping(value = "/findFeedBack")
    public OpenPage<FeedBack> findFeedBack(@RequestParam String userName,@FastJson OpenPage page) {
     
        try {
        	page = systemService.findFeedBack(userName,page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }  
    @ResponseBody
   	@RequestMapping(value = "/deleteFeedWeb")
   	public void deleteFeedWeb(@RequestParam String feedId)  throws RemoteInvokeException{
    	systemService.deleteFeedWeb(feedId);
   	}
	@RequestMapping(value = "/tofeedBack")
	public String tofeedBack()  throws RemoteInvokeException{
		return "data-Manage/feedback";
	
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/saveAppLogoImage")
	public String saveAppLogoImage(MultipartHttpServletRequest request,
			HttpServletResponse response, @RequestParam String objId,
			@RequestParam String objType,@RequestParam String imageId) throws RemoteInvokeException {
		String name = CommonStringUtils.genPK();
		String path = request.getSession().getServletContext()
				.getRealPath("/WEB-INF/images/" + objType.toLowerCase());
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		int month = cal.get(Calendar.MONTH) + 1;
		String fileName = name + ".jpg";
		path += "/" + month;
		MultipartFile file = request.getFile("file");
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
			if(StringUtils.isNotEmpty(imageId)){
				userService.deleteImageObjByImageId(imageId);
			}
			ImageObj imageObj = new ImageObj();
			imageObj.setImageId(name);
			imageObj.setImageType(objType);
			imageObj.setObjId(objId);
			imageObj.setOptTime(sdf.format(time));
			userService.saveImageObj(imageObj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/" + month + "/" + fileName;
	}

}
