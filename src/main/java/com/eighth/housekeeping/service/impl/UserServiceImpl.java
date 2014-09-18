package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.dao.ImageObjDAO;
import com.eighth.housekeeping.dao.OrderDAO;
import com.eighth.housekeeping.dao.UserDAO;
import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.ImageObj;
import com.eighth.housekeeping.domain.MemberInfo;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.VerifyCode;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.UserService;
import com.eighth.housekeeping.utils.CommonStringUtils;
import com.eighth.housekeeping.utils.Constants;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dam on 2014/7/24.
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    ImageObjDAO imageObjDAO;
    @Override
    public MemberInfo add(MemberInfo userInfo) throws RemoteInvokeException {
        if(StringUtils.isBlank(userInfo.getUserId())){
            userInfo.setUserId(CommonStringUtils.genPK());
        }
        userDAO.saveMember(userInfo);
        return userInfo;
    }

    @Override
    public VerifyCode obtainVerifyCode(String mobile) throws RemoteInvokeException {
        userDAO.deleteVerifyCode(mobile);
        VerifyCode code = new VerifyCode();
        code.setMobile(mobile);
        code.setToken(CommonStringUtils.gen4RandomKey());
        userDAO.saveVerifyCode(code);
        return code;
    }

    @Override
    public String checkVerifyCode(VerifyCode token) throws RemoteInvokeException {
        String result = "";
        if(token == null || StringUtils.isBlank(token.getMobile())
                || StringUtils.isBlank(token.getToken())){
            result = "FAULT";
        }else {
            VerifyCode code = userDAO.findVerifyCodeByMobile(token.getMobile());
            if(code == null){
                result = "FAULT";
            }else{
                Date currentDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date codeGenDate = null;
                try {
                    codeGenDate = sdf.parse(code.getOptTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long diffResult = (currentDate.getTime() - codeGenDate.getTime())/1000;
                if(diffResult > 30){
                    result = "PAST";
                }else if(token.getToken().equals(code.getToken())){
                    MemberInfo memberInfo = userDAO.findMemberByMobile(token.getMobile());
                    if(memberInfo == null){
                        String memberId = CommonStringUtils.genPK();
                        memberInfo = new MemberInfo();
                        memberInfo.setMobile(token.getMobile());
                        memberInfo.setUserId(memberId);
                        add(memberInfo);
                    }
                    result = memberInfo.getUserId();

                }else{
                    result = "FAULT";
                }
            }

        }

        return result;
    }

    @Override
    public String modifyMemberInfo(MemberInfo userInfo) throws RemoteInvokeException {
        if(StringUtils.isNotBlank(userInfo.getUserId())){
            return userDAO.modifyMemberInfo(userInfo);
        }
        return "FAIL";
    }

    @Override
    public MemberInfo modifyPushAuntInfo(String memberId, int days) throws RemoteInvokeException {
        if(StringUtils.isNotBlank(memberId)){
            MemberInfo memberInfo = userDAO.findMemberByMemberId(memberId);
            memberInfo.setPushAuntInfo(days);
            userDAO.modifyMemberInfo(memberInfo);
            return memberInfo;
        }
        return null;
    }

    @Override
    public MemberInfo findMemberByMemberId(String memberId) throws RemoteInvokeException {
    	MemberInfo memberInfo= userDAO.findMemberByMemberId(memberId);
    	List<ImageObj> imageList = imageObjDAO.findImageObjByObjIdAndType(memberId, Constants.PORTRAIT);
		if (!CollectionUtils.isEmpty(imageList)) {
			ImageObj imageObj=imageList.get(0);
			memberInfo.setImageObj(imageObj);
		}
		return memberInfo;
    }

	@Override
	public String deleteByMemberId(String memberId)
			throws RemoteInvokeException {
		return userDAO.deleteByMemberId(memberId);
	}

	@Override
	public MemberInfo findMemberByMemberIdWeb(String memberId)
			throws RemoteInvokeException {
		MemberInfo memberInfo = userDAO.findMemberByMemberId(memberId);
		List<ImageObj> imageList = imageObjDAO.findImageObjByObjIdAndType(memberId, Constants.PORTRAIT);
		if (!CollectionUtils.isEmpty(imageList)) {
			ImageObj imageObj=imageList.get(0);
			memberInfo.setImageObj(imageObj);
		}
		setyearOrMonthMoney(memberInfo);
		return memberInfo;
	}

	private void setyearOrMonthMoney( MemberInfo memberInfo) {
		String memberId=memberInfo.getUserId();
		List<AuntOrder> auntOrderList = orderDAO.getListByMemberId(memberId,null);
		BigDecimal monthMoney=new BigDecimal(0);
		BigDecimal yearMoney=new BigDecimal(0);
		BigDecimal sumMoney=new BigDecimal(0);
		int orderCount=0;
		int payedOrderCount=0;
		int notPayedOrderCount=0;
		BigDecimal notPayedOrderMoney=new BigDecimal(0);
		int couponUseCounts=0;
		if (!CollectionUtils.isEmpty(auntOrderList)) {
			Calendar cal=Calendar.getInstance();
			String year=cal.get(Calendar.YEAR)+"";
			String month=cal.get(Calendar.MONTH)+"";
			for (AuntOrder auntOrder : auntOrderList) {
				
				orderCount++;
				if (auntOrder.getUseCouponCount()>0) {
					couponUseCounts++;
				}
				if (Constants.ONLINE_PAYED.equals(auntOrder.getOrderStatus())) {
					payedOrderCount++;
					sumMoney=sumMoney.add(auntOrder.getActualPrice()==null?new BigDecimal(0):auntOrder.getActualPrice());
					if (StringUtils.isNotEmpty(auntOrder.getOptTime()) && auntOrder.getOptTime().contains("-")) {
						String[] time=auntOrder.getOptTime().split("-");
						String yearStr=time[0];
						String monthStr=time[1];
						if (yearStr.equals(year)) {
							yearMoney=yearMoney.add(auntOrder.getActualPrice()==null?new BigDecimal(0):auntOrder.getActualPrice());
						}
						if (monthStr.equals(month)) {
							monthMoney=monthMoney.add(auntOrder.getActualPrice()==null?new BigDecimal(0):auntOrder.getActualPrice());
						}
					}
				}else{
					notPayedOrderCount++;
					notPayedOrderMoney=notPayedOrderMoney.add(auntOrder.getActualPrice()==null?new BigDecimal(0):auntOrder.getActualPrice());
				}
			}
		}
		memberInfo.setMonthMoney(monthMoney);
		memberInfo.setYearMoney(yearMoney);
		memberInfo.setSumMoney(sumMoney);
		memberInfo.setOrderCount(orderCount);
		memberInfo.setPayedOrderCount(payedOrderCount);
		memberInfo.setNotPayedOrderCount(notPayedOrderCount);
		memberInfo.setNotPayedOrderMoney(notPayedOrderMoney);
		memberInfo.setCouponUseCounts(couponUseCounts);
	}

	@Override
	public OpenPage<MemberInfo> findUserPage(String mobile, String nickName,
			OpenPage page) throws RemoteInvokeException {
		page= userDAO.findUserPage(mobile, nickName, page);
		if (page!=null && !CollectionUtils.isEmpty(page.getRows())) {
			for (Object obj : page.getRows()) {
				MemberInfo memberInfo=(MemberInfo)obj;
				setyearOrMonthMoney(memberInfo);
			}
		}
		return page;
	}

	@Override
	public String saveImageObj(ImageObj imageObj) {
		return imageObjDAO.saveImageObj(imageObj);
	}

	@Override
	public void deleteImageObj(String objId, String objType) {
		 imageObjDAO.deleteImageObj(objId,objType);
	}

	@Override
	public void deleteImageObjByImageId(String imageId) {
		imageObjDAO.deleteImageObjByImageId(imageId);
		
	}


}
