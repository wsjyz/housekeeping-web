package com.eighth.housekeeping.proxy.service;

import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

/**
 * 订单服务
 * Created by dam on 2014/7/4.
 */
public interface OrderService {

    /**
     * 新增订单
     * @return
     * @throws RemoteInvokeException
     */
    AuntOrder addUserOrder(AuntOrder order)throws RemoteInvokeException;
}
