package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.BidRequest;

import java.math.BigDecimal;

public interface IBidService {
    /**
     *  统计用户对于一个借款的投标总额
     * @param bidRequestId 借款id
     * @param userId 用户id
     * @return
     */
    BigDecimal sumByBidRequestIdAndUserId(Long bidRequestId,Long userId);

    //保存一个投标记录
    void save(BidRequest br,BigDecimal amount);

    /**
     * 批量修改投标对象下的投标记录状态
     * @param bidRequestId
     * @param state
     */
    void batchUpdate(Long bidRequestId,int state);
}
