package com.ch.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//过滤器；spring中内置的拦截器用于拦截admin相关的UI接口，防止游客登陆时将后台的数据删掉
public class LoginInterceptor extends HandlerInterceptorAdapter {

//    重写一个HandlerInterceptorAdapter预拦截方法
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
//        判断登陆者是否为游客状态，即为有的用户可能直接会输入admin之后的相关网页，
//        但他并没有登录，所以为了避免这种情况执行一下操作
        if(request.getSession().getAttribute("user")==null){
//            如果为游客输入这个后面的网址，则让其重定向到登录界面
            response.sendRedirect("/admin");
            return false;
        }
//        如果用户是已经登录的状态，输入admin之后的网页，则返回true值，继续执行之后的操作
        return true;
    }
}
