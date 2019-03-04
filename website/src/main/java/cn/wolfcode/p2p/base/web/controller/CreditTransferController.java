package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.util.JsonResoult;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.query.CreditTransferQueryObject;
import cn.wolfcode.p2p.business.service.ICreditTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CreditTransferController {
    @Autowired
    private ICreditTransferService creditTransferService;

    //可转让债权页面
    @RequestMapping("/canCreditTrans")
    public String canCreditTrans(@ModelAttribute("qo") CreditTransferQueryObject qo, Model model){

        qo.setCurrentUserId(UserContext.getLoginInfo().getId());
        PageResult pageResult = creditTransferService.canCreditTrans(qo);
        model.addAttribute("pageResult",pageResult);
        return "credittransfer_cantrans_list";
    }

    //转让实现
    @RequestMapping("/creditTransfer")
    @ResponseBody
    public JsonResoult creditTransfer(Long[] bidId){
        JsonResoult resoult = new JsonResoult();
        try {

            creditTransferService.creditTransfer(bidId);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
            e.printStackTrace();
        }
        return resoult;
    }

    //债权认购
    @RequestMapping("/subscribe")
    @ResponseBody
    public JsonResoult subscribe(Long id){
        JsonResoult resoult = new JsonResoult();
        try {

            creditTransferService.subscribe(id);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
            e.printStackTrace();
        }
        return resoult;
    }


}
