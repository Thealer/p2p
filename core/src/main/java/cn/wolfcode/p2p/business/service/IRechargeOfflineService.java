package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.business.domain.RechargeOffline;
import cn.wolfcode.p2p.business.query.RechargeQueryObject;

public interface IRechargeOfflineService {
    //线下充值提交
    void rechargeSave(RechargeOffline rechargeOffline);

    //充值审核数据
    PageResult query(RechargeQueryObject qo);

    //审核
    void audit(Long id, int state, String remark);
}
