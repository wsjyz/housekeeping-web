package com.eighth.housekeeping.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.config.AlipayConfig;
import com.alipay.util.UtilDate;
import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.Corp;
import com.eighth.housekeeping.domain.ImageObj;
import com.eighth.housekeeping.domain.MemberInfo;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.VerifyCode;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.AuntService;
import com.eighth.housekeeping.proxy.service.CorpService;
import com.eighth.housekeeping.proxy.service.UserService;
import com.eighth.housekeeping.utils.ChangePassword;
import com.eighth.housekeeping.utils.CommonStringUtils;
import com.eighth.housekeeping.utils.Constants;
import com.eighth.housekeeping.utils.JsonStatus;
import com.eighth.housekeeping.utils.PayOrderJson;
import com.eighth.housekeeping.web.FastJson;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.security.Credential.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/UserService")
public class UserServiceController {
	@Autowired
	UserService userService;

	@Autowired
	CorpService corpService;
	@Autowired
	AuntService auntService;

	@ResponseBody
	@RequestMapping(value = "/findUserPage")
	public OpenPage<MemberInfo> findUserPage(@RequestParam String mobile,
			@RequestParam String nickName, @FastJson OpenPage page)
			throws RemoteInvokeException {
		return userService.findUserPage(mobile, nickName, page);
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
	public VerifyCode obtainVerifyCode(@RequestParam String mobile) {
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
	public MemberInfo modifyPushAuntInfo(@RequestParam String memberId,
			@RequestParam int days) {
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
	public MemberInfo findMemberByMemberId(String memberId)
			throws RemoteInvokeException {
		if (StringUtils.isNotBlank(memberId)) {
			return userService.findMemberByMemberId(memberId);
		}
		return null;
	}

	@RequestMapping(value = "/toLogin")
	public String toLogin() {
		return "login";
	}

	@ResponseBody
	@RequestMapping(value = "/loginIng")
	public JsonStatus loginIng(@RequestParam String mobile,
			@RequestParam String password) {
		JsonStatus jsonStatus = new JsonStatus();
		Corp corp = corpService.login(mobile, password);
		if (corp != null && StringUtils.isNotEmpty(corp.getCorpId())) {
			jsonStatus.setSuccess(true);
			jsonStatus.setUrl("/hw/UserService/toIndex?auntId="
					+ corp.getCorpId());
		} else {
			jsonStatus.setSuccess(false);
			jsonStatus.setUrl("/hw/UserService/toLogin");
		}
		return jsonStatus;
	}

	@ResponseBody
	@RequestMapping(value = "/toIndex")
	public ModelAndView toIndex(@RequestParam String auntId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		Map<String, Object> model = modelAndView.getModel();
		model.put("corpId", auntId);
		return modelAndView;
	}

	@RequestMapping(value = "/toAunt")
	public String toAunt() throws RemoteInvokeException {
		return "aunt/aunt";
	}

	@RequestMapping(value = "/toInstitution")
	public String toInstitution() throws RemoteInvokeException {
		return "institution/institution";
	}

	@RequestMapping(value = "/toMember")
	public String toMember() throws RemoteInvokeException {
		return "member/member";
	}

	@RequestMapping(value = "/toMemberAdd")
	public ModelAndView toMemberAdd() throws RemoteInvokeException {
		ModelAndView view = new ModelAndView();
		view.setViewName("member/member-add");
//		Map<String, Object> model = view.getModel();
//		List<Corp> list = corpService.corpList();
//		model.put("corpList", list);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/saveUserInfo")
	public void saveUserInfo(@FastJson MemberInfo userInfo) {
		try {
			if (StringUtils.isEmpty(userInfo.getUserId())) {
				userInfo.setStatus("ACTIVE");
				userService.add(userInfo);
			} else {
				userService.modifyMemberInfo(userInfo);
			}
		} catch (RemoteInvokeException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/toMemberView")
	public ModelAndView toMemberView(@RequestParam String memberId)
			throws RemoteInvokeException {
		MemberInfo userInfo = userService.findMemberByMemberIdWeb(memberId);
		ModelAndView view = new ModelAndView();
		view.setViewName("member/member-view");
		Map<String, Object> model = view.getModel();
//		List<Corp> list = corpService.corpList();
//		model.put("corpList", list);
		model.put("memberInfo", userInfo);
		return view;
	}

	@RequestMapping(value = "/toMemberEdit")
	public ModelAndView toMemberEdit(@RequestParam String memberId)
			throws RemoteInvokeException {
		MemberInfo userInfo = userService.findMemberByMemberIdWeb(memberId);
		ModelAndView view = new ModelAndView();
		view.setViewName("member/member-edit");
		Map<String, Object> model = view.getModel();
//		List<Corp> list = corpService.corpList();
//		model.put("corpList", list);
		model.put("memberInfo", userInfo);
		return view;
	}

	@RequestMapping(value = "/toMemberComments")
	public String toMemberComments() throws RemoteInvokeException {
		return "member/member-comments";
	}

	@ResponseBody
	@RequestMapping(value = "/deleteMemberWeb")
	public void deleteMemberWeb(@RequestParam String memberId)
			throws RemoteInvokeException {
		userService.deleteByMemberId(memberId);
	}

	@ResponseBody
	@RequestMapping(value = "/disableMember")
	public void disableMember(@RequestParam String memberId)
			throws RemoteInvokeException {
		MemberInfo userInfo = userService.findMemberByMemberId(memberId);
		userInfo.setStatus("NOT_ACTIVE");
		userService.modifyMemberInfo(userInfo);
	}

	@ResponseBody
	@RequestMapping(value = "/saveImageObj")
	public String saveImageObj(MultipartHttpServletRequest request,
			HttpServletResponse response, @RequestParam String objId,
			@RequestParam String objType) throws RemoteInvokeException {
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
			userService.deleteImageObj(objId, objType);
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

	
	@ResponseBody
	@RequestMapping(value = "/saveImageObjCase")
	public String saveImageObjCase(MultipartHttpServletRequest request,
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
	@ResponseBody
	@RequestMapping(value = "/toUpdatePassword")
	public ModelAndView toUpdatePassword(@RequestParam String corpId)
			throws RemoteInvokeException {
		ModelAndView view = new ModelAndView();
		view.setViewName("member/change-password");
		Map<String, Object> model = view.getModel();
		model.put("corpId", corpId);
		return view;

	}

	@ResponseBody
	@RequestMapping(value = "/changePassword")
	public void changePassword(@FastJson ChangePassword changePassword)
			throws RemoteInvokeException {
		String corpId = changePassword.getCorpId();
		if (StringUtils.isNotEmpty(corpId)) {
			Corp corp = corpService.findCorpId(corpId);
			corp.setPassword(changePassword.getNewPassword());
			corp.setOptTime(new Date().toString());
			corpService.updateCorp(corp);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkPassword")
	public String checkPassword(@RequestParam String corpId,@RequestParam String oldPassword)
			throws RemoteInvokeException {
		boolean a=false;
		if (StringUtils.isNotEmpty(corpId)) {
			Corp corp = corpService.findCorpId(corpId);
			if(corp!=null && oldPassword.equals(corp.getPassword())){
				a=true;
			}	
		}
		return "{\"valid\":"+a+"}";
	}

	
	
	@ResponseBody
	@RequestMapping("/toPayMentUserInfo")
	public String toPayMentUserInfo(@RequestParam String userId,@RequestParam String card,HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		try {
			StringBuffer requestURL = request.getRequestURL();
	        String requestURLPrefix = requestURL.substring(0,requestURL.indexOf("hw")+3);
	        BigDecimal money=new BigDecimal(0);
	        String count="0";
	        if(Constants.GOLD.equals(card)){
	        	money=new BigDecimal(400);
	        	count="50";
	        }else  if(Constants.WHITE_GOLD.equals(card)){
	        	money=new BigDecimal(1300);
	        	count="150";
	        }else  if(Constants.DIAMOND.equals(card)){
	        	money=new BigDecimal(3200);
	        	count="350";
	        }
			// 必填，须保证每次请求都是唯一
			// req_data详细信息
			// 卖家支付宝帐户
			String seller_email = new String("lvsiwei@ylbj.cn");
			// 必填
			// 商户订单号
			String out_trade_no = new String(UtilDate.getOrderNum());
			// 商户网站订单系统中唯一订单号，必填
			// 服务器异步通知页面路径
			String notify_url =requestURLPrefix+"UserService/toNotify?userId="
					+ userId+"&card="+card+"&count="+count;
			// 需http://格式的完整路径，不能加?id=123这类自定义参数

			// 页面跳转同步通知页面路径
			String call_back_url =requestURLPrefix+"UserService/tocallbackurl?userId="
					+ userId+"&card="+card+"&count="+count;
			// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
			// 必填

			sb.append("partner=\"");
			sb.append(AlipayConfig.partner);
			sb.append("\"&out_trade_no=\"");
			sb.append(out_trade_no);
			sb.append("\"&subject=\"");
			sb.append(out_trade_no);
			sb.append("\"&body=\"");
			sb.append(out_trade_no);
			sb.append("\"&total_fee=\"");
			sb.append(money.toString());
			sb.append("\"&notify_url=\"");

			// 网址需要做URL编码
			sb.append(URLDecoder.decode(notify_url,AlipayConfig.input_charset));
			sb.append("\"&service=\"mobile.securitypay.pay");
			sb.append("\"&_input_charset=\"UTF-8");
			sb.append("\"&return_url=\"");
			sb.append( URLDecoder.decode(call_back_url,AlipayConfig.input_charset));
			sb.append("\"&payment_type=\"1");
			sb.append("\"&seller_id=\"");
			sb.append(seller_email);
			sb.append("\"&it_b_pay=\"1m\"");

		} catch (Exception e) {
			e.printStackTrace();
		}
		String sbb=sb.toString().replace(" ","");
		return sbb;
	}
	
	@ResponseBody
	@RequestMapping("/toPayMentUserInfoJson")
	public PayOrderJson toPayMentUserInfoJson(@RequestParam String userId,@RequestParam String card,HttpServletRequest request) {
		PayOrderJson payOrderJson=new PayOrderJson();
		try {
			StringBuffer requestURL = request.getRequestURL();
	        String requestURLPrefix = requestURL.substring(0,requestURL.indexOf("hw")+3);
	        BigDecimal money=new BigDecimal(0);
	        String count="0";
	        if(Constants.GOLD.equals(card)){
	        	money=new BigDecimal(450);
	        	count="45";
	        }else  if(Constants.WHITE_GOLD.equals(card)){
	        	money=new BigDecimal(900);
	        	count="90";
	        }else  if(Constants.DIAMOND.equals(card)){
	        	money=new BigDecimal(1800);
	        	count="180";
	        }
			// 必填，须保证每次请求都是唯一
			// req_data详细信息
			// 卖家支付宝帐户
			String seller_email = new String("lvsiwei@ylbj.cn");
			// 必填
			// 商户订单号
			String out_trade_no = new String(UtilDate.getOrderNum());
			// 商户网站订单系统中唯一订单号，必填
			// 服务器异步通知页面路径
			String notify_url =requestURLPrefix+"UserService/toNotify?userId="
					+ userId+"&card="+card+"&count="+count;
			// 需http://格式的完整路径，不能加?id=123这类自定义参数

			// 页面跳转同步通知页面路径
			String call_back_url =requestURLPrefix+"UserService/tocallbackurl?userId="
					+ userId+"&card="+card+"&count="+count;
			// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
			// 必填

			payOrderJson.setPartner(AlipayConfig.partner);
			payOrderJson.setOut_trade_no(out_trade_no);
			payOrderJson.setSubject(out_trade_no);
			payOrderJson.setBody(out_trade_no);
			payOrderJson.setTotal_fee(money.toString());
			payOrderJson.setNotify_url(URLDecoder.decode(notify_url,AlipayConfig.input_charset));
			payOrderJson.setService("mobile.securitypay.pay");
			payOrderJson.set_input_charset("UTF-8");
			payOrderJson.setReturn_url(URLDecoder.decode(call_back_url,AlipayConfig.input_charset));
			payOrderJson.setPayment_type("1");
			payOrderJson.setSeller_id(seller_email);
			payOrderJson.setIt_b_pay("1m");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payOrderJson;
	}
	@RequestMapping("/toNotify")
	public void toNotify(@RequestParam String userId,@RequestParam String card,@RequestParam String count) {
		try {
			MemberInfo info=userService.findMemberByMemberId(userId);
			MemberInfo userInfo=new MemberInfo();
			userInfo.setUserId(userId);
			userInfo.setCard(card);
			int nowCount=Integer.parseInt(count)+info.getCouponCounts()==null?0:Integer.parseInt(info.getCouponCounts());
			userInfo.setCouponCounts(nowCount+"");
			if (userInfo != null) {
				try {
					userService.modifyMemberInfo(userInfo);
				} catch (RemoteInvokeException e) {
					e.printStackTrace();
				}
			}
		} catch (RemoteInvokeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@RequestMapping("/tocallbackurl")
	public void tocallbackurl(@RequestParam String userId,@RequestParam String card,@RequestParam String  count) {
		try {
			MemberInfo info=userService.findMemberByMemberId(userId);
			MemberInfo userInfo=new MemberInfo();
			userInfo.setUserId(userId);
			userInfo.setCard(card);
			int nowCount=Integer.parseInt(count)+info.getCouponCounts()==null?0:Integer.parseInt(info.getCouponCounts());
			userInfo.setCouponCounts(nowCount+"");
			if (userInfo != null) {
				try {
					userService.modifyMemberInfo(userInfo);
				} catch (RemoteInvokeException e) {
					e.printStackTrace();
				}
			}
		} catch (RemoteInvokeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
