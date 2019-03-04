package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.Bid;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface BidMapper {

    int insert(Bid record);

    Bid selectByPrimaryKey(Long id);


    int updateByPrimaryKey(Bid record);

    BigDecimal sumByBidRequestIdAndUserId(@Param("bidRequestId") Long bidRequestId, @Param("userId") Long userId);

    void batchUpdate(@Param("bidRequestId") Long bidRequestId, @Param("state") int state);
}