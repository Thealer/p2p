package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.query.SystemDictionaryQueryObject;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
import cn.wolfcode.p2p.base.util.JsonResoult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SystemDictionaryController {

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequestMapping("/systemDictionary_list")
    public String systemDictionaryList(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model){

        PageResult pageResult =  systemDictionaryService.queryDic(qo);
        model.addAttribute("pageResult",pageResult);

        return "systemdic/systemDictionary_list";
    }

    @RequestMapping("/systemDictionary_update")
    @ResponseBody
    public JsonResoult systemDictionaryUpdate(SystemDictionary systemDictionary){
        JsonResoult resoult = new JsonResoult();
        try {

            systemDictionaryService.systemDictionaryUpdate(systemDictionary);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }

        return resoult;
    }

    @RequestMapping("/systemDictionaryItem_update")
    @ResponseBody
    public JsonResoult systemDictionaryItemUpdate(SystemDictionaryItem systemDictionaryItem){
        System.out.println(systemDictionaryItem.toString());
        JsonResoult resoult = new JsonResoult();
        try {

            systemDictionaryService.systemDictionaryItemUpdate(systemDictionaryItem);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }

        return resoult;
    }


    @RequestMapping("/systemDictionaryItem_list")
    public String systemDictionaryItemList(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model){



        PageResult pageResult =  systemDictionaryService.queryItem(qo);
        model.addAttribute("pageResult",pageResult);

        //分组列表
        List<SystemDictionary> systemDictionaryGroups = systemDictionaryService.selectAll();
        model.addAttribute("systemDictionaryGroups",systemDictionaryGroups);

        return "systemdic/systemDictionaryItem_list";
    }



}
