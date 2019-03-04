package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.util.JsonResoult;
import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.domain.RechargeOffline;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;
import cn.wolfcode.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ReachargeController {

    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    @Autowired
    private IRechargeOfflineService rechargeOfflineService;

    @RequestMapping("/recharge")
    public String recharge(Model model){

        List<PlatformBankInfo> banks = platformBankInfoService.selectAll();
        model.addAttribute("banks",banks);
        return "recharge";
    }

    @RequestMapping("/recharge_save")
    @ResponseBody
    public JsonResoult rechargeSave(RechargeOffline rechargeOffline){
        JsonResoult resoult = new JsonResoult();
        try {
            rechargeOfflineService.rechargeSave(rechargeOffline);//发送短信验证码
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }

        return resoult;
    }
}
