package com.eighth.housekeeping.proxy;




import com.alibaba.fastjson.JSON;
import com.eighth.housekeeping.domain.*;
import com.eighth.housekeeping.proxy.exception.RemoteInvokeException;
import com.eighth.housekeeping.proxy.service.*;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by dam on 2014/6/27.
 */
public class ProxyTest {

    public static void main(String[] args) {
        String memberId = "u1";
        String auntId = "1";
        UserService proxy = new RemoteProxy<UserService>(UserService.class)
                .putOtherParameters("access_token","124")
                .putOtherParameters("imei","222")
                .getProxy();
        OrderService orderService = new RemoteProxy<OrderService>(OrderService.class).getProxy();

        ReviewService reviewService = new RemoteProxy<ReviewService>(ReviewService.class).getProxy();

        SignInfoService signInfoService = new RemoteProxy<SignInfoService>(SignInfoService.class).getProxy();

        SystemService systemService = new RemoteProxy<SystemService>(SystemService.class).getProxy();

        AuntService auntService = new RemoteProxy<AuntService>(AuntService.class).getProxy();


        try {
           // VerifyCode code = proxy.obtainVerifyCode("13833441123");
//            String result = proxy.checkVerifyCode(code);
//            MemberInfo info = proxy.findMemberByMemberId(result);
//            info.setUserName("理光");
//            proxy.modifyMemberInfo(info);
//            MemberInfo info1 = proxy.findMemberByMemberId(result);
//            System.out.println(info1.getUserName());
          // OpenPage<AuntOrder> orderOpenPage = orderService.findOrderList(memberId, "ALL",new OpenPage<AuntOrder>());
            //AuntOrder order = orderService.findOrderById("o1");
//            orderService.saveUserOrder(order);
            //orderService.deleteOrder(memberId,"dd3f1bfdbc414c17b8c5efa0022da539");
//            Review review = new Review();
//            review.setReviewTag("VERY_SATISFY");
//            review.setReviewContent("NB1");
//            review.setCreateUserId(memberId);
//            review.setAuntId(auntId);
//            Review a = reviewService.addReview(review);
//            SignInfo signInfo = new SignInfo();
//            signInfo.setAuntId(auntId);
//            signInfo.setSignPlaceDesc("西直门地铁A出口");
//            signInfo.setSignGeographic("xxx");
//            SignInfo a = signInfoService.sign(signInfo);

//            APKVersion version = systemService.updateAPK("1.0.0");
//            AuntInfo auntInfo = auntService.findAuntByIdForMember(auntId,memberId);
//            SignInfo signInfo = signInfoService.findSignDetail(auntId);
          //  AuntWorkCase workCase = auntService.findCaseById("1");
           // SystemManage systemManage = systemService.findSystemManage();

//            FeedBack feedBack = new FeedBack();
//            feedBack.setMemberId("0fceb950b214421e82debdf048126c75");
//            feedBack.setContent("功能太好用了，受不了");
//            FeedBack newFeed = systemService.saveFeedBack(feedBack);

//            String result = orderService.deleteOrderBatch("0fceb950b214421e82debdf048126c75","70c77431aa6941b7a0a240a3d0625b2f");
//            System.out.println(result);

//            int orderCounts = orderService.findOrderCountsByMemberIdAndType("u1","ONLINE_PAYED");
//            System.out.println(orderCounts);
//            AuntInfo params = new AuntInfo();
//            params.setAge(10);
//            params.setLatitude(116.403859);
//            params.setLongitude(39.915290);
//            OpenPage<AuntInfo> page = new OpenPage<AuntInfo>();
//            page.setPageNo(0);
//            page.setPageSize(10);
//            OpenPage<AuntInfo> auntInfoOpenPage = auntService.searchAuntByCondition(params,page);

            //OpenPage<AuntOrder> orderOpenPage = orderService.findAuntOrderList(auntId, "ALL",new OpenPage<AuntOrder>());
            OpenPage openPage = auntService.findReviewByAuntId("ALL",auntId,new OpenPage<Review>());
            System.out.println(JSON.toJSONString(openPage));
        } catch (RemoteInvokeException e) {
            e.printStackTrace();
        }

    }
    private Class getGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        } else {
            return (Class) genericClass;
        }
    }
    public static Class<?> getClass(Type type)
    {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
