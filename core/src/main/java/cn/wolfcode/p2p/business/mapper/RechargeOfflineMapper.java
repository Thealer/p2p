package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.RechargeOffline;
import cn.wolfcode.p2p.business.query.RechargeQueryObject;

import java.util.List;

public interface RechargeOfflineMapper {

    int insert(RechargeOffline record);

    RechargeOffline selectByPrimaryKey(Long id);


    int updateByPrimaryKey(RechargeOffline record);

    int selectForCount(RechargeQueryObject qo);

    List selectForList(RechargeQueryObject qo);
}