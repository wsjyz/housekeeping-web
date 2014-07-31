package com.eighth.housekeeping.dao;

import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.OpenPage;

/**
 * Created by dam on 2014/7/29.
 */
public interface OrderDAO {

    void saveUserOrder(AuntOrder order);

    OpenPage<AuntOrder> findOrderList(String memberId, String orderType,OpenPage<AuntOrder> page);

    AuntOrder findOrderById(String orderId);

    void deleteOrder(String memberId, String orderId);
}
