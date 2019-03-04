package cn.wolfcode.p2p.base.util;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.vo.SendVerifyCodeVo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * 用于获取session
 */
public class UserContext {
    private static final String VERIFY_CODE_IN_SESSION = "verify_code_in_session";

    //登录session
    private static final String LOGININFO_IN_SESSION = "logininfo";

    //获取session
    private static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        return session;
    }
    //将验证码发送记录保存到session中
    public static void setVerifyCodeVo(SendVerifyCodeVo vo){
        getSession().setAttribute(VERIFY_CODE_IN_SESSION,vo);
    }

    //从session中获取验证码发送记录
    public static SendVerifyCodeVo getVerifyCodeVo(){
        return (SendVerifyCodeVo) getSession().getAttribute(VERIFY_CODE_IN_SESSION);
    }

    //将logininfo保存到session中
    public static void setLoginInfo(LoginInfo login){
        getSession().setAttribute(LOGININFO_IN_SESSION,login);
    }

    //从session中获取logininfo
    public static LoginInfo getLoginInfo(){
        return (LoginInfo) getSession().getAttribute(LOGININFO_IN_SESSION);
    }

}
