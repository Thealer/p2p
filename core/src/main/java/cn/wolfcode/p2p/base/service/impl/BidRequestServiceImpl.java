package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.*;
import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.mapper.AccountMapper;
import cn.wolfcode.p2p.base.mapper.BidRequestMapper;
import cn.wolfcode.p2p.base.query.BidRequestQueryObject;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IBidRequestService;
import cn.wolfcode.p2p.base.service.IBidService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.*;
import cn.wolfcode.p2p.business.domain.BidRequestAuditHistory;
import cn.wolfcode.p2p.business.domain.PaymentSchedule;
import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import cn.wolfcode.p2p.business.domain.SystemAccount;
import cn.wolfcode.p2p.business.service.*;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("all")
public class BidRequestServiceImpl implements IBidRequestService {
    @Autowired
    private BidRequestMapper bidRequestMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IBidRequestAuditHistoryService bidRequestAuditHistoryService;

    @Autowired
    private IBidService bidService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountFlowService accountFlowService;

    @Autowired
    private IPaymentScheduleService paymentScheduleService;

    @Autowired
    private IPaymentScheduleDetailService paymentScheduleDetailService;

    @Autowired
    private ISystemAccountService systemAccountService;

    @Autowired
    private ISystemAccountFlowService systemAccountFlowService;

