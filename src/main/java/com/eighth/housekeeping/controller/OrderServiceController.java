package com.eighth.housekeeping.controller;

import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.OrderService;
import com.eighth.housekeeping.web.FastJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/OrderService")
public class OrderServiceController {
    @Autowired
    OrderService orderService;

    @ResponseBody
    @RequestMapping(value = "/saveUserOrder")
    public AuntOrder saveUserOrder(@FastJson AuntOrder order) {
        AuntOrder auntOrder = null;
        try {
            auntOrder = orderService.saveUserOrder(order);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return auntOrder;
    }

    @ResponseBody
    @RequestMapping(value = "/payOrder")
    public String payOrder(@RequestParam String memberId, @RequestParam String orderId) {
        String string = null;
        try {
            string = orderService.payOrder(memberId, orderId);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return string;
    }

    @ResponseBody
    @RequestMapping(value = "/findOrderList")
    public OpenPage<AuntOrder> findOrderList(@RequestParam String memberId, @RequestParam String orderType,@FastJson OpenPage<AuntOrder> page) {
        OpenPage<AuntOrder> openPage = null;
        try {
            openPage = orderService.findOrderList(memberId, orderType,page);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return openPage;
    }
    @ResponseBody
    @RequestMapping(value = "/findAuntOrderList")
    OpenPage<AuntOrder> findAuntOrderList(@RequestParam String auntId,@RequestParam String orderType,@FastJson OpenPage<AuntOrder> page){
        OpenPage<AuntOrder> openPage = null;
        try {
            openPage = orderService.findAuntOrderList(auntId, orderType,page);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return openPage;
    }

    @ResponseBody
    @RequestMapping(value = "/findOrderById")
    public AuntOrder findOrderById(@RequestParam String orderId) {
        AuntOrder auntOrder = null;
        try {
            auntOrder = orderService.findOrderById(orderId);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return auntOrder;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteOrder")
    public String deleteOrder(@RequestParam String memberId, @RequestParam String orderId) {
        String string = null;
        try {
            string = orderService.deleteOrder(memberId, orderId);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return string;
    }
    @ResponseBody
    @RequestMapping(value = "/deleteOrderBatch")
    String deleteOrderBatch(@RequestParam String memberId, @FastJson String... orderIds){
        String string = null;
        try {
            string = orderService.deleteOrderBatch(memberId, orderIds);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return string;
    }
    @ResponseBody
    @RequestMapping(value = "/findOrderCountsByMemberIdAndType")
    int findOrderCountsByMemberIdAndType(@RequestParam String memberId,@RequestParam String orderType){
        try {
            return orderService.findOrderCountsByMemberIdAndType(memberId,orderType);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/findAuntOrderListByWeb")
    OpenPage<AuntOrder> findAuntOrderListByWeb(@RequestParam String auntId,@RequestParam String contactWay,@FastJson OpenPage<AuntOrder> page){
        OpenPage<AuntOrder> openPage = null;
        try {
            openPage = orderService.findAuntOrderListByWeb(auntId, contactWay,page);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        return openPage;
    }
    @ResponseBody
    @RequestMapping(value = "/deleteOrderByOrderId")
   public void deleteOrderByOrderId(@RequestParam String orderId){
    	try {
			orderService.deleteOrderByOrderId(orderId);
		} catch (RemoteInvokeException e) {
			e.printStackTrace();
		}
    }
}
