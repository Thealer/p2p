package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.domain.BidRequest;

public interface IBidRequestAuditHistoryService {
    //保存一个借款审核对象
    void save(int auditType, BidRequest br, int state,String remark);
}
