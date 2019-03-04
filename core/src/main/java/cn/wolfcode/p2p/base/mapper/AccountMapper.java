package cn.wolfcode.p2p.base.mapper;

import cn.wolfcode.p2p.base.domain.Account;

public interface AccountMapper {

    void insert(Account record);

    Account selectByPrimaryKey(Long id);


    int updateByPrimaryKey(Account record);
}