package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.util.JsonResoult;
import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.query.RechargeQueryObject;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;
import cn.wolfcode.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RechargeController {

    @Autowired
    private IRechargeOfflineService rechargeOfflineService;

    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    @RequestMapping("/rechargeOffline")
    public String rechargeOffline(@ModelAttribute("qo")RechargeQueryObject qo, Model model){
        System.out.println(qo);
        PageResult pageResult = rechargeOfflineService.query(qo);
        model.addAttribute("pageResult", pageResult);

        List<PlatformBankInfo> banks =  platformBankInfoService.selectAll();
        model.addAttribute("banks",banks);
        return "rechargeoffline/list";
    }

    //审核
    @RequestMapping("/rechargeOffline_audit")
    @ResponseBody
    public JsonResoult audit(Long id, int state, String remark){
        System.out.println(state);
        JsonResoult resoult = new JsonResoult();
        try {

            rechargeOfflineService.audit(id,state,remark);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }

        return resoult;
    }
}
