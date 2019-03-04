package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IBidRequestService;
import cn.wolfcode.p2p.base.util.AsserUtil;
import cn.wolfcode.p2p.base.util.Constans;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.domain.CreditTransfer;
import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import cn.wolfcode.p2p.business.mapper.CreditTransferMapper;
import cn.wolfcode.p2p.business.query.CreditTransferQueryObject;
import cn.wolfcode.p2p.business.service.IAccountFlowService;
import cn.wolfcode.p2p.business.service.ICreditTransferService;
import cn.wolfcode.p2p.business.service.IPaymentScheduleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CreditTransferServiceImpl implements ICreditTransferService {
    @Autowired
    private CreditTransferMapper creditTransferMapper;

    @Autowired
    private IPaymentScheduleDetailService paymentScheduleDetailService;
    @Autowired
    private IBidRequestService bidRequestService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountFlowService accountFlowService;

    public PageResult canCreditTrans(CreditTransferQueryObject qo) {
        int count = creditTransferMapper.selectCanCreditTransForCount(qo);
        if (count == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(creditTransferMapper.selectCanCreditTransForList(qo), count, qo.getCurrentPage(), qo.getPageSize());
    }

    public void creditTransfer(Long[] bidId) {
        List<CreditTransfer> creditTransfers = creditTransferMapper.selectReadyCreditTransferForList(UserContext.getLoginInfo().getId(),bidId);
        for (CreditTransfer transfer : creditTransfers) {
            transfer.setTransFrom(UserContext.getLoginInfo());
            transfer.setBidRequestState(Constans.BIDREQUEST_STATE_BIDDING);
            transfer.setPublishDate(new Date());
            creditTransferMapper.insert(transfer);
            //修改当前bidId对应的transfer为已转让
            paymentScheduleDetailService.batchUpdateTransferByBidId(transfer.getBidId(), PaymentScheduleDetail.TRANSFER_STATE_YET);

        }
    }

    public PageResult query(CreditTransferQueryObject cqo) {
        int count = creditTransferMapper.selectForCount(cqo);
        if (count == 0) {
            return PageResult.empty(cqo.getPageSize());
        }
        return new PageResult(creditTransferMapper.selectForList(cqo), count, cqo.getCurrentPage(), cqo.getPageSize());
    }

    public void subscribe(Long id) {
        //判断
        //判断债权状态处于招标中
        CreditTransfer transfer = creditTransferMapper.selectByPrimaryKey(id);
        AsserUtil.isTrue(transfer.getBidRequestState() != Constans.BIDREQUEST_STATE_BIDDING,
                "该标不处于待认购状态,请选择其他债权");
        //借款人不能认购
        //当前登录用户
        LoginInfo currentUser = UserContext.getLoginInfo();
        //借款对象
        BidRequest br = bidRequestService.seletById(transfer.getBidRequestId());
        //借款人
        LoginInfo borrowUser = br.getCreateUser();
        AsserUtil.isTrue(currentUser.getId().longValue() == borrowUser.getId().longValue(),
                "借款人不能认购");
        //转让人不能认购
        AsserUtil.isTrue(currentUser.getId().longValue() == transfer.getTransFrom().getId().longValue(),
                "转让人不能认购自己转让的债权");
        //可用余额大于等于债权标金额
        //当前账户
        Account currentAccount = accountService.selectById(currentUser.getId());
        AsserUtil.isTrue(currentAccount.getUsable_amount().compareTo(transfer.getBidRequestAmount()) < 0,
                "账户余额不足");

        //转让人账户
        Account transferAccount = accountService.selectById(transfer.getTransFrom().getId());
        //可用余额增加 + 债权金额
        transferAccount.setUsable_amount(transferAccount.getUsable_amount().add(transfer.getBidRequestAmount()));
        //创建流水
        accountFlowService.creatTransferFlow(transferAccount,transfer.getBidRequestAmount(),br);
        //待收本金减少
        transferAccount.setUnreceive_principal(transferAccount.getUnreceive_principal().subtract(transfer.getBidRequestAmount()));
        //待收利息减少
        transferAccount.setUnreceive_interest(transferAccount.getUnreceive_interest().subtract(transfer.getRemainInterest()));
        //债权标的状态修改为一认购,修改认购时间,修改认购人
        transfer.setBidRequestState(Constans.BIDREQUEST_STATE_COMPLETE_PAY_BACK);
        transfer.setTransTo(currentUser);
        transfer.setTransDate(new Date());
        update(transfer);
        //修改这个债权转让标对应的收款计划的转让状态为0: 可用被转让
        paymentScheduleDetailService.batchUpdateTransferByBidId(transfer.getBidId(),PaymentScheduleDetail.TRANSFER_STATE_NORMAL);
        //认购人
        //可用余额减少 - 债权金额
        currentAccount.setUsable_amount(currentAccount.getUsable_amount().subtract(transfer.getBidRequestAmount()));
        //创建认购流水
        accountFlowService.createSubscribeFlow(currentAccount,transfer.getBidRequestAmount(),br);
        //待收本金增加
        currentAccount.setUnreceive_principal(currentAccount.getUnreceive_principal().add(transfer.getBidRequestAmount()));
        //待收利息增加
        currentAccount.setUnreceive_interest(currentAccount.getUnreceive_interest().add(transfer.getRemainInterest()));
        //修改这个债权标对应的收款计划的收款人为:认购人
        paymentScheduleDetailService.batchUpdateToLoginInfoIdByBidId(transfer.getBidId(),currentAccount.getId());
    }

    private void update(CreditTransfer transfer) {
        AsserUtil.isTrue(0 == creditTransferMapper.updateByPrimaryKey(transfer), 
                "债权信息修改失败,乐观锁异常");
    }
}
