package com.fh.config.inter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class KuaYuInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String yuming = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", yuming);
        return true;
    }
}
