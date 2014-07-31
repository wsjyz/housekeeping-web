package com.eighth.housekeeping.proxy.service;

import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.annotation.RemoteMethod;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

/**
 * 订单服务
 * Created by dam on 2014/7/4.
 */
public interface OrderService {

    /**
     * 新增|修改订单
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "order" })
    AuntOrder saveUserOrder(AuntOrder order)throws RemoteInvokeException;

    /**
     * 支付订单
     * @param memberId 用户ID
     * @param orderId 订单ID
     * @return SUCCESS成功，FAIL失败
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={"memberId", "orderId" })
    String payOrder(String memberId,String orderId)throws RemoteInvokeException;

    /**
     * 获取用户订单列表
     * @param memberId
     * @param orderType PAYED已支付|UNPAY未支付|ALL全部
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "memberId","orderType","page" })
    OpenPage<AuntOrder> findOrderList(String memberId,String orderType,OpenPage<AuntOrder> page)throws RemoteInvokeException;

    /**
     * 获取订单详情
     * @param orderId
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "orderId" })
    AuntOrder findOrderById(String orderId)throws RemoteInvokeException;

    /**
     * 删除订单
     * @param memberId 用户ID
     * @param orderId 订单ID
     * @return SUCCESS成功，FAIL失败
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "memberId","orderId" })
    String deleteOrder(String memberId,String orderId)throws RemoteInvokeException;
}
