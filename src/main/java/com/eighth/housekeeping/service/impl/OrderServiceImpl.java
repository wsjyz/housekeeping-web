package com.eighth.housekeeping.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.eighth.housekeeping.dao.CorpDAO;
import com.eighth.housekeeping.dao.OrderDAO;
import com.eighth.housekeeping.dao.SystemDAO;
import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.Corp;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.domain.SystemManage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.AuntService;
import com.eighth.housekeeping.proxy.service.OrderService;
import com.eighth.housekeeping.utils.CommonStringUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Created by dam on 2014/7/24.
 */
@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    AuntService auntService;
    @Autowired
    CorpDAO corpDao;
    @Autowired
    SystemDAO systemDAO;

    @Override
    public AuntOrder saveUserOrder(AuntOrder order) throws RemoteInvokeException {
        order.setOrderId(CommonStringUtils.genPK());
        String orderStatus = order.getOrderStatus();
        if(StringUtils.isBlank(orderStatus)){
            order.setOrderStatus("NOT_PAY");
        }
        if(order.getOrderUse() != null){
            BigDecimal price = new BigDecimal(0);
            SystemManage systemManage = systemDAO.findSystemManage();
            if(order.getOrderUse().equals("HOURLY_WORKER")){//小时工
                AuntInfo auntInfo = auntService.findAuntByIdForAunt(order.getAuntId());
                BigDecimal auntPrice = auntInfo.getPrice();
                if(auntPrice==null){
                	auntPrice=systemManage.getHourlyUnitPrice();
                }
                price = auntPrice.multiply(new BigDecimal(order.getWorkLength()));
                order.setUnitPrice(auntPrice);
            }else{//新居开荒
                price = systemManage.getNewHouseUnitPrice().multiply(new BigDecimal(order.getFloorSpace()));
            }

            BigDecimal couponPrice = systemManage.getCouponUnitPrice()
                    .multiply(new BigDecimal(order.getUseCouponCount()));

            order.setTotalPrice(price);
            order.setActualPrice(price.subtract(couponPrice));
        }
        orderDAO.saveUserOrder(order);
        return order;
    }

    @Override
    public String payOrder(String memberId, String orderId) throws RemoteInvokeException {
        return null;
    }

    @Override
    public OpenPage<AuntOrder> findOrderList(String memberId, String orderType,OpenPage<AuntOrder> page) throws RemoteInvokeException {
    	page= orderDAO.findOrderList(memberId,orderType,page);
    	if(!CollectionUtils.isEmpty(page.getRows())){
    		for (AuntOrder auntOrder : page.getRows()) {
                if(auntOrder != null && StringUtils.isNotEmpty(auntOrder.getAuntId())){
                    AuntInfo auntInfo = auntService.findAuntByIdForAunt(auntOrder.getAuntId());
                    auntOrder.setAuntInfo(auntInfo);
                }

			}
    	}
    	return page;
    }

    @Override
    public OpenPage<AuntOrder> findAuntOrderList(String auntId, String orderType, OpenPage<AuntOrder> page) throws RemoteInvokeException {
        return orderDAO.findAuntOrderList(auntId,orderType,page);
    }

    @Override
    public AuntOrder findOrderById(String orderId) throws RemoteInvokeException {
        return orderDAO.findOrderById(orderId);
    }

    @Override
    public String deleteOrder(String memberId, String orderId) throws RemoteInvokeException {
        orderDAO.deleteOrder(memberId,orderId);
        return "SUCCESS";
    }

    @Override
    public String deleteOrderBatch(String memberId, String... orderIds) throws RemoteInvokeException {
        if(orderIds != null && orderIds.length>0){
            orderDAO.deleteOrderBatch(memberId,orderIds);
            return "SUCCESS";
        }
        return "FAIL";
    }

    @Override
    public int findOrderCountsByMemberIdAndType(String memberId,String orderType)throws RemoteInvokeException {
        return orderDAO.findOrderCountsByMemberIdAndType(memberId,orderType);
    } 

	@Override
	public OpenPage<AuntOrder> findAuntOrderListByWeb(String auntId,String corpId,
			String corpName,String auntNo, OpenPage<AuntOrder> page)
			throws RemoteInvokeException {
		Corp corp =new Corp();
		if(StringUtils.isNotEmpty(corpName)){
			corp=corpDao.getCorpByName(corpName);
		}
		String newCorpId="";
		if(StringUtils.isNotEmpty(corp.getCorpId())){
			 newCorpId=corp.getCorpId();
		}
		if(StringUtils.isNotEmpty(corpId)){
			newCorpId=corpId;
		}
		page= orderDAO.findAuntOrderListByWeb(auntId,newCorpId, null,auntNo, page);
		if (!CollectionUtils.isEmpty(page.getRows())) {
			for (AuntOrder auntOrder : page.getRows()) {
				if (StringUtils.isNotEmpty(auntOrder.getAuntId())) {
					AuntInfo auntInfo =auntService.findAuntByIdForAunt(auntOrder.getAuntId());
					if(auntInfo!=null){
						auntOrder.setAuntName(auntInfo.getUserName());
						if (StringUtils.isNotEmpty(auntInfo.getCorpId())) {
							corp=corpDao.findCorpId(auntInfo.getCorpId());
						}
					}
				}
				if(corp!=null && StringUtils.isNotEmpty(corp.getCorpName())){
					BigDecimal totalPrice = auntOrder.getTotalPrice();
					if(totalPrice!=null){
						if(!"NEW_HOUSE".equals(auntOrder.getOrderUse())){
							BigDecimal multiply=new BigDecimal(0);
							if(StringUtils.isNotEmpty(corpId) && !"ADMIN".equals(corpId)){
								multiply = totalPrice.divide(new BigDecimal(30),BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(2));
							}else{
								 multiply = totalPrice.divide(new BigDecimal(30),BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(3));
							}
							auntOrder.setProfit(multiply.toString());
						}else{
							auntOrder.setProfit(totalPrice.toString());
						}
					}
					BigDecimal actualPrice = auntOrder.getActualPrice();
					if(actualPrice!=null){
						if(StringUtils.isNotEmpty(corpId) && !"ADMIN".equals(corpId)){
							actualPrice = actualPrice.divide(new BigDecimal(30),BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(28));
						}
						auntOrder.setActualPrice(actualPrice);
					}
					if("ADMIN".equals(corp.getCorpName())){
						auntOrder.setCorpName("上海居优家政服务有限公司");
					}else{
						auntOrder.setCorpName(corp.getCorpName());
					}
				}
			}
		}
		return page;
	}
	@Override
	public void deleteOrderByOrderId(String orderId) throws RemoteInvokeException {
	
		 orderDAO.deleteOrderByOrderId(orderId);
	}

	@Override
	public void updateOrderByOrderNo(String orderNo, String orderStatus) {
		orderDAO.updateOrderByOrderNo(orderNo,orderStatus);
		
	}

	@Override
	public AuntOrder findOrderByOrderNo(String orderNo) {
		return orderDAO.findOrderByOrderNo(orderNo);
	}

	@Override
	public List<AuntOrder> getAllAuntOrder() {
		return orderDAO.getAllAuntOrder();
	}

	@Override
	public void updateUseCouponCount(String orderId, String useCouponCount) {
		 orderDAO.updateUseCouponCount(orderId,useCouponCount);

	}
}
