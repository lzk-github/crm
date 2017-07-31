package com.kaishengit.interceptor;

import com.kaishengit.entity.Account;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录状态 - 拦截器
 * Created by lzk on 2017/7/19.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 拦截器方法,判断用户访问路径以及用户登录状态
     * @param request 请求
     * @param response 响应
     * @param handler
     * @return 是否拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession httpSession = request.getSession();
        String uri = request.getRequestURI();

        if(uri.startsWith("/static")) {
            return true;
        }

        if("/".equals(uri)) {
            return true;
        }

        if(httpSession.getAttribute("curr_acc") == null) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
