package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.domain.*;
import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.query.BidRequestQueryObject;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.service.*;
import cn.wolfcode.p2p.base.util.Constans;
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

import java.math.BigDecimal;

@Controller
public class InvestController {

    @Autowired
    private IBidRequestService bidRequestService;

    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IRealauthService realauthService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBidService bidService;

    @Autowired
    private ICreditTransferService creditTransferService;

    //投资列表
    @RequestMapping("/invest")
    public String invest(){

        return "invest";
    }

    //投资列表数据查询

    @RequestMapping("/invest_list")
    public String investList(@ModelAttribute("qo")BidRequestQueryObject qo, Model model){

        if(qo.getBidRequestType() == Constans.BIDREQUEST_TYPE_NORMAL){
            //全部:招标中和已经完成的全部
            if (qo.getBidRequestState() == -1) {
                qo.setBidRequestStates(new int[]{Constans.BIDREQUEST_STATE_BIDDING,
                        Constans.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
            }
            PageResult pageResult = bidRequestService.query(qo);
            model.addAttribute("pageResult", pageResult);
            return "invest_list";
        }else{
            //债权表认购列表查询
            CreditTransferQueryObject cqo = new CreditTransferQueryObject();
            cqo.setBidRequestState(Constans.BIDREQUEST_STATE_BIDDING);
            cqo.setCurrentPage(qo.getCurrentPage());
            cqo.setPageSize(qo.getPageSize());
            PageResult pageResult = creditTransferService.query(cqo);
            model.addAttribute("pageResult",pageResult);
            return "credittransfer_list";
        }
    }

    @RequestMapping("/borrow_info")
    public String borrowInfo(Long id,Model model){
        //借款对象
        BidRequest bidRequest = bidRequestService.seletById(id);
        model.addAttribute("bidRequest",bidRequest);
        //借款人
        LoginInfo createUser = bidRequest.getCreateUser();
        //借款人的UserInfo
        UserInfo userInfo = userInfoService.selectById(createUser.getId());
        model.addAttribute("userInfo",userInfo);
        //借款人实名信息
        RealAuth realAuth = realauthService.selectById(userInfo.getRealAuthId());
        model.addAttribute("realAuth",realAuth);
        //登录用户
        LoginInfo loginInfo = UserContext.getLoginInfo();
        if(loginInfo != null){
            //判断是否是借款人 self
            if(loginInfo.getId().longValue() == createUser.getId().longValue()){
                model.addAttribute("self",true);
            }else{
                //投资人账户
                Account account = accountService.selectById(loginInfo.getId());
                model.addAttribute("account",account);
            }
        }

        return "borrow_info";
    }

    //用户投资
    @RequestMapping("/borrow_bid")
    @ResponseBody
    public JsonResoult borrowBid(Long bidRequestId, BigDecimal amount){
        JsonResoult resoult = new JsonResoult();
        try {
            bidRequestService.bid(bidRequestId,amount);
        } catch (DisplayException e) {
            resoult.setMsg(e.getMessage());
        }catch (Exception e) {
            resoult.setMsg("系统出现异常,正在修复中");
        }
        return resoult;
    }

}
