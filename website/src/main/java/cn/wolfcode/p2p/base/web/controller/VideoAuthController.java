package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.domain.*;
import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.IOrderTimeService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.service.IVideoAuthService;
import cn.wolfcode.p2p.base.util.JsonResoult;
import cn.wolfcode.p2p.base.util.UserContext;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class VideoAuthController {
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IVideoAuthService videoAuthService;
    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private IOrderTimeService orderTimeService;


    @RequestMapping("/videoAuditOrder")
    public String videoAuditOrder(Model model){
        //当前登录用户
        UserInfo userInfo = userInfoService.selectById(UserContext.getLoginInfo().getId());
        if(userInfo.isVedioAuth()){//若用户已经完成视频认证,跳到已完成视频认证界面
            model.addAttribute("videoSuccess",true);
        }else if(userInfo.getVideoAuthId() != null){//若用户已经发起了一个视频申请预约,则跳到已申请信息页面
            model.addAttribute("videoAuditOrder",videoAuthService.selectById(userInfo.getVideoAuthId()));
        }else{//否则,显示填写视频认证信息
            //审核客服
            List<LoginInfo> auditors = loginInfoService.selectAuditors();

            //预约时间段列表
            List<OrderTime> orderTimes = orderTimeService.selectAll();
            //创建预约日期
            List<Date> orderDates = new ArrayList<Date>();
            Date now = new Date();
            orderDates.add(DateUtils.addDays(now,1));
            orderDates.add(DateUtils.addDays(now,2));
            orderDates.add(DateUtils.addDays(now,3));

            model.addAttribute("auditors",auditors);
            model.addAttribute("orderTimes",orderTimes);
            model.addAttribute("orderDates",orderDates);

        }
        return "videoOrder";
    }

    //保存一个视频申请
    @RequestMapping("/saveVideoAuditOrder")
    @ResponseBody
    public JsonResoult saveVideoAuditOrder(VideoAuth videoAuth){
        JsonResoult resoult = new JsonResoult();
        try {

            videoAuthService.saveVideoAuditOrder(videoAuth);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }
        return resoult;
    }
}
