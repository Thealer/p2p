package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.domain.BidRequestAuditHistory;
import cn.wolfcode.p2p.business.mapper.BidRequestAuditHistoryMapper;
import cn.wolfcode.p2p.business.service.IBidRequestAuditHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BidRequestAuditHistoryServiceImpl implements IBidRequestAuditHistoryService {
    @Autowired
    private BidRequestAuditHistoryMapper bidRequestAuditHistoryMapper;

    public void save(){

    }

    public void save(int auditType, BidRequest br, int state,String remark) {
        BidRequestAuditHistory history = new BidRequestAuditHistory();
        history.setApplier(br.getCreateUser());
        history.setApplyTime(br.getApplyTime());
        history.setAuditor(UserContext.getLoginInfo());
        history.setAuditTime(new Date());
        history.setAuditType(auditType);
        history.setBidRequestId(br.getId());
        history.setRemark(remark);
        history.setState(state);

        bidRequestAuditHistoryMapper.insert(history);
    }
}
