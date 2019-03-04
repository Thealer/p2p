package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.OrderTime;

import java.util.List;

public interface IOrderTimeService {
    List<OrderTime> selectAll();

}
