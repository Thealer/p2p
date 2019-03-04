package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.business.query.CreditTransferQueryObject;

public interface ICreditTransferService {
    //可转让债权
    PageResult canCreditTrans(CreditTransferQueryObject qo);

    //转让债权
    void creditTransfer(Long[] bidId);

    //债权认购页面数据
    PageResult query(CreditTransferQueryObject cqo);

    //债权认购
    void subscribe(Long id);
}
