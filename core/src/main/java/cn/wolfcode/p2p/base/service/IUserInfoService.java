package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;

public interface IUserInfoService {

    //初始化userinfo
    void init(LoginInfo loginInfo);

    //根据id查用户信息
    UserInfo selectById(Long id);

    //只修改基本资料
    void UpdateBasicInfo(UserInfo userInfo);

    //用于修改所有的用户资料
    void update(UserInfo userInfo);
}
