package com.eighth.housekeeping.proxy.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.annotation.RemoteMethod;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.web.FastJson;

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
     * @param orderType ONLINE_PAYED已支付|NOT_PAY未支付|ALL全部
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "memberId","orderType","page" })
    OpenPage<AuntOrder> findOrderList(String memberId,String orderType,OpenPage<AuntOrder> page)throws RemoteInvokeException;

    /**
     * 获取用户订单列表(阿姨端)
     * @param auntId
     * @param orderType ONLINE_PAYED已支付|NOT_PAY未支付|ALL全部
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "auntId","orderType","page" })
    OpenPage<AuntOrder> findAuntOrderList(String auntId,String orderType,OpenPage<AuntOrder> page)throws RemoteInvokeException;

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

    /**
     * 批量删除订单
     * @param memberId 用户ID
     * @param orderIds 订单ID数组
     * @return SUCCESS成功，FAIL失败
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "memberId","orderIds" })
    String deleteOrderBatch(String memberId,String... orderIds)throws RemoteInvokeException;

    /**
     * 查询相应类型订单数量
     * @param memberId 会员ID
     * @param orderType PAYED已支付|UNPAY未支付|ALL全部
     * @return
     * @throws RemoteInvokeException
     */
    @RemoteMethod(methodVarNames={ "memberId","orderType" })
    int findOrderCountsByMemberIdAndType(String memberId,String orderType)throws RemoteInvokeException;
    
    
    OpenPage<AuntOrder> findAuntOrderListByWeb(String auntId,String corpId, String contactWay,String auntNo, OpenPage<AuntOrder> page)throws RemoteInvokeException;
    void deleteOrderByOrderId(String orderId) throws RemoteInvokeException;
    
    void updateOrderByOrderNo(String orderNo,String orderStatus);
    
    AuntOrder findOrderByOrderNo(String orderNo);
    
     List<AuntOrder> getAllAuntOrder();
     
     void updateUseCouponCount(String orderId,String useCouponCount);

}
