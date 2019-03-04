package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.service.IIpLogService;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.util.JsonResoult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台登录
 */
@Controller
public class LoginController {

    @Autowired
    private ILoginInfoService loginInfoService;

    @Autowired
    private IIpLogService ipLogService;

    @RequestMapping("/userLogin")
    @ResponseBody
    public JsonResoult login(LoginInfo loginInfo, HttpServletRequest request){
        JsonResoult resoult = new JsonResoult();
        //登录成功或失败的状态
        int state = IpLog.ERROR_STATE;
        try {
            //设置用户登录类型
            loginInfo.setUserType(LoginInfo.USERTYPE_MGRSITE);
            loginInfoService.login(loginInfo);
            state = IpLog.SUCCESS_STATE;
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("出现异常,正在修复中");
        }
        ipLogService.save(loginInfo.getUsername(),state,request.getRemoteAddr(),LoginInfo.USERTYPE_MGRSITE);
        return resoult;
    }
}
