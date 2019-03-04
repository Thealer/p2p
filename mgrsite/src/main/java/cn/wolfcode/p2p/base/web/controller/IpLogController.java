package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.anno.LoginAnnotation;
import cn.wolfcode.p2p.base.query.IpLogQueryObject;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.service.IIpLogService;
import cn.wolfcode.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 后台日志
 */
@Controller
public class IpLogController {
    @Autowired
    private IIpLogService ipLogService;

    @RequestMapping("/ipLog")
    @LoginAnnotation
    public String ipLog(@ModelAttribute("qo") IpLogQueryObject qo, Model model){

        qo.setUsername(UserContext.getLoginInfo().getUsername());//只能查看自己的登录日志
        PageResult pageResult = ipLogService.query(qo);
        model.addAttribute("pageResult",pageResult);

        return "/ipLog/list";
    }
}
