package com.eighth.housekeeping.controller;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.RSA;
import com.alipay.util.UtilDate;
import com.eighth.housekeeping.domain.AuntOrder;
import com.eighth.housekeeping.domain.OpenPage;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.OrderService;
import com.eighth.housekeeping.utils.PayOrderJson;
import com.eighth.housekeeping.web.FastJson;

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
	public OpenPage<AuntOrder> findOrderList(@RequestParam String memberId,
			@RequestParam String orderType, @FastJson OpenPage<AuntOrder> page) {
		OpenPage<AuntOrder> openPage = null;
		try {
			openPage = orderService.findOrderList(memberId, orderType, page);
		} catch (RemoteInvokeException e) {
			e.printStackTrace();
		}
		return openPage;
	}

	@ResponseBody
	@RequestMapping(value = "/findAuntOrderList")
	OpenPage<AuntOrder> findAuntOrderList(@RequestParam String auntId,
			@RequestParam String orderType, @FastJson OpenPage<AuntOrder> page) {
		OpenPage<AuntOrder> openPage = null;
		try {
			openPage = orderService.findAuntOrderList(auntId, orderType, page);
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
	public String deleteOrder(@RequestParam String memberId,
			@RequestParam String orderId) {
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
	String deleteOrderBatch(@RequestParam String memberId,
			@FastJson String... orderIds) {
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
	int findOrderCountsByMemberIdAndType(@RequestParam String memberId,
			@RequestParam String orderType) {
		try {
			return orderService.findOrderCountsByMemberIdAndType(memberId,
					orderType);
		} catch (RemoteInvokeException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@ResponseBody
	@RequestMapping(value = "/findAuntOrderListByWeb")
	OpenPage<AuntOrder> findAuntOrderListByWeb(@RequestParam String auntId,
			@RequestParam String contactWay, @RequestParam String auntNo,
			@FastJson OpenPage<AuntOrder> page) {
		OpenPage<AuntOrder> openPage = null;
		try {
			openPage = orderService.findAuntOrderListByWeb(auntId, null,
					contactWay, auntNo, page);
		} catch (RemoteInvokeException e) {
			e.printStackTrace();
		}
		return openPage;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteOrderByOrderId")
	public void deleteOrderByOrderId(@RequestParam String orderId) {
		try {
			orderService.deleteOrderByOrderId(orderId);
		} catch (RemoteInvokeException e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping("/toPayMent")
	public String toPayMent(@RequestParam String orderId,HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		try {
			AuntOrder order = orderService.findOrderById(orderId);
			StringBuffer requestURL = request.getRequestURL();
	        String requestURLPrefix = requestURL.substring(0,requestURL.indexOf("hw")+3);

			// 必填，须保证每次请求都是唯一
			// req_data详细信息
			// 卖家支付宝帐户
			String seller_email = new String("geassccvip@163.com");
			// 必填
			// 商户订单号
			String out_trade_no = new String(order.getOrderNo());
			// 商户网站订单系统中唯一订单号，必填
			// 服务器异步通知页面路径
			String notify_url =requestURLPrefix+"OrderService/toNotify?orderNo="
					+ out_trade_no;
			// 需http://格式的完整路径，不能加?id=123这类自定义参数

			// 页面跳转同步通知页面路径
			String call_back_url =requestURLPrefix+"OrderService/tocallbackurl?orderNo="
					+ out_trade_no;
			// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

			// 必填

			sb.append("partner=\"");
			sb.append(AlipayConfig.partner);
			sb.append("\"&out_trade_no=\"");
			sb.append(out_trade_no);
			sb.append("\"&subject=\"");
			sb.append(out_trade_no);
			sb.append("\"&body=\"");
			sb.append(out_trade_no);
			sb.append("\"&total_fee=\"");
			sb.append(order.getActualPrice());
			sb.append("\"&notify_url=\"");

			// 网址需要做URL编码
			sb.append(URLDecoder.decode(notify_url,AlipayConfig.input_charset));
			sb.append("\"&service=\"mobile.securitypay.pay");
			sb.append("\"&_input_charset=\"UTF-8");
			sb.append("\"&return_url=\"");
			sb.append( URLDecoder.decode(call_back_url,AlipayConfig.input_charset));
			sb.append("\"&payment_type=\"1");
			sb.append("\"&seller_id=\"");
			sb.append(seller_email);
			sb.append("\"&it_b_pay=\"1m\"");

		} catch (Exception e) {
			e.printStackTrace();
		}
		String sbb=sb.toString().replace(" ","");
		return sbb;
	}

	@ResponseBody
	@RequestMapping("/toPayMentJson")
	public PayOrderJson toPayMentJson(@RequestParam String orderId,HttpServletRequest request) {
		PayOrderJson payOrderJson=new PayOrderJson();
		try {
			AuntOrder order = orderService.findOrderById(orderId);
			StringBuffer requestURL = request.getRequestURL();
	        String requestURLPrefix = requestURL.substring(0,requestURL.indexOf("hw")+3);

			// 必填，须保证每次请求都是唯一
			// req_data详细信息
			// 卖家支付宝帐户
			String seller_email = new String("geassccvip@163.com");
			// 必填
			// 商户订单号
			String out_trade_no = new String(order.getOrderNo());
			// 商户网站订单系统中唯一订单号，必填
			// 服务器异步通知页面路径
			String notify_url =requestURLPrefix+"OrderService/toNotify?orderNo="
					+ out_trade_no;
			// 需http://格式的完整路径，不能加?id=123这类自定义参数

			// 页面跳转同步通知页面路径
			String call_back_url =requestURLPrefix+"OrderService/tocallbackurl?orderNo="
					+ out_trade_no;
			// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

			// 必填
			payOrderJson.setPartner(AlipayConfig.partner);
			payOrderJson.setOut_trade_no(out_trade_no);
			payOrderJson.setSubject(out_trade_no);
			payOrderJson.setBody(out_trade_no);
			payOrderJson.setTotal_fee(order.getActualPrice().toString());
			payOrderJson.setNotify_url(URLDecoder.decode(notify_url,AlipayConfig.input_charset));
			payOrderJson.setService("mobile.securitypay.pay");
			payOrderJson.set_input_charset("UTF-8");
			payOrderJson.setReturn_url(URLDecoder.decode(call_back_url,AlipayConfig.input_charset));
			payOrderJson.setPayment_type("1");
			payOrderJson.setSeller_id(seller_email);
			payOrderJson.setIt_b_pay("1m");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return payOrderJson;
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
		String orderNo = auntOrder.getOrderNo();
		view.addObject("WIDseller_email", "geassccvip@163.com");
		view.addObject("WIDout_trade_no", orderNo);
		view.addObject("WIDsubject", orderNo);
		view.addObject("WIDtotal_fee", auntOrder.getActualPrice());

		view.setViewName("payOrder/index");
		return view;
	}

	@RequestMapping("/toAlipayapi")
	public ModelAndView toAlipayapi(@RequestParam String WIDseller_email,
			@RequestParam String WIDout_trade_no,
			@RequestParam String WIDsubject, @RequestParam String WIDtotal_fee) {
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
		if (auntOrder != null) {
			orderService.updateOrderByOrderNo(orderNo, "ONLINE_PAYED");
		}
	}

	@RequestMapping("/tocallbackurl")
	public void tocallbackurl(@RequestParam String orderNo) {
		AuntOrder auntOrder = orderService.findOrderByOrderNo(orderNo);
		if (auntOrder != null) {
			orderService.updateOrderByOrderNo(orderNo, "ONLINE_PAYED");
		}
	}

	@RequestMapping(value = "/toOrder")
	public ModelAndView toOrder(@RequestParam String corpId) throws RemoteInvokeException {
		List<AuntOrder> List = orderService.getAllAuntOrder();
		BigDecimal monthMoney = new BigDecimal(0);
		BigDecimal yearMoney = new BigDecimal(0);
		BigDecimal sumMoney = new BigDecimal(0);
		SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthSdf = new SimpleDateFormat("MM");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		String year = yearSdf.format(date);
		String month = monthSdf.format(date);
		for (AuntOrder auntOrder : List) {
			if (auntOrder.getActualPrice() != null) {
				sumMoney = sumMoney.add(auntOrder.getActualPrice());
				if (auntOrder.getOptTime().contains(year)) {
					yearMoney = yearMoney.add(auntOrder.getActualPrice());
				}
				if (auntOrder.getOptTime().contains("-" + month + "-")) {
					monthMoney = monthMoney.add(auntOrder.getActualPrice());
				}

			}

		}
		ModelAndView view = new ModelAndView();
		Map<String, Object> model = view.getModel();
		model.put("monthMoney", monthMoney);
		model.put("yearMoney", yearMoney);
		model.put("sumMoney", sumMoney);
		model.put("corpId", corpId);
		view.setViewName("orderManager/bill-management");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/findOrderListByWeb")
	OpenPage<AuntOrder> findOrderListByWeb(@RequestParam String corpId,
			@RequestParam String contactWay, @RequestParam String auntNo,
			@FastJson OpenPage<AuntOrder> page) {

		try {
			page = orderService.findAuntOrderListByWeb(null, corpId,
					contactWay, auntNo, page);
		} catch (RemoteInvokeException e) {
			e.printStackTrace();
		}
		return page;
	}
}
