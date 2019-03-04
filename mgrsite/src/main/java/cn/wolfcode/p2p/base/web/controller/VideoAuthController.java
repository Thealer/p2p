package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.query.VideoAuthQueryObject;
import cn.wolfcode.p2p.base.service.IVideoAuthService;
import cn.wolfcode.p2p.base.util.JsonResoult;
import cn.wolfcode.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VideoAuthController {

    @Autowired
    private IVideoAuthService videoAuthService;

    @RequestMapping("/vedioAuth")
    public String vedioAuth(@ModelAttribute("qo") VideoAuthQueryObject qo, Model model) {
        //只能查看自己的预约情况
        qo.setAuditorId(UserContext.getLoginInfo().getId());
        PageResult pageResult = videoAuthService.query(qo);
        model.addAttribute("pageResult", pageResult);
        return "vedioAuth/list";
    }

    @RequestMapping("/vedioAuth_audit")
    @ResponseBody
    public JsonResoult audit(Long id, int state, String remark){
        JsonResoult resoult = new JsonResoult();
        try {

            videoAuthService.audit(id,state,remark);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }

        return resoult;
    }
}
