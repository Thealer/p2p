package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.PaymentSchedule;
import cn.wolfcode.p2p.business.query.PaymentScheduleQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PaymentScheduleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentSchedule record);

    PaymentSchedule selectByPrimaryKey(Long id);

    List<PaymentSchedule> selectAll();

    int updateByPrimaryKey(PaymentSchedule record);

    int selectForCount(PaymentScheduleQueryObject qo);

    List selectForList(PaymentScheduleQueryObject qo);

    int countByStateAndBidRequestId(@Param("state") int state, @Param("bidRequestId") Long bidRequestId);

    List<PaymentSchedule> bidRequestCheck(@Param("state") int state, @Param("date") Date date);

}