package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.mapper.PlatformBankInfoMapper;
import cn.wolfcode.p2p.business.query.PlatformBankInfoQueryObject;
import cn.wolfcode.p2p.business.service.IPlatformBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformBankInfoServiceImpl implements IPlatformBankInfoService {
    @Autowired
    private PlatformBankInfoMapper platformBankInfoMapper;

    public PageResult query(PlatformBankInfoQueryObject qo) {
        int count = platformBankInfoMapper.selectForCount(qo);
        if (count == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(platformBankInfoMapper.selectForList(qo), count, qo.getCurrentPage(), qo.getPageSize());
    }

    public void save(PlatformBankInfo bank) {
        if (bank.getId() == null) {
            platformBankInfoMapper.insert(bank);
        }else{
            platformBankInfoMapper.updateByPrimaryKey(bank);
        }


    }

    public List<PlatformBankInfo> selectAll() {
        return platformBankInfoMapper.selectAll();
    }
}
