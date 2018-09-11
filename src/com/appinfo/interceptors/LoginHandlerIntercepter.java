package com.appinfo.interceptors;

import com.appinfo.pojo.BackendUser;
import com.appinfo.pojo.DevUser;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 */
public class LoginHandlerIntercepter extends HandlerInterceptorAdapter {
    private static Logger logger=Logger.getLogger(LoginHandlerIntercepter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object arg2) throws Exception {
        logger.debug("SysInterceptor preHandle ==========================");
        HttpSession session = request.getSession();

        BackendUser backendUser = (BackendUser)session.getAttribute("backenUserSession");
        DevUser devUser = (DevUser)session.getAttribute("devUserSession");
        if(null != devUser){ //dev SUCCESS
            return true;
        }else if(null != backendUser){ //backend SUCCESS
            return true;
        }else{
            response.sendRedirect(request.getContextPath()+"/403.jsp");
            return false;
        }

    }
}
