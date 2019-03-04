package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.util.Constans;
import cn.wolfcode.p2p.business.domain.AccountFlow;
import cn.wolfcode.p2p.business.mapper.AccountFlowMapper;
import cn.wolfcode.p2p.business.service.IAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountFlowServiceImpl implements IAccountFlowService {

    @Autowired
    private AccountFlowMapper accountFlowMapper;


    private void createBaseFlow(Account account,int actionType,BigDecimal amount,String note){
        AccountFlow accountFlow = new AccountFlow();
        accountFlow.setAccountId(account.getId());
        accountFlow.setActionTime(new Date());
        accountFlow.setActionType(actionType);
        accountFlow.setAmount(amount);
        accountFlow.setFreezedAmount(account.getFreezed_amount());
        accountFlow.setNote(note);
        accountFlow.setUsableAmount(account.getUsable_amount());
        accountFlowMapper.insert(accountFlow);
    }
    public void createRechargeFlow(Account account, BigDecimal amount) {
        createBaseFlow(account, Constans.ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE,amount,"线下充值:"+amount+"元");
    }


    public void createBidFlow(Account account, BidRequest br, BigDecimal amount) {
        createBaseFlow(account,Constans.ACCOUNT_ACTIONTYPE_BID_FREEZED,amount,"投标"+br.getTitle()+"冻结:"+amount+"元");
    }

    public void createBidErrorFlow(Account account, BigDecimal amount, BidRequest br) {
        createBaseFlow(account,Constans.ACCOUNT_ACTIONTYPE_BID_UNFREEZED,amount,"投标"+br.getTitle()+"失败,取消冻结,可用余额增加:"+amount+"元");
    }

    public void createBidSuccessFlow(Account account, BigDecimal amount, BidRequest br) {
        createBaseFlow(account,Constans.ACCOUNT_ACTIONTYPE_BID_SUCCESSFUL,amount,"投标"+br.getTitle()+"成功,取消冻结:"+amount+"元");
    }

    public void createBorrowSuccessFlow(Account account, BigDecimal amount) {
        createBaseFlow(account,Constans.ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL,amount,"借款成功,可用余额增加:"+amount+"元");
    }

    public void createPayManagementFlow(Account account, BigDecimal amount) {
        createBaseFlow(account,Constans.ACCOUNT_ACTIONTYPE_CHARGE,amount,"借款成功,支付平台管理费:"+amount+"元");
    }

    public void createReturnMoneyFlow(Account account, BigDecimal amount) {
        createBaseFlow(account,Constans.ACCOUNT_ACTIONTYPE_RETURN_MONEY,amount,"还款成功,可用余额减少:"+amount+"元");
    }

    public void createReciveFlow(Account account, BigDecimal amount) {
        createBaseFlow(account,Constans.ACCOUNT_ACTIONTYPE_CALLBACK_MONEY,amount,"收款成功,可用余额增加:"+amount+"元");
    }

    public void createPayInterestMangerChargeFlow(Account account, BigDecimal amount) {
        createBaseFlow(account,Constans.ACCOUNT_ACTIONTYPE_INTEREST_SHARE,amount,"支付平台利息管理费成功,可用余额减少:"+amount+"元");
    }

    public void creatTransferFlow(Account transferAccount, BigDecimal bidRequestAmount, BidRequest br) {
        createBaseFlow(transferAccount,Constans.ACCOUNT_ACTIONTYPE_TRANSFER_SUCCESS,bidRequestAmount,br.getTitle() +",债权转让充公,可用余额增加:"+bidRequestAmount+"元");
    }

    public void createSubscribeFlow(Account currentAccount, BigDecimal bidRequestAmount, BidRequest br) {
        createBaseFlow(currentAccount,Constans.ACCOUNT_ACTIONTYPE_SUBSCRIBE_SUCCESS,bidRequestAmount,br.getTitle() +",债权认购充公,可用余额减少:"+bidRequestAmount+"元");
    }

}
