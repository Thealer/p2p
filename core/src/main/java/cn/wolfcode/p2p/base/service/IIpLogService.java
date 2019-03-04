package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.query.IpLogQueryObject;
import cn.wolfcode.p2p.base.query.PageResult;

/**
 * 保存登录日志
 */
public interface IIpLogService {

    void save(String username, int state, String remoteAddr,int userType);

    PageResult query(IpLogQueryObject qo);
}
