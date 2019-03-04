package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.business.domain.PaymentSchedule;

import java.util.Date;

public interface IPaymentScheduleDetailService {
    //创建收款计划
    void creatPaymentScheduleDetail(BidRequest br, PaymentSchedule ps);

    //修改当前期的收款时间和收款计划
    void batchUpdatePayDateByPsId(Long id, Date now);

    //修改当前bidId对应的transfer为已转让
    void batchUpdateTransferByBidId(Long bidId, int transferStateYet);

    //修改债权标对应的收款计划的收款人为认购人
    void batchUpdateToLoginInfoIdByBidId(Long bidId, Long id);
}
