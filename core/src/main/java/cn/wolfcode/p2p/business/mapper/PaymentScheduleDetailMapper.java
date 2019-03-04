package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.PaymentScheduleDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PaymentScheduleDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentScheduleDetail record);

    PaymentScheduleDetail selectByPrimaryKey(Long id);

    List<PaymentScheduleDetail> selectAll();

    int updateByPrimaryKey(PaymentScheduleDetail record);

    void batchUpdatePayDateByPsId(@Param("psId") Long psId, @Param("now") Date now);

    void batchUpdateTransferByBidId(@Param("bidId") Long bidId, @Param("transferState") int transferState);

    void batchUpdateToLoginInfoIdByBidId(@Param("bidId") Long bidId, @Param("userId") Long userId);
}