package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.Bid;
import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.mapper.BidMapper;
import cn.wolfcode.p2p.base.service.IBidService;
import cn.wolfcode.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class BidServiceImpl implements IBidService {
    @Autowired
    private BidMapper bidMapper;

    public BigDecimal sumByBidRequestIdAndUserId(Long bidRequestId, Long userId) {
        return bidMapper.sumByBidRequestIdAndUserId(bidRequestId,userId);
    }

    public void save(BidRequest br, BigDecimal amount) {
        Bid bid = new Bid();
        bid.setActualRate(br.getCurrentRate());
        bid.setAvailableAmount(amount);
        bid.setBidRequestId(br.getId());
        bid.setBidRequestState(br.getBidRequestState());
        bid.setBidRequestTitle(br.getTitle());
        bid.setBidTime(new Date());
        bid.setBidUser(UserContext.getLoginInfo());
        bidMapper.insert(bid);
    }

    public void batchUpdate(Long bidRequestId, int state) {
        bidMapper.batchUpdate(bidRequestId,state);
    }
}
