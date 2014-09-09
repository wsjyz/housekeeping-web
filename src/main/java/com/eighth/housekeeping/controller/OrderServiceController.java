package com.eighth.housekeeping.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alipay.util.UtilDate;
import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.OrderService;
import com.eighth.housekeeping.web.FastJson;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
            openPage = orderService.findAuntOrderListByWeb(auntId, contactWay,null,page);
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
    
    @RequestMapping("/payment")
    public ModelAndView toPay(@RequestParam String orderId) {
        ModelAndView view = new ModelAndView();
        AuntOrder auntOrder = null;
        try {
            auntOrder = orderService.findOrderById(orderId);
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }
        String orderNo=auntOrder.getOrderNo();
        view.addObject("WIDseller_email", "geassccvip@163.com");
        view.addObject("WIDout_trade_no",orderNo);
        view.addObject("WIDsubject",orderNo);
        view.addObject("WIDtotal_fee",auntOrder.getActualPrice());

        view.setViewName("payOrder/index");
        return view;
    }
	
	@RequestMapping("/toAlipayapi")
    public ModelAndView toAlipayapi(@RequestParam String WIDseller_email,@RequestParam String WIDout_trade_no,@RequestParam String WIDsubject	,@RequestParam String WIDtotal_fee) {
        ModelAndView view = new ModelAndView();
        view.addObject("WIDseller_email", WIDseller_email);
        view.addObject("WIDout_trade_no", WIDout_trade_no);
        view.addObject("WIDsubject", WIDsubject);
        view.addObject("WIDtotal_fee", WIDtotal_fee);
        view.setViewName("payOrder/alipayapi");
        return view;
    }
	@RequestMapping("/toNotify")
    public void toNotify(@RequestParam String orderNo) {
		AuntOrder auntOrder = orderService.findOrderByOrderNo(orderNo);
		if(auntOrder!=null){
			orderService.updateOrderByOrderNo(orderNo, "ONLINE_PAYED");
		}
    }
	
	@RequestMapping("/tocallbackurl")
    public void tocallbackurl(@RequestParam String orderNo) {
		AuntOrder auntOrder = orderService.findOrderByOrderNo(orderNo);
		if(auntOrder!=null){
			orderService.updateOrderByOrderNo(orderNo, "ONLINE_PAYED");
		}
    }
	   @RequestMapping(value = "/toOrder")
	   	public String toOrder()  throws RemoteInvokeException{
		   List<AuntOrder> List = orderService.getAllAuntOrder();
		   BigDecimal monthMoney=new BigDecimal(0);
		   BigDecimal yearMoney=new BigDecimal(0);
		   BigDecimal sumMoney=new BigDecimal(0);
		   SimpleDateFormat yearSdf=new SimpleDateFormat("yyyy");
		   SimpleDateFormat monthSdf=new SimpleDateFormat("MM");
		   Calendar cal=Calendar.getInstance();
		   Date date=cal.getTime();
		   String year =yearSdf.format(date);
		   String month =monthSdf.format(date);
		   for (AuntOrder auntOrder : List) {
			   if (auntOrder.getActualPrice()!=null) {
				   sumMoney=sumMoney.add(auntOrder.getActualPrice());
				   if(auntOrder.getOptTime().contains(year)){
					   yearMoney=yearMoney.add(auntOrder.getActualPrice());
				   }
				   if(auntOrder.getOptTime().contains("-"+month+"-")){
					   monthMoney=monthMoney.add(auntOrder.getActualPrice());
				   }
			   
			}
			   
		   }
		   ModelAndView view = new ModelAndView();
		   Map<String, Object> model = view.getModel();
	       model.put("monthMoney",monthMoney);
	       model.put("yearMoney",yearMoney);
	       model.put("sumMoney",sumMoney);
	   	   return "orderManager/bill-management";
	   	}
	   
	   @ResponseBody
	    @RequestMapping(value = "/findOrderListByWeb")
	    OpenPage<AuntOrder> findOrderListByWeb(@RequestParam String auntId,@RequestParam String contactWay,@RequestParam String auntNo,@FastJson OpenPage<AuntOrder> page){
	    
	        try {
	        	page = orderService.findAuntOrderListByWeb(auntId, contactWay,auntNo,page);
	        } catch (RemoteInvokeException e) {
	            e.printStackTrace();
	        }
	        return page;
	    }
}
