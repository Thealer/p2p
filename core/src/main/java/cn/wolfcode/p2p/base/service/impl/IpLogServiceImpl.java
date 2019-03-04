package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.mapper.IpLogMapper;
import cn.wolfcode.p2p.base.query.IpLogQueryObject;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.service.IIpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IpLogServiceImpl implements IIpLogService {

    @Autowired
    private IpLogMapper ipLogMapper;
    public void save(String username, int state, String ip,int userType) {
        IpLog ipLog = new IpLog();
        ipLog.setIp(ip);
        ipLog.setLoginTime(new Date());
        ipLog.setState(state);
        ipLog.setUsername(username);
        ipLog.setUserType(userType);
        ipLogMapper.insert(ipLog);
    }

    public PageResult query(IpLogQueryObject qo) {
        int count = ipLogMapper.queryForCount(qo);
        if(count == 0){
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(ipLogMapper.queryForList(qo),count,qo.getCurrentPage(),qo.getPageSize());
    }
}
