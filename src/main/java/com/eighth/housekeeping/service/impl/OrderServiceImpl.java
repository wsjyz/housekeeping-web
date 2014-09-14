package com.eighth.housekeeping.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.eighth.housekeeping.dao.OrderDAO;
import com.eighth.housekeeping.dao.SystemDAO;
import com.eighth.housekeeping.domain.AuntInfo;
import com.eighth.housekeeping.domain.AuntOrder;
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
                if(auntOrder != null&&auntOrder.getAuntId() != null){
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
			String contactWay,String auntNo, OpenPage<AuntOrder> page)
			throws RemoteInvokeException {
		return orderDAO.findAuntOrderListByWeb(auntId,corpId, contactWay,auntNo, page);
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
}
