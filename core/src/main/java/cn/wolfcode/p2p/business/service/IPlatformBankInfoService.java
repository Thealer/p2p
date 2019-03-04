package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.business.domain.PlatformBankInfo;
import cn.wolfcode.p2p.business.query.PlatformBankInfoQueryObject;

import java.util.List;

public interface IPlatformBankInfoService {
    //银行卡信息
    PageResult query(PlatformBankInfoQueryObject qo);

    //保存银行卡
    void save(PlatformBankInfo bank);

    List<PlatformBankInfo> selectAll();
}
