package cn.wolfcode.p2p.base.web.interceptor;

import cn.wolfcode.p2p.base.anno.LoginAnnotation;
import cn.wolfcode.p2p.base.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录检查
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler != null && handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;//获取请求方法
            //判断方法上是否有LoginAnnotion注解
            if(method.hasMethodAnnotation(LoginAnnotation.class) && UserContext.getLoginInfo() == null){//有贴标签且用户没有登录,跳转到登录页面
                response.sendRedirect("/login.html");
                return false;
            }
        }
        //其他情况放行
        return true;
    }
}
