package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.util.AsserUtil;
import cn.wolfcode.p2p.base.util.Constans;
import cn.wolfcode.p2p.business.domain.SystemAccount;
import cn.wolfcode.p2p.business.domain.SystemAccountFlow;
import cn.wolfcode.p2p.business.mapper.SystemAccountFlowMapper;
import cn.wolfcode.p2p.business.mapper.SystemAccountMapper;
import cn.wolfcode.p2p.business.service.ISystemAccountFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class SystemAccountFlowServiceImpl implements ISystemAccountFlowService {
    @Autowired
    private SystemAccountFlowMapper systemAccountFlowMapper;

    @Autowired
    private SystemAccountMapper systemAccountMapper;

    public void createBaseFlow(int actionType,BigDecimal amount,SystemAccount systemAccount, String note) {
        SystemAccountFlow flow = new SystemAccountFlow();
        flow.setCreatedDate(new Date());
        flow.setActionType(actionType);
        flow.setAmount(amount);
        flow.setFreezedAmount(systemAccount.getFreezedAmount());
        flow.setUsableAmount(systemAccount.getUsableAmount());
        flow.setNote(note);
        systemAccountFlowMapper.insert(flow);
    }
    public void upfate(SystemAccount systemAccount){
        AsserUtil.isTrue(0 == systemAccountMapper.updateByPrimaryKey(systemAccount),
                "系统账户修改失败,乐观锁异常");
    }

    public void createGetInterestManagerChargeFlow(SystemAccount systemAccount, BigDecimal amount, String bidRequestTitle) {
        createBaseFlow(Constans.SYSTEM_ACCOUNT_ACTIONTYPE_INTREST_MANAGE_CHARGE,amount,systemAccount,
                "["+bidRequestTitle+"] 还款,收取投资人利息管理费"+amount+"元");
    }

    public void createManagementChargeFlow(SystemAccount systemAccount, BigDecimal amount, BidRequest br) {
        createBaseFlow(Constans.SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE,amount,systemAccount,
                "收取["+br.getTitle()+"] 借款管理费:"+amount+"元");
    }
}
