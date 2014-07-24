package com.eighth.housekeeping.utils;

import java.util.UUID;

/**
 * Created by dam on 2014/7/24.
 */
public class CommonStringUtils {

    public static String genPK(){
        return UUID.randomUUID().toString().replace("-","");
    }
    public static String gen4RandomKey(){
        Double d = Math.random()*9000+1000;
        Integer i = d.intValue();
        return i.toString();
    }

    public static void main(String[] args) {
        System.out.println(CommonStringUtils.gen4RandomKey());
    }
}
