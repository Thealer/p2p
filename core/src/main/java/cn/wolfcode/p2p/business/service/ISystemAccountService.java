package cn.wolfcode.p2p.business.service;

import cn.wolfcode.p2p.business.domain.SystemAccount;

public interface ISystemAccountService {
    SystemAccount getCurrent();

    void update(SystemAccount systemAccount);
}
