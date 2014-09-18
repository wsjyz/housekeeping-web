package com.eighth.housekeeping.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.eighth.housekeeping.dao.AuntDAO;
import com.eighth.housekeeping.dao.AuntWorkCaseDAO;
import com.eighth.housekeeping.dao.ImageObjDAO;
import com.eighth.housekeeping.dao.OrderDAO;
import com.eighth.housekeeping.dao.ReviewDAO;
import com.eighth.housekeeping.dao.SignInfoDAO;
import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.AuntWorkCase;
import com.eighth.housekeeping.domain.ImageObj;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.Review;
import com.eighth.housekeeping.domain.annotation.Column;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.AuntService;
import com.eighth.housekeeping.utils.CommonStringUtils;
import com.eighth.housekeeping.utils.Constants;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Created by dam on 2014/7/24.
 */
@Service("AuntService")
public class AuntServiceImpl implements AuntService {
	@Autowired
	private AuntDAO auntDAO;
	@Autowired
	private AuntWorkCaseDAO auntWorkCaseDAO;
	@Autowired
	private ReviewDAO reviewDAO;
	@Autowired
	ImageObjDAO imageObjDAO;
	@Autowired
	OrderDAO orderDAO;
	@Autowired
	SignInfoDAO signInfoDAO;

	@Override
	public AuntInfo login(String mobile, String password)
			throws RemoteInvokeException {
		String md5Psw = CommonStringUtils.getMD5(password.getBytes());
		AuntInfo auntInfo= auntDAO.findAuntByMobileAndPsw(mobile, md5Psw);
		if (StringUtils.isNotEmpty(auntInfo.getAuntId())) {
			List<ImageObj> imageList = imageObjDAO.findImageObjByObjIdAndType(
					auntInfo.getAuntId(), Constants.PORTRAIT);
			if (!CollectionUtils.isEmpty(imageList)) {
				ImageObj imageObj = imageList.get(0);
				auntInfo.setImageObj(imageObj);
			}
			 if(auntInfo!= null){
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            String today = sdf.format(new Date());
		            String[] todayStrArr = today.split("-");
		            Integer count = signInfoDAO.findAuntMonthSignCount(auntInfo.getAuntId(),todayStrArr[0],todayStrArr[1]);
		            auntInfo.setMonthOfSignCounts(count);
		        }
		}
		return auntInfo;
	}

	@Override
	public AuntInfo findAuntByIdForAunt(String auntId)
			throws RemoteInvokeException {
		AuntInfo auntInfo= auntDAO.findAuntByIdForAunt(auntId);
		if (StringUtils.isNotEmpty(auntInfo.getAuntId())) {
			List<ImageObj> imageList = imageObjDAO.findImageObjByObjIdAndType(
					auntInfo.getAuntId(), Constants.PORTRAIT);
			if (!CollectionUtils.isEmpty(imageList)) {
				ImageObj imageObj = imageList.get(0);
				auntInfo.setImageObj(imageObj);
			}
		}
		return auntInfo;
	}

	@Override
	public AuntInfo findAuntByIdForMember(String auntId)
			throws RemoteInvokeException {
		AuntInfo auntInfo= auntDAO.findAuntByIdForMember(auntId);
		if (StringUtils.isNotEmpty(auntInfo.getAuntId())) {
			List<ImageObj> imageList = imageObjDAO.findImageObjByObjIdAndType(
					auntInfo.getAuntId(), Constants.PORTRAIT);
			if (!CollectionUtils.isEmpty(imageList)) {
				ImageObj imageObj = imageList.get(0);
				auntInfo.setImageObj(imageObj);
			}
		}
        auntInfo.setBrowseCounts(auntInfo.getBrowseCounts()+1);
        auntDAO.updateAuntInfo(auntInfo);
		return auntInfo;
	}

	@Override
	public AuntWorkCase findCaseById(String caseId)
			throws RemoteInvokeException {
		return auntWorkCaseDAO.findCaseById(caseId);
	}

	@Override
	public OpenPage<Review> findReviewByAuntId(String reviewTag, String auntId,
			OpenPage page) throws RemoteInvokeException {
		return reviewDAO.findReviewByAuntId(reviewTag, auntId, page);
	}

	@Override
	public String resetPassword(String auntId, String oldPassword,
			String newPassword) throws RemoteInvokeException {
		return null;
	}

	@Override
	public String modifyAuntGeo(String auntId, double longitude, double latitude) {
		auntDAO.modifyAuntGeo(auntId, longitude, latitude);
		return "SUCCESS";
	}

	@Override
	public String addAuntInfo(AuntInfo auntInfo) {
		if(StringUtils.isEmpty(auntInfo.getAuntId())){
			auntInfo.setAuntId(CommonStringUtils.genPK());
		}
		return auntDAO.addAuntInfo(auntInfo);
	}

	@Override
	public String updateAuntInfo(AuntInfo auntInfo) {
		return auntDAO.updateAuntInfo(auntInfo);
	}

	@Override
	public String deleteAunt(String auntId) {
		imageObjDAO.deleteImageObj(auntId, Constants.PORTRAIT);
		List<AuntWorkCase> list = auntWorkCaseDAO.findCaseByAuntId(auntId);
		if (!CollectionUtils.isEmpty(list)) {
			for (AuntWorkCase auntWorkCase : list) {
				imageObjDAO.deleteImageObj(auntWorkCase.getCaseId(), Constants.WORKCASE);
			}
		}
		auntWorkCaseDAO.deleteWorkCase(auntId);
		return auntDAO.deleteAunt(auntId);
	}

