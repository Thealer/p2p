package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.LoginInfo;

public interface IAccountService {

    void init(LoginInfo loginInfo);

    Account selectById(Long id);

    //修改账户信息
    void update(Account account);
}
