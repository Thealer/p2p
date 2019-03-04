package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.Bid;
import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.util.Constans;
import cn.wolfcode.p2p.business.domain.PaymentSchedule;
import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import cn.wolfcode.p2p.business.mapper.PaymentScheduleDetailMapper;
import cn.wolfcode.p2p.business.service.IPaymentScheduleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PaymentScheduleDetailServiceImpl implements IPaymentScheduleDetailService {
    @Autowired
    private PaymentScheduleDetailMapper paymentScheduleDetailMapper;

    public void creatPaymentScheduleDetail(BidRequest br, PaymentSchedule ps) {
        List<Bid> bids = br.getBids();
        for (int i = 0; i < bids.size(); i++) {
            Bid bid = bids.get(i);
            PaymentScheduleDetail detai = new PaymentScheduleDetail();
            detai.setBidAmount(bid.getAvailableAmount());
            detai.setBidId(bid.getId());
            detai.setBidRequestId(br.getId());
            //当前期收款截止时间
            detai.setDeadLine(ps.getDeadLine());
            detai.setFromLoginInfo(br.getCreateUser());
            detai.setMonthIndex(ps.getMonthIndex());
            detai.setPaymentScheduleId(ps.getId());
            detai.setReturnType(br.getReturnType());
            detai.setToLoginInfoId(bid.getBidUser().getId());
            //当前这一个收款的本金:当前bid投标金额/借款总额*当前期还款本金
            BigDecimal scale = bid.getAvailableAmount().divide(br.getBidRequestAmount(), Constans.SCAL_CAL,BigDecimal.ROUND_HALF_UP);
            BigDecimal currentPrincipal = scale.multiply(ps.getPrincipal());
            detai.setPrincipal(currentPrincipal.setScale(Constans.SCAL_STORE,BigDecimal.ROUND_HALF_UP));

            //当前这一个收款的利息;当前bid投标金额/借款总额*当前期还款利息
            BigDecimal currentInterest = scale.multiply(ps.getInterest());
            detai.setInterest(currentInterest.setScale(Constans.SCAL_STORE,BigDecimal.ROUND_HALF_UP));

            detai.setTotalAmount(detai.getPrincipal().add(detai.getInterest()));
            paymentScheduleDetailMapper.insert(detai);


        }
    }

    public void batchUpdatePayDateByPsId(Long id, Date now) {
        paymentScheduleDetailMapper.batchUpdatePayDateByPsId(id,now);
    }

    public void batchUpdateTransferByBidId(Long bidId, int transferState) {
        paymentScheduleDetailMapper.batchUpdateTransferByBidId(bidId,transferState);
    }

    public void batchUpdateToLoginInfoIdByBidId(Long bidId, Long id) {
        paymentScheduleDetailMapper.batchUpdateToLoginInfoIdByBidId(bidId,id);
    }
}
