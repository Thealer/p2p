package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.SystemAccount;

import java.math.BigDecimal;

public interface ISystemAccountFlowService {
    //系统账户
    void createManagementChargeFlow(SystemAccount systemAccount, BigDecimal amount, BidRequest br);
    void upfate(SystemAccount systemAccount);

    //收取利息管理费流水
    void createGetInterestManagerChargeFlow(SystemAccount systemAccount, BigDecimal amount, String bidRequestTitle);
}
