package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.CreditTransfer;
import cn.wolfcode.p2p.business.query.CreditTransferQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CreditTransferMapper {

    int insert(CreditTransfer record);

    CreditTransfer selectByPrimaryKey(Long id);

    int updateByPrimaryKey(CreditTransfer record);

    int selectCanCreditTransForCount(CreditTransferQueryObject qo);

    List selectCanCreditTransForList(CreditTransferQueryObject qo);

    List<CreditTransfer> selectReadyCreditTransferForList(@Param("currentUserId") Long currentUserId, @Param("bidIds") Long[] bidIds);

    int selectForCount(CreditTransferQueryObject cqo);

    List selectForList(CreditTransferQueryObject cqo);
}