package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.query.IpLogQueryObject;

import java.util.List;

public interface IpLogMapper {

    void insert(IpLog record);


    int queryForCount(IpLogQueryObject qo);

    List queryForList(IpLogQueryObject qo);
}