package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.util.CalculatetUtil;
import cn.wolfcode.p2p.base.util.Constans;
import cn.wolfcode.p2p.business.domain.PaymentSchedule;
import cn.wolfcode.p2p.business.mapper.PaymentScheduleMapper;
import cn.wolfcode.p2p.business.query.PaymentScheduleQueryObject;
import cn.wolfcode.p2p.business.service.IPaymentScheduleDetailService;
import cn.wolfcode.p2p.business.service.IPaymentScheduleService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PaymentScheduleServiceImpl implements IPaymentScheduleService {

    @Autowired
    private PaymentScheduleMapper paymentScheduleMapper;

    @Autowired
    private IPaymentScheduleDetailService paymentScheduleDetailService;

    public void creatPaymentSchedule(BidRequest br) {
        //还款期数
        int months = br.getMonthes2Return();
        //还款截止开始时间
        Date biginDate = br.getPublishTime();
        for (int i = 0; i < months; i++) {
            //创建一个还款计划对象
            PaymentSchedule ps = new PaymentSchedule();
            ps.setBidRequestId(br.getId());
            ps.setBidRequestTitle(br.getTitle());
            ps.setBidRequestType(br.getBidRequestType());
            ps.setBorrowUser(br.getCreateUser());
            //第几期
            int monthIndex = i + 1;
            //当前还款截止时间
            ps.setDeadLine(DateUtils.addMonths(biginDate,monthIndex));
            //第几期
            ps.setMonthIndex(monthIndex);
            ps.setState(Constans.PAYMENT_STATE_NORMAL);
            ps.setReturnType(br.getReturnType());
            //计算当前期利息
            BigDecimal monthInterest = CalculatetUtil.calMonthlyInterest(br.getReturnType(),br.getBidRequestAmount(),br.getCurrentRate()
                    ,monthIndex,br.getMonthes2Return());
            ps.setInterest(monthInterest.setScale(Constans.SCAL_STORE,BigDecimal.ROUND_HALF_UP));
            //计算当前期总额
            BigDecimal monthTotalAmount = CalculatetUtil.calMonthToReturnMoney(br.getReturnType(),br.getBidRequestAmount(),br.getCurrentRate()
                    ,monthIndex,br.getMonthes2Return());
            ps.setTotalAmount(monthTotalAmount.setScale(Constans.SCAL_STORE,BigDecimal.ROUND_HALF_UP));
            //本金
            ps.setPrincipal(ps.getTotalAmount().subtract(ps.getInterest()));
            paymentScheduleMapper.insert(ps);
            //生成收款计划
            paymentScheduleDetailService.creatPaymentScheduleDetail(br,ps);

        }
    }

    public PageResult query(PaymentScheduleQueryObject qo) {
        int count = paymentScheduleMapper.selectForCount(qo);
        if (count == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(paymentScheduleMapper.selectForList(qo), count, qo.getCurrentPage(), qo.getPageSize());
    }

    public PaymentSchedule selectById(Long id) {
        return paymentScheduleMapper.selectByPrimaryKey(id);
    }

    public void update(PaymentSchedule ps) {
        paymentScheduleMapper.updateByPrimaryKey(ps);
    }

    public int countByStateAndBidRequestId(int state, Long bidRequestId) {
        return paymentScheduleMapper.countByStateAndBidRequestId(state,bidRequestId);
    }

    public List<PaymentSchedule> bidRequestCheck(int state, Date date) {
        return paymentScheduleMapper.bidRequestCheck(state,date);
    }

}
