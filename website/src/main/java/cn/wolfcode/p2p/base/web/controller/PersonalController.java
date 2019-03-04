package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.anno.LoginAnnotation;
import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.JsonResoult;
import cn.wolfcode.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PersonalController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequestMapping("/personal")
    @LoginAnnotation
    public String personal(Model model) {
        Account account = accountService.selectById(UserContext.getLoginInfo().getId());
        model.addAttribute("account", account);
        return "personal";
    }


    @RequestMapping("/basicInfo")
    @LoginAnnotation
    public String basicInfo(Model model) {
        UserInfo userinfo = userInfoService.selectById(UserContext.getLoginInfo().getId());

        System.out.println(userinfo);
        model.addAttribute("userinfo",userinfo);

        //查询用户各种资料
        List<SystemDictionaryItem> educationBackgrounds = systemDictionaryService.selectItemsBySn("educationBackgrounds");
        List<SystemDictionaryItem> incomeGrades = systemDictionaryService.selectItemsBySn("incomeGrades");
        List<SystemDictionaryItem> marriages = systemDictionaryService.selectItemsBySn("marriages");
        List<SystemDictionaryItem> kidCounts = systemDictionaryService.selectItemsBySn("kidCounts");
        List<SystemDictionaryItem> houseConditions = systemDictionaryService.selectItemsBySn("houseConditions");

        model.addAttribute("educationBackgrounds",educationBackgrounds);
        model.addAttribute("incomeGrades",incomeGrades);
        model.addAttribute("marriages",marriages);
        model.addAttribute("kidCounts",kidCounts);
        model.addAttribute("houseConditions",houseConditions);

        return "userInfo";
    }

    //保存个人资料
    @RequestMapping("/basicInfo_save")
    @ResponseBody
    public JsonResoult basicInfoSave(UserInfo userInfo){
        JsonResoult resoult = new JsonResoult();
        try {

            userInfoService.UpdateBasicInfo(userInfo);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }

        return resoult;
    }


}
