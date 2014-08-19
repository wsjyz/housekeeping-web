package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.dao.OrderDAO;
import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.OrderService;
import com.eighth.housekeeping.utils.CommonStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dam on 2014/7/24.
 */
@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Override
    public AuntOrder saveUserOrder(AuntOrder order) throws RemoteInvokeException {
        order.setOrderId(CommonStringUtils.genPK());
        orderDAO.saveUserOrder(order);
        return order;
    }

    @Override
    public String payOrder(String memberId, String orderId) throws RemoteInvokeException {
        return null;
    }

    @Override
    public OpenPage<AuntOrder> findOrderList(String memberId, String orderType,OpenPage<AuntOrder> page) throws RemoteInvokeException {
        return orderDAO.findOrderList(memberId,orderType,page);
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
	public OpenPage<AuntOrder> findAuntOrderListByWeb(String auntId,
			String contactWay, OpenPage<AuntOrder> page)
			throws RemoteInvokeException {
		return orderDAO.findAuntOrderListByWeb(auntId, contactWay, page);
	}

	@Override
	public void deleteOrderByOrderId(String orderId) throws RemoteInvokeException {
	
		 orderDAO.deleteOrderByOrderId(orderId);
	}
}
