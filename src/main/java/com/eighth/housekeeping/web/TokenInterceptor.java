package com.eighth.housekeeping.web;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dam on 2014/7/5.
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String contentType = request.getContentType();
        if(contentType != null && contentType.startsWith("application/x-www-form-urlencoded")){
            //接收token和imei以备后用
            String accessToken = request.getParameter("access_token");
            String imeiCode = request.getParameter("imei");
            System.out.println("access token:"+accessToken+" imei:"+imeiCode);
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
