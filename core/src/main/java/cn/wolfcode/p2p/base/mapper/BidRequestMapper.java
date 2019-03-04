package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.query.BidRequestQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BidRequestMapper {

    int insert(BidRequest record);

    BidRequest selectByPrimaryKey(Long id);


    int updateByPrimaryKey(BidRequest record);

    int selectForCount(BidRequestQueryObject qo);

    List selectForList(BidRequestQueryObject qo);

    List<BidRequest> selectPublish(@Param("state") int state, @Param("date") Date date);
}