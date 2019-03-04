package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.mapper.AccountMapper;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.util.AsserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountMapper accountMapper;

    public void init(LoginInfo loginInfo) {
        Account account = new Account();
        account.setId(loginInfo.getId());
        account.setTrade_password(loginInfo.getPassword());
        accountMapper.insert(account);
    }

    public Account selectById(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    public void update(Account account) {
        AsserUtil.isTrue(0 == accountMapper.updateByPrimaryKey(account),"修改失败,乐观锁异常");
    }
}
