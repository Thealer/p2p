package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.query.BidRequestQueryObject;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.service.IBidRequestService;
import cn.wolfcode.p2p.base.util.Constans;
import cn.wolfcode.p2p.base.util.JsonResoult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class BidRequestController {

    @Autowired
    private IBidRequestService bidRequestService;

    //借款审核
    @RequestMapping("/bidrequest_publishaudit_list")
    public String bidrequestPublishauditList(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {

        //设置查询的借款状态为申请中
        qo.setBidRequestState(Constans.BIDREQUEST_STATE_APPLY);
        PageResult pageResult = bidRequestService.query(qo);
        model.addAttribute("pageResult", pageResult);
        return "bidrequest/publish_audit";
    }

    //满标一审页面
    @RequestMapping("/bidrequest_audit1_list")
    public String bidrequestAudit1List(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {

        //设置查询的借款状态为满标一审
        qo.setBidRequestState(Constans.BIDREQUEST_STATE_APPROVE_PENDING_1);
        PageResult pageResult = bidRequestService.query(qo);
        model.addAttribute("pageResult", pageResult);
        return "bidrequest/audit1";
    }

    //满标二审页面
    @RequestMapping("/bidrequest_audit2_list")
    public String bidrequestAudit2List(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {

        //设置查询的借款状态为满标二审
        qo.setBidRequestState(Constans.BIDREQUEST_STATE_APPROVE_PENDING_2);
        PageResult pageResult = bidRequestService.query(qo);
        model.addAttribute("pageResult", pageResult);
        return "bidrequest/audit2";
    }


    //提交借款审核
    @RequestMapping("/bidrequest_publishaudit")
    @ResponseBody
    public JsonResoult audit(Long id, int state, String remark, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date publishTime){
        JsonResoult resoult = new JsonResoult();
        try {

            bidRequestService.auditPublish(id,state,remark,publishTime);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }

        return resoult;
    }


    //提交满标一审审核
    @RequestMapping("/bidrequest_audit1")
    @ResponseBody
    public JsonResoult audit1(Long id, int state, String remark){
        JsonResoult resoult = new JsonResoult();
        try {

            bidRequestService.audit1(id,state,remark);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }

        return resoult;
    }

    //提交满标二审审核
    @RequestMapping("/bidrequest_audit2")
    @ResponseBody
    public JsonResoult audit2(Long id, int state, String remark){
        JsonResoult resoult = new JsonResoult();
        try {

            bidRequestService.audit2(id,state,remark);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
            e.printStackTrace();
        }

        return resoult;
    }


}
