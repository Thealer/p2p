package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.business.domain.PaymentSchedule;
import cn.wolfcode.p2p.business.query.PaymentScheduleQueryObject;

import java.util.Date;
import java.util.List;

public interface IPaymentScheduleService {
    //还款计划
    void creatPaymentSchedule(BidRequest br);

    //还款页面
    PageResult query(PaymentScheduleQueryObject qo);

    //还款对象
    PaymentSchedule selectById(Long id);

    //修改还款对象
    void update(PaymentSchedule ps);

    //是否还完款状态
    int countByStateAndBidRequestId(int state, Long bidRequestId);

    List<PaymentSchedule> bidRequestCheck(int paymentStateNormal, Date date);
}