	@Override
	public OpenPage<AuntInfo> searchAuntByCondition(AuntInfo auntInfo,
			OpenPage<AuntInfo> page) throws RemoteInvokeException {
		page= auntDAO.searchAuntByCondition(auntInfo, page);
		if(!CollectionUtils.isEmpty(page.getRows())){
			for (AuntInfo auntInfoTemp : page.getRows()) {
				if (StringUtils.isNotEmpty(auntInfoTemp.getAuntId())) {
					List<ImageObj> imageList = imageObjDAO.findImageObjByObjIdAndType(
							auntInfoTemp.getAuntId(), Constants.PORTRAIT);
					if (!CollectionUtils.isEmpty(imageList)) {
						ImageObj imageObj = imageList.get(0);
						auntInfoTemp.setImageObj(imageObj);
					}
				}
			}
		}
		return page;
	}

	@Override
	public AuntInfo findAuntByIdByWeb(String auntId)
			throws RemoteInvokeException {
		AuntInfo auntInfo = auntDAO.findAuntByIdForMember(auntId);
		List<ImageObj> imageList = imageObjDAO.findImageObjByObjIdAndType(
				auntId, Constants.PORTRAIT);
		if (!CollectionUtils.isEmpty(imageList)) {
			ImageObj imageObj = imageList.get(0);
			auntInfo.setImageObj(imageObj);
		}
		setMoneyByAuntInfo(auntInfo);
		return auntInfo;
	}

	private void setMoneyByAuntInfo(AuntInfo auntInfo) {
		String auntId = auntInfo.getAuntId();
		List<AuntOrder> auntOrderList = orderDAO
				.getListByMemberId(null, auntId);
		BigDecimal mothOfIncome = new BigDecimal(0);// 本月收入
		BigDecimal yearOfIncome = new BigDecimal(0);// 年度金额
		BigDecimal sumMoney = new BigDecimal(0);// 总金额
		int yearOfOrderCounts = 0;// 年度订单数
		int monthOfOrderCounts = 0;// 月度订单数
		int totalOrderCounts = 0;// 总订单数
		int payOrderCount = 0;
		int discussCount = 0;
		List<Review> reviewByAuntId = reviewDAO.getReviewByAuntId(auntId);
		if (CollectionUtils.isEmpty(reviewByAuntId)) {
			discussCount = reviewByAuntId.size();
		}
		if (!CollectionUtils.isEmpty(auntOrderList)) {
			Calendar cal = Calendar.getInstance();
			String year = cal.get(Calendar.YEAR) + "";
			String month = cal.get(Calendar.MONTH) + "";
			for (AuntOrder auntOrder : auntOrderList) {
				totalOrderCounts++;
				if (Constants.ONLINE_PAYED.equals(auntOrder.getOrderStatus())) {
					payOrderCount++;
					sumMoney = sumMoney
							.add(auntOrder.getActualPrice() == null ? new BigDecimal(
									0) : auntOrder.getActualPrice());
					if (StringUtils.isNotEmpty(auntOrder.getOptTime())
							&& auntOrder.getOptTime().contains("-")) {
						String[] time = auntOrder.getOptTime().split("-");
						String yearStr = time[0];
						String monthStr = time[1];
						if (yearStr.equals(year)) {
							yearOfIncome = yearOfIncome.add(auntOrder
									.getActualPrice() == null ? new BigDecimal(
									0) : auntOrder.getActualPrice());
						}
						if (monthStr.equals(month)) {
							mothOfIncome = mothOfIncome.add(auntOrder
									.getActualPrice() == null ? new BigDecimal(
									0) : auntOrder.getActualPrice());
						}
					}
				}
				if (StringUtils.isNotEmpty(auntOrder.getOptTime())
						&& auntOrder.getOptTime().contains("-")) {
					String[] time = auntOrder.getOptTime().split("-");
					String yearStr = time[0];
					String monthStr = time[1];
					if (yearStr.equals(year)) {
						yearOfOrderCounts++;
					}
					if (monthStr.equals(month)) {
						monthOfOrderCounts++;
						mothOfIncome = mothOfIncome.add(auntOrder
								.getActualPrice() == null ? new BigDecimal(0)
								: auntOrder.getActualPrice());
					}
				}
			}
		}
		auntInfo.setPayOrderCount(payOrderCount);
		auntInfo.setYearOfIncome(yearOfIncome);
		auntInfo.setMothOfIncome(mothOfIncome);
		auntInfo.setSumMoney(sumMoney);
		auntInfo.setTotalOrderCounts(totalOrderCounts);
		auntInfo.setMonthOfOrderCounts(monthOfOrderCounts);
		auntInfo.setYearOfOrderCounts(yearOfOrderCounts);
		auntInfo.setDiscussCount(discussCount);
	}

	@Override
	public OpenPage<AuntInfo> searchAuntByWeb(String corpId,String userName, String mobile,
			OpenPage<AuntInfo> page) throws RemoteInvokeException {
		page = auntDAO.searchAuntByWeb(corpId,userName, mobile, page);
		if (!CollectionUtils.isEmpty(page.getRows())) {
			for (AuntInfo auntInfo : page.getRows()) {
				setMoneyByAuntInfo(auntInfo);
			}
		}
		return page;
	}

	@Override
	public Boolean checkIdentityCard(String identityCard) {
		return auntDAO.checkIdentityCard(identityCard);
	}
}