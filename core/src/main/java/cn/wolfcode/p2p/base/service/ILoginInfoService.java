package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.LoginInfo;

import java.util.List;

public interface ILoginInfoService {
    LoginInfo selectByid(long id);

    void register(String username,String verifycode,String password,String confirmPwd);

    boolean existUsername(String username);//是否已经存在用户名

    void login(LoginInfo loginInfo);

    List<LoginInfo> selectAuditors();
}
