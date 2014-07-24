package com.eighth.housekeeping.service.impl;

import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * Created by dam on 2014/7/24.
 */
@Service("OrderService")
public class OrderServiceImpl implements OrderService {
    @Override
    public AuntOrder saveUserOrder(AuntOrder order) throws RemoteInvokeException {
        return null;
    }

    @Override
    public String payOrder(String memberId, String orderId) throws RemoteInvokeException {
        return null;
    }

    @Override
    public OpenPage<AuntOrder> findOrderList(String memberId, String orderType) throws RemoteInvokeException {
        return null;
    }

    @Override
    public AuntOrder findOrderById(String orderId) throws RemoteInvokeException {
        return null;
    }

    @Override
    public String deleteOrder(String memberId, String orderId) throws RemoteInvokeException {
        return null;
    }
}