    public void apply(BidRequest bidRequest) {
        //判断基本出参数
        //判断借款金额是否>=系统规定最小借款金额
        AsserUtil.isTrue(bidRequest.getBidRequestAmount().compareTo(Constans.BORROW_MIN_AMOUNT) < 0,
                "最小借款金额:" + Constans.BORROW_MIN_AMOUNT);
        //判断借款金额是否<=用户剩余借款额度
        LoginInfo loginInfo = UserContext.getLoginInfo();
        Account account = accountMapper.selectByPrimaryKey(loginInfo.getId());
        AsserUtil.isTrue(bidRequest.getBidRequestAmount().compareTo(account.getRemain_borrow_limit()) > 0,
                "用户最大借款金额:" + account.getRemain_borrow_limit());
        //判断利率是否>=系统规定最小李利率
        AsserUtil.isTrue(bidRequest.getCurrentRate().compareTo(Constans.BORROW_MIN_RATE) < 0,
                "最小利率:" + Constans.BORROW_MIN_RATE);
        //判断利率是否<=系统规定最大利率
        AsserUtil.isTrue(bidRequest.getCurrentRate().compareTo(Constans.BORROW_MAX_RATE) > 0,
                "最大利率:" + Constans.BORROW_MAX_RATE);
        //判断最小投标是否>=系统规定最小投标
        AsserUtil.isTrue(bidRequest.getMinBidAmount().compareTo(Constans.BORROW_MIN_BIT_AMOUNT) < 0,
                "最小投标额度:" + Constans.BORROW_MIN_BIT_AMOUNT);
        //最大投标金额不能大于借款的50%
        BigDecimal tempAmount = bidRequest.getBidRequestAmount().multiply(new BigDecimal("0.5"));
        AsserUtil.isTrue(bidRequest.getMinBidAmount().compareTo(tempAmount) > 0, "最大投标金额:" + tempAmount);
        //判断是否已经有在申请中的借款
        //用户
        UserInfo userInfo = userInfoService.selectById(loginInfo.getId());
        AsserUtil.isTrue(userInfo.isBidRequestInProcess(), "您已经有一个借款在申请中了");
        //判断是否满足借款条件
        AsserUtil.isTrue(!userInfo.isCanBorrow(), "您不满足借款条件");
        //保存一个借款申请
        BidRequest br = new BidRequest();
        br.setApplyTime(new Date());
        br.setBidRequestAmount(bidRequest.getBidRequestAmount());
        br.setBidRequestState(Constans.BIDREQUEST_STATE_APPLY);
        br.setBidRequestType(bidRequest.getBidRequestType());
        br.setCreateUser(loginInfo);
        br.setCurrentRate(bidRequest.getCurrentRate());
        br.setDescription(bidRequest.getDescription());
        br.setDisableDays(bidRequest.getDisableDays());
        br.setMinBidAmount(bidRequest.getMinBidAmount());
        br.setMonthes2Return(bidRequest.getMonthes2Return());
        br.setReturnType(bidRequest.getReturnType());
        br.setTitle(bidRequest.getTitle());

        //总利息
        br.setTotalRewardAmount(CalculatetUtil.calTotalInterest(bidRequest.getReturnType(), bidRequest.getBidRequestAmount(),
                bidRequest.getCurrentRate(), bidRequest.getMonthes2Return()));
        bidRequestMapper.insert(br);

        //修改userInfo的bit_state,增加有借款在申请中的状态
        Long bitState = BitStatesUtils.addState(userInfo.getBit_state(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
        userInfo.setBit_state(bitState);
        userInfoService.update(userInfo);

    }

    //
    public PageResult query(BidRequestQueryObject qo) {
        int count = bidRequestMapper.selectForCount(qo);
        if (count == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(bidRequestMapper.selectForList(qo), count, qo.getCurrentPage(), qo.getPageSize());
    }

    public void auditPublish(Long id, int state, String remark,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date publishTime) {
        System.out.println(publishTime);
        //判断基本参数
        //借款对象是否处于待审核申请
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        AsserUtil.isTrue(br.getBidRequestState() != Constans.BIDREQUEST_STATE_APPLY,
                "不处于待审核状态");
        //设置风控审核备注
        br.setNote(remark);
        //创建借款审核历史对象,设置相关审核信息
        bidRequestAuditHistoryService.save(BidRequestAuditHistory.AUDIT_TYPE_PUBLISH, br, state, remark);
        //审核成功
        if (state == BidRequestAuditHistory.STATE_SUCCESS) {
            if(publishTime != null){
                //定时发标
                //发标时间
                br.setPublishTime(publishTime);
                //设置招标截止时间:发标时间+招标天数
                br.setDisableDate(DateUtils.addDays(publishTime, br.getDisableDays()));
                //设置借款对象的状态值为:待发标
                br.setBidRequestState(Constans.BIDREQUEST_STATE_PUBLISH_PENDING);
            }else{
                //设置借款对象的状态值为:招标中
                br.setBidRequestState(Constans.BIDREQUEST_STATE_BIDDING);
                //设置招标截止时间:发标时间+招标天数
                Date date = new Date();
                br.setDisableDate(DateUtils.addDays(date, br.getDisableDays()));
                //设置发标时间
                br.setPublishTime(date);
            }
        } else {
            //审核失败
            //设置借款对象的状态:发标审核失败
            br.setBidRequestState(Constans.BIDREQUEST_STATE_PUBLISH_REFUSE);
            //设置借款用户userInfo的bit_state,移除借款申请中的状态
            UserInfo userInfo = userInfoService.selectById(UserContext.getLoginInfo().getId());
            Long bitState = BitStatesUtils.removeState(userInfo.getBit_state(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
            userInfo.setBit_state(bitState);
            userInfoService.update(userInfo);
        }

        update(br);

    }

    public List<BidRequest> indexBidRequestList() {
        BidRequestQueryObject qo = new BidRequestQueryObject();
        //设置查询条件
        qo.setBidRequestStates(new int[]{Constans.BIDREQUEST_STATE_BIDDING,
                Constans.BIDREQUEST_STATE_PAYING_BACK, Constans.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
        //设置排序条件
        qo.setOrderBy(" br.bidRequestState ");
        qo.setOrderType(" ASC ");

        //查询5条
        qo.setPageSize(5);

        return bidRequestMapper.selectForList(qo);
    }

    public BidRequest seletById(Long id) {
        return bidRequestMapper.selectByPrimaryKey(id);
    }

    public void bid(Long bidRequestId, BigDecimal amount) {
        System.out.println(bidRequestId);
        //查出借款对象
        BidRequest br = bidRequestMapper.selectByPrimaryKey(bidRequestId);
        //判断借款本人不能投标
        AsserUtil.isTrue(br.getCreateUser().getId().longValue() == UserContext.getLoginInfo().getId().longValue(),
                "自己不能投自己的借款");
        //判断借款状态要处于招标中
        AsserUtil.isTrue(br.getBidRequestState() != Constans.BIDREQUEST_STATE_BIDDING,
                "该标不处于招标中");
        //如果剩余投标金额小于最小投标金额,必须一次性投完,投资金额必须等于当前剩余可投标金额
        if (br.getMinBidAmount().compareTo(br.getRemainAmount()) > 0) {
            AsserUtil.isTrue(amount.compareTo(br.getRemainAmount()) != 0,
                    "当前可投标金额:" + br.getRemainAmount());
        } else {
            //否则投标金额要大于最小投标金额
            AsserUtil.isTrue(amount.compareTo(br.getMinBidAmount()) < 0,
                    "投标金额不能小于最小投标金额:" + br.getMinBidAmount());

        }
        //判断这个人对于这个投标金额不能大于这个借款的50%
        BigDecimal totalBidAmount = bidService.sumByBidRequestIdAndUserId(bidRequestId, UserContext.getLoginInfo().getId());
        totalBidAmount = totalBidAmount.add(amount);
        AsserUtil.isTrue(totalBidAmount.compareTo(br.getBidRequestAmount().multiply(new BigDecimal("0.5"))) > 0,
                "投标金额不能大于借款总额的50%");
        //投标金额不能大剩余可投标金额
        AsserUtil.isTrue(amount.compareTo(br.getRemainAmount()) > 0,
                "剩余可投标:" + br.getRemainAmount());
        //查出投资人账户
        Account account = accountService.selectById(UserContext.getLoginInfo().getId());
        //投资金额不能大于账户可用余额
        AsserUtil.isTrue(amount.compareTo(account.getUsable_amount()) > 0,
                "账户可用余额不足");
        //保存一个投标记录
        bidService.save(br, amount);
        //修改借款对象当前的总投标额
        br.setCurrentSum(br.getCurrentSum().add(amount));
        //修改借款象的当前投标次数
        br.setBidCount(br.getBidCount() + 1);
        //投资人的可用余额减少
        account.setUsable_amount(account.getUsable_amount().subtract(amount));
        //投资人的冻结金额增加
        account.setFreezed_amount(account.getFreezed_amount().add(amount));
        accountService.update(account);

        //保存一个投标流水
        accountFlowService.createBidFlow(account, br, amount);
        //如果满标修改借款状态为满标一审
        //该借款下的投资对象的状态修改为满标一审
        if (br.getCurrentSum().compareTo(br.getBidRequestAmount()) == 0) {
            br.setBidRequestState(Constans.BIDREQUEST_STATE_APPROVE_PENDING_1);
            bidService.batchUpdate(bidRequestId, Constans.BIDREQUEST_STATE_APPROVE_PENDING_1);
        }

        update(br);
    }


    public void audit1(Long id, int state, String remark) {
        //查出标
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        //判断借款的状态是否为满标一审
        AsserUtil.isTrue(br.getBidRequestState() != Constans.BIDREQUEST_STATE_APPROVE_PENDING_1,
                "借款状态不处于满标一审");
        //保存借款审核记录
        bidRequestAuditHistoryService.save(BidRequestAuditHistory.AUDIT_TYPE_AUDIT1, br, state, remark);
        //如果失败,修改借款的状态为审核拒绝
        if (state == BidRequestAuditHistory.STATE_REJECT) {
            br.setBidRequestState(Constans.BIDREQUEST_STATE_REJECTED);
            //这个借款下面的投资对象设为审核拒绝
            bidService.batchUpdate(br.getId(), Constans.BIDREQUEST_STATE_REJECTED);
            //查询出投资列表
            //循环列表
            List<Bid> bids = br.getBids();
            for (Bid bid : bids) {
                //查出投资人账户
                Account bidAccount = accountService.selectById(bid.getBidUser().getId());
                //投资人退款,冻结金额减少
                bidAccount.setFreezed_amount(bidAccount.getFreezed_amount().subtract(bid.getAvailableAmount()));
                //可用余额增加
                bidAccount.setUsable_amount(bidAccount.getUsable_amount().add(bid.getAvailableAmount()));
                //创建解冻流水
                accountFlowService.createBidErrorFlow(bidAccount, bid.getAvailableAmount(), br);
                accountService.update(bidAccount);
                //移除用户是否有借款在申请中的状态
                UserInfo userInfo = userInfoService.selectById(br.getCreateUser().getId());
                Long bitState = BitStatesUtils.removeState(userInfo.getBit_state(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
                userInfo.setBit_state(bitState);
                userInfoService.update(userInfo);
            }
        } else {
            //如果审核通过
            //修改标的状态为满标二审
            br.setBidRequestState(Constans.BIDREQUEST_STATE_APPROVE_PENDING_2);
            //修改投标对象的状态为满标二审
            bidService.batchUpdate(br.getId(), Constans.BIDREQUEST_STATE_APPROVE_PENDING_2);
            //更新标
            update(br);
        }
    }


    public void audit2(Long id, int state, String remark) {
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        //判断借款对象是否处于满标二审
        AsserUtil.isTrue(br.getBidRequestState() != Constans.BIDREQUEST_STATE_APPROVE_PENDING_2,
                "该标不处于满标二审状态");
        //设置相关信息,保存审核记录
        bidRequestAuditHistoryService.save(BidRequestAuditHistory.AUDIT_TYPE_AUDIT2, br, state, remark);
        //如果审核成功,修改借款对象状态为,还款中
        if (state == BidRequestAuditHistory.STATE_SUCCESS) {
            br.setBidRequestState(Constans.BIDREQUEST_STATE_PAYING_BACK);
            //修改借款对象下的投资对象状态为还款中
            bidService.batchUpdate(br.getId(), Constans.BIDREQUEST_STATE_PAYING_BACK);
            //查出投资人账户
            List<Bid> bids = br.getBids();
            //投资人账户缓存
            Map<Long, Account> bidAccountMaps = new HashMap<Long, Account>();
            for (Bid bid : bids) {
                Long userId = bid.getBidUser().getId();
                Account bidAccount = bidAccountMaps.get(userId);
                if (bidAccount == null) {
                    bidAccount = accountService.selectById(userId);
                    bidAccountMaps.put(userId, bidAccount);
                }
                //投资人账户冻结金额减少
                bidAccount.setFreezed_amount(bidAccount.getFreezed_amount().subtract(bid.getAvailableAmount()));
                //创建投资成功流水
                accountFlowService.createBidSuccessFlow(bidAccount, bid.getAvailableAmount(), br);
                //投资人待收本金增加,
                bidAccount.setUnreceive_principal(bidAccount.getUnreceive_principal().add(bid.getAvailableAmount()));
                //投资人待收利息增加
                //本次投标产生的利息:本次投标金额/总借款金额 * 总利息
                BigDecimal currentInterest = bid.getAvailableAmount().divide(br.getBidRequestAmount(), Constans.SCAL_CAL, BigDecimal.ROUND_HALF_UP)
                        .multiply(br.getTotalRewardAmount());
                bidAccount.setUnreceive_interest(bidAccount.getUnreceive_interest().add(currentInterest));
            }
            //修改借款人账户
            for (Account account : bidAccountMaps.values()) {
                accountService.update(account);
            }

            //借款人账号
            Account borrowAccount = accountService.selectById(br.getCreateUser().getId());
            //借款人可用余额增加
            borrowAccount.setUsable_amount(borrowAccount.getUsable_amount().add(br.getBidRequestAmount()));
            //创建借款成功流水
            accountFlowService.createBorrowSuccessFlow(borrowAccount, br.getBidRequestAmount());
            //借款人授信额度减少
            borrowAccount.setRemain_borrow_limit(borrowAccount.getRemain_borrow_limit().subtract(br.getBidRequestAmount()));
            //借款人代还本息增加(总额)
            borrowAccount.setUnreturn_amount(borrowAccount.getUnreturn_amount().add(br.getBidRequestAmount()).add(br.getTotalRewardAmount()));
            //移除借款人借款在申请中的状态
            UserInfo userInfo = userInfoService.selectById(borrowAccount.getId());
            Long bitState = BitStatesUtils.removeState(userInfo.getBit_state(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
            userInfo.setBit_state(bitState);
            userInfoService.update(userInfo);
            //支付平台借款管理费
            BigDecimal managementCharge = CalculatetUtil.calAccountManagementCharge(br.getBidRequestAmount());
            borrowAccount.setUsable_amount(borrowAccount.getUsable_amount().subtract(managementCharge));
            //修改借款人账户
            accountService.update(borrowAccount);
            //创建管理费支付流水
            accountFlowService.createPayManagementFlow(borrowAccount, managementCharge);
            //创建借款人还款计划
            //创建收款人收款计划
            paymentScheduleService.creatPaymentSchedule(br);
            //平台收取管理费
            SystemAccount systemAccount = systemAccountService.getCurrent();
            systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(managementCharge));
            systemAccountService.update(systemAccount);
            systemAccountFlowService.createManagementChargeFlow(systemAccount, managementCharge, br);
        } else {
            //审核失败,同满标一审
            br.setBidRequestState(Constans.BIDREQUEST_STATE_REJECTED);
            //这个借款下面的投资对象设为审核拒绝
            bidService.batchUpdate(br.getId(), Constans.BIDREQUEST_STATE_REJECTED);
            //查询出投资列表
            //循环列表
            List<Bid> bids = br.getBids();
            for (Bid bid : bids) {
                //查出投资人账户
                Account bidAccount = accountService.selectById(bid.getBidUser().getId());
                //投资人退款,冻结金额减少
                bidAccount.setFreezed_amount(bidAccount.getFreezed_amount().subtract(bid.getAvailableAmount()));
                //可用余额增加
                bidAccount.setUsable_amount(bidAccount.getUsable_amount().add(bid.getAvailableAmount()));
                //创建解冻流水
                accountFlowService.createBidErrorFlow(bidAccount, bid.getAvailableAmount(), br);
                accountService.update(bidAccount);
                //移除用户是否有借款在申请中的状态
                UserInfo userInfo = userInfoService.selectById(br.getCreateUser().getId());
                Long bitState = BitStatesUtils.removeState(userInfo.getBit_state(), BitStatesUtils.HAS_BIDREQUEST_IN_PROCESS);
                userInfo.setBit_state(bitState);
                userInfoService.update(userInfo);
            }

        }
        update(br);
    }

    public void returnMoney(Long id) {
        PaymentSchedule ps = paymentScheduleService.selectById(id);
        //判断还款计划的状态要处于未还款状态
        AsserUtil.isTrue(ps.getState() == Constans.PAYMENT_STATE_DONE,
                "当前期借款已还");
        //当前登录用户必须是还款人本人
        AsserUtil.isTrue(ps.getBorrowUser().getId().longValue() != UserContext.getLoginInfo().getId().longValue(),
                "只能为自己还款");
        //判断用户的可用余额要大于等于本期还款总额
        Account borrowAccount = accountService.selectById(UserContext.getLoginInfo().getId());
        AsserUtil.isTrue(borrowAccount.getUsable_amount().compareTo(ps.getTotalAmount()) < 0,
                "账户余额不足");
        //执行还款
        //借款人可用余额减少
        BigDecimal returnAmount = ps.getTotalAmount();
        borrowAccount.setUsable_amount(borrowAccount.getUsable_amount().subtract(returnAmount));
        //产生还款流水
        accountFlowService.createReturnMoneyFlow(borrowAccount, returnAmount);
        //代还总额减少
        borrowAccount.setUnreturn_amount(borrowAccount.getUnreturn_amount().subtract(returnAmount));
        //剩余授信额度增加
        borrowAccount.setRemain_borrow_limit(borrowAccount.getRemain_borrow_limit().add(ps.getPrincipal()));
        //修改账户
        accountService.update(borrowAccount);
        //本期还款状态修改为已还
        ps.setState(Constans.PAYMENT_STATE_DONE);
        //本期还款计划,时间
        Date now = new Date();
        ps.setPayDate(now);
        paymentScheduleService.update(ps);
        //本期收款计划,收款时间
        paymentScheduleDetailService.batchUpdatePayDateByPsId(ps.getId(), now);
        //平台账户
        SystemAccount systemAccount = systemAccountService.getCurrent();
        //投资人
        List<PaymentScheduleDetail> details = ps.getPaymentScheduleDetails();
        //收款人账户缓存
        Map<Long, Account> bidAccountMaps = new HashMap<Long, Account>();
        for (PaymentScheduleDetail detail : details) {
            Long userId = detail.getToLoginInfoId();
            //用户账户
            Account bidAccount = bidAccountMaps.get(userId);
            if(bidAccount == null){
                bidAccount = accountService.selectById(userId);
                bidAccountMaps.put(userId,bidAccount);
            }
            //可用余额增加
            bidAccount.setUsable_amount(bidAccount.getUsable_amount().add(detail.getTotalAmount()));
            //产生收款流水
            accountFlowService.createReciveFlow(bidAccount,detail.getTotalAmount());
            //待收本金减少
            bidAccount.setUnreceive_principal(bidAccount.getUnreceive_principal().subtract(detail.getPrincipal()));
            //待收利息减少
            bidAccount.setUnreceive_interest(bidAccount.getUnreceive_interest().subtract(detail.getInterest()));
            //支付平台利息管理费,可用余额减少
            BigDecimal interestMangerCharge = CalculatetUtil.calInterestManagerCharge(detail.getInterest());
            bidAccount.setUsable_amount(bidAccount.getUsable_amount().subtract(interestMangerCharge));
            //产生支付利息管理流水
            accountFlowService.createPayInterestMangerChargeFlow(bidAccount,interestMangerCharge);
            //平台
            //平台收取利息管理费,可用余额增加
            systemAccount.setUsableAmount(systemAccount.getUsableAmount().add(interestMangerCharge));
            //产生利息管理费流水
            systemAccountFlowService.createGetInterestManagerChargeFlow(systemAccount,interestMangerCharge,ps.getBidRequestTitle());
        }
        //修改投资人账户
        for (Account acc : bidAccountMaps.values()) {
            accountService.update(acc);
        }
        //修改系统账户
        systemAccountService.update(systemAccount);
        //已还完
        int returnMonthIndex = paymentScheduleService.countByStateAndBidRequestId(Constans.PAYMENT_STATE_DONE,ps.getBidRequestId());
        BidRequest br = bidRequestMapper.selectByPrimaryKey(ps.getBidRequestId());
        if(returnMonthIndex == br.getMonthes2Return()){
            //借款对象状态修改为已还清
            br.setBidRequestState(Constans.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
            update(br);
            //借款对象下面的投标对象修改已还清
            bidService.batchUpdate(br.getId(),Constans.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
        }
    }

    public List<BidRequest> indexPublistList() {
        BidRequestQueryObject qo = new BidRequestQueryObject();
        //设置查询条件
        qo.setBidRequestState(Constans.BIDREQUEST_STATE_PUBLISH_PENDING);
        //查询5条
        qo.setPageSize(5);
        //设置排序条件
        qo.setOrderBy(" br.bidRequestState ");
        qo.setOrderType(" ASC ");
        return bidRequestMapper.selectForList(qo);
    }

    public void bidRequestPublishCheck() {
        List<BidRequest> brs = bidRequestMapper.selectPublish(Constans.BIDREQUEST_STATE_PUBLISH_PENDING,new Date());
        for (BidRequest br : brs) {
            br.setBidRequestState(Constans.BIDREQUEST_STATE_BIDDING);
            update(br);
        }
    }

    /*public void bidRequestCheck() {
        List<PaymentSchedule> pays = paymentScheduleService.bidRequestCheck(Constans.PAYMENT_STATE_NORMAL,new Date());
        for (PaymentSchedule pay : pays) {
            BigDecimal money = (pay.getMoney().add(pay.getTotalAmount())).multiply(new BigDecimal("0.008"));
            pay.setMoney(pay.getMoney().add(money));
            paymentScheduleService.update(pay);
        }
    }*/


    private void update(BidRequest br) {
        int count = bidRequestMapper.updateByPrimaryKey(br);
        if (count == 0) {
            throw new DisplayException("借款信息修改失败[乐观锁异常]");
        }
    }
}
