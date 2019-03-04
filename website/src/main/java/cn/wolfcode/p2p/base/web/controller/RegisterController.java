package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.ISendVerifyCodeService;
import cn.wolfcode.p2p.base.util.JsonResoult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {

    @Autowired
    private ILoginInfoService loginInfoService;

    @Autowired
    private ISendVerifyCodeService sendVerifyCodeService;

    @RequestMapping("/userRegister")
    @ResponseBody
    public JsonResoult userRegister(String username,String verifycode,String password,String confirmPwd){

        JsonResoult resoult = new JsonResoult();
        try {
            loginInfoService.register(username, verifycode, password, confirmPwd);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("出现异常,正在修复中");
        }
        return resoult;
    }

    //判断账户是否存在
    @RequestMapping("/existUsername")
    @ResponseBody
    public boolean existUsername(String username){
        return !loginInfoService.existUsername(username);
    }

    @RequestMapping("/sendVerifyCode")
    @ResponseBody
    public JsonResoult sendVerifyCode(String phone){
        JsonResoult resoult = new JsonResoult();
        try {
            sendVerifyCodeService.send(phone);//发送短信验证码
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }

        return resoult;
    }

}
