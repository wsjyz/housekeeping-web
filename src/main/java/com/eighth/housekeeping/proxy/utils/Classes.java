package com.eighth.housekeeping.proxy.utils;

import com.alibaba.fastjson.*;
import com.eighth.housekeeping.domain.VerifyCode;
import com.eighth.housekeeping.proxy.annotation.RemoteMethod;
import com.eighth.housekeeping.proxy.service.UserService;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.Arrays;

/**
 * Created by dam on 2014/6/27.
 */
public class Classes {


    public static Object stringToObject(String s, java.lang.reflect.Type type){
        if(type instanceof Class){
            Class c = (Class)type;
            if(c.isAssignableFrom(String.class)){
                return s;
            }
        }
        return JSON.parseObject(s,type);
    }

    public static String parseClassMethodToUri(String packageClassName,String methodName){
        StringBuilder str = new StringBuilder(packageClassName+"/"+methodName);
        str.delete(0,str.lastIndexOf(".") + 1);
        return str.toString();
    }

    public static String[] parseMethodVarNames(Method method){
        String[] methodVarNames = new String[]{};
        if(method.isAnnotationPresent(RemoteMethod.class)){
            RemoteMethod remoteMethod = method.getAnnotation(RemoteMethod.class);
            methodVarNames = remoteMethod.methodVarNames();
        }
        return methodVarNames;
    }

    public static void main(String[] args) {
        Class cl = UserService.class;
        Method m = null;
        try {
            //m = cl.getMethod("modifyPushAuntInfo",String.class,int.class);
            m = cl.getMethod("checkVerifyCode", VerifyCode.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Type t = m.getReturnType();
        Class c = (Class)t;
        if(c.isAssignableFrom(String.class)){
            System.out.println("yes");
        }
        //Classes.parseMethodVarNames(m);
    }
}
