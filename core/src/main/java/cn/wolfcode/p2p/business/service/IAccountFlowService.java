package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.BidRequest;

import java.math.BigDecimal;

public interface IAccountFlowService {
    //保存充值流水
    void createRechargeFlow(Account account, BigDecimal amount);

    //保存一个投标流水
    void createBidFlow(Account account, BidRequest br,BigDecimal amount);

    //审核失败解冻流水
    void createBidErrorFlow(Account account,BigDecimal amount, BidRequest br);

    //投标成功流水
    void createBidSuccessFlow(Account account,BigDecimal amount, BidRequest br);

    //借款成功流水
    void createBorrowSuccessFlow(Account account,BigDecimal amount);

    //平台管理费流水
    void createPayManagementFlow(Account account, BigDecimal amount);

    //创建还款流水
    void createReturnMoneyFlow(Account account,BigDecimal amount);

    //创建收款流水
    void createReciveFlow(Account account, BigDecimal amount);

    //支付平台利息管理费流水
    void createPayInterestMangerChargeFlow(Account account, BigDecimal amount);

    //创建债权转让成功流水
    void creatTransferFlow(Account transferAccount, BigDecimal bidRequestAmount, BidRequest br);

    //创建认购成功流水
    void createSubscribeFlow(Account currentAccount, BigDecimal bidRequestAmount, BidRequest br);
}
