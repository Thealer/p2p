package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.anno.LoginAnnotation;
import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IBidRequestService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.Constans;
import cn.wolfcode.p2p.base.util.JsonResoult;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.query.PaymentScheduleQueryObject;
import cn.wolfcode.p2p.business.service.IPaymentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 借款页面跳转
 *
 */

@Controller
public class BorrowController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IBidRequestService bidRequestService;

    @Autowired
    private IPaymentScheduleService paymentScheduleService;


    @RequestMapping("/borrow")
    public String borrow(Model model){
        LoginInfo loginInfo = UserContext.getLoginInfo();
        if(loginInfo != null){//有登陆,跳转动态页面
            //共享account账户信息
            Account account = accountService.selectById(loginInfo.getId());
            model.addAttribute("account",account);

            //共享用户信息
            UserInfo userinfo =  userInfoService.selectById(loginInfo.getId());
            model.addAttribute("userinfo",userinfo);

            return "borrow";
        }
        return "redirect:/borrowpage.html";
    }

    //借款信息填写页面/结果页面
    @RequestMapping("/borrowInfo")
    @LoginAnnotation
    public String borrowInfo(Model model){
        UserInfo userInfo = userInfoService.selectById(UserContext.getLoginInfo().getId());
        //如果没有满足借款条件
        if(!userInfo.isCanBorrow()){
            return "redirect:/borrow";
        }
        //判断是否已经有申请
        if(userInfo.isBidRequestInProcess()){
            return "borrow_apply_result";
        }
        //最小借款金额
        model.addAttribute("minBidRequestAmount", Constans.BORROW_MIN_AMOUNT);
        //用户账户
        model.addAttribute("account",accountService.selectById(UserContext.getLoginInfo().getId()));
        //最小和最大利率
        model.addAttribute("min",Constans.BORROW_MIN_RATE);
        model.addAttribute("max",Constans.BORROW_MAX_RATE);
        //最小投标金额
        model.addAttribute("minBidAmount",Constans.BORROW_MIN_BIT_AMOUNT);
        return "borrow_apply";
    }
    //保存一个借款对象
    @RequestMapping("/borrow_apply")
    @ResponseBody
    public JsonResoult borrowApply(BidRequest bidRequest){
        JsonResoult resoult = new JsonResoult();
        try {

            bidRequestService.apply(bidRequest);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }
        return resoult;
    }

    //还款列表
    @RequestMapping("/borrowBidReturn_list")
    public String borrowBidReturnList(@ModelAttribute("qo") PaymentScheduleQueryObject qo, Model model) {

        //还款人只能看到自己的还款列表
        qo.setBorrowUserId(UserContext.getLoginInfo().getId());
        PageResult pageResult = paymentScheduleService.query(qo);
        model.addAttribute("pageResult", pageResult);

        //用户账户
        Account account = accountService.selectById(UserContext.getLoginInfo().getId());
        model.addAttribute("account",account);
        return "returnmoney_list";
    }

    //还款
    @RequestMapping("/returnMoney")
    @ResponseBody
    public JsonResoult returnMoney(Long id){
        JsonResoult resoult = new JsonResoult();
        try {

            bidRequestService.returnMoney(id);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
            e.printStackTrace();
        }
        return resoult;
    }
}
