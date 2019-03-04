package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.util.JsonResoult;
import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.query.PlatformBankInfoQueryObject;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PlatforBankInfoController {

    @Autowired
    private IPlatformBankInfoService platformBankInfoService;

    @RequestMapping("/companyBank_list")
    public String companyBankList(@ModelAttribute("qo")PlatformBankInfoQueryObject qo, Model model){

        PageResult pageResult = platformBankInfoService.query(qo);
        model.addAttribute("pageResult", pageResult);
        return "platformbankinfo/list";
    }

    @RequestMapping("/companyBank_update")
    @ResponseBody
    public JsonResoult save(PlatformBankInfo bank){
        System.out.println(bank.getId());
        JsonResoult resoult = new JsonResoult();
        try {

            platformBankInfoService.save(bank);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }

        return resoult;
    }
}
