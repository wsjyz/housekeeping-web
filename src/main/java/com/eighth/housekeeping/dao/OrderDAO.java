package com.eighth.housekeeping.dao;

import java.util.List;

import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;

/**
 * Created by dam on 2014/7/29.
 */
public interface OrderDAO {

    void saveUserOrder(AuntOrder order);

    OpenPage<AuntOrder> findOrderList(String memberId, String orderType,OpenPage<AuntOrder> page);

    OpenPage<AuntOrder> findAuntOrderList(String auntId, String orderType, OpenPage<AuntOrder> page);

    AuntOrder findOrderById(String orderId);

    void deleteOrder(String memberId, String orderId);
    List<AuntOrder> getListByMemberId(String memberId,String auntId);
    void deleteOrderBatch(String memberId, String... orderIds);

    int findOrderCountsByMemberIdAndType(String memberId,String orderType);
    
    OpenPage<AuntOrder> findAuntOrderListByWeb( String auntId,String corpId, String contactWay,String auntNo, OpenPage<AuntOrder> page);
    void deleteOrderByOrderId( String orderId);
    
    void updateOrderByOrderNo(String orderNo,String orderStatus);
    AuntOrder findOrderByOrderNo(String orderNo);
    
    List<AuntOrder> getAllAuntOrder();

	void updateUseCouponCount(String orderId, String useCouponCount);


}
