package cn.wolfcode.p2p.business.mapper;

import cn.wolfcode.p2p.business.domain.SystemAccount;

public interface SystemAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemAccount record);

    SystemAccount selectByPrimaryKey(Long id);


    int updateByPrimaryKey(SystemAccount record);

    SystemAccount getCurrent();

}