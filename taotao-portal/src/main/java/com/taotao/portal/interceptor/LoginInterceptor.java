package com.taotao.portal.interceptor;

import com.taotao.common.utils.CookieUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //在Handler执行之前处理
        //判断用户是否登录
        //1.从cookie中取token
        String token = CookieUtil.getCookieValue(httpServletRequest, "TT_TOKEN");
        //2.根据token换取用户信息，调用sso系统的接口。
        TbUser user = userService.getUserByToken(token);
        //取不到用户信息
        if (null == user) {
            //跳转到登录页面，把用户请求的url作为参数传递给登录页面。
            httpServletResponse.sendRedirect(userService.SSO_DOMAIN_BASE_USRL + userService.SSO_PAGE_LOGIN
                    + "?redirect=" + httpServletRequest.getRequestURL());
            //返回false
            return false;
        }
        //取到用户信息，放行
        //把用户信息放入Request
        httpServletRequest.setAttribute("user", user);
        //返回值决定handler是否执行。true：执行，false：不执行。
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // handler执行之后，返回ModelAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // 返回ModelAndView之后。
        //响应用户之后。
    }
}
