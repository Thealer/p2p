package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;
import cn.wolfcode.p2p.base.service.IRealauthService;
import cn.wolfcode.p2p.base.util.JsonResoult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RealAuthController {


    @Autowired
    private IRealauthService realauthService;

    @RequestMapping("/realAuth")
    public String realAuth(@ModelAttribute("qo") RealAuthQueryObject qo, Model model) {
        PageResult pageResult = realauthService.query(qo);
        model.addAttribute("pageResult", pageResult);
        return "realAuth/list";
    }


    @RequestMapping("/realAuth_audit")
    @ResponseBody
    public JsonResoult audit(Long id,int state, String remark){
        JsonResoult resoult = new JsonResoult();
        try {

            realauthService.audit(id,state,remark);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }

        return resoult;
    }

}
