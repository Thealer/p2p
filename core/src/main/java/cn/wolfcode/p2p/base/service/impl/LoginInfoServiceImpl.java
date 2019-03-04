package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.DisplayException;
import cn.wolfcode.p2p.base.mapper.LoginInfoMapper;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.*;
import cn.wolfcode.p2p.base.vo.SendVerifyCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoginInfoServiceImpl implements ILoginInfoService,ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private LoginInfoMapper loginInfoMapper;


    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IAccountService accountService;

    public LoginInfo selectByid(long id) {
        return loginInfoMapper.selectByPrimaryKey(id);
    }

    public void register(String username,String verifycode,String password,String confirmPwd) {
        AsserUtil.isNull(username,"手机号不能为空");
        AsserUtil.isTrue(username.length()!= Constans.VALUE_USERNAME_LENGTH,"手机号长度为"+Constans.VALUE_USERNAME_LENGTH+"位");
        AsserUtil.isNull(verifycode,"验证码不能为空");
        AsserUtil.isNull(password,"密码不能为空");
        AsserUtil.isNull(confirmPwd,"确认密码不能为空");
        AsserUtil.isTrue(!confirmPwd.equals(password),"确认密码不一致");
        //判断注册时的手机和发送验证码的手机是否一致
        SendVerifyCodeVo vo = UserContext.getVerifyCodeVo();
        AsserUtil.isNull(vo,"无效验证码,请重新发送");
        String phone = vo.getPhone();
        AsserUtil.isTrue(!phone.equals(username),"注册手机和发送验证码的手机不一致");
        //校验验证码
        AsserUtil.isTrue(!vo.getCode().equals(verifycode),"验证码无效,请重新发送");
        //校验验证码是否过期
        Date sendTime = vo.getSendTime();
        if(DateUtil.getSecondsBetween(sendTime,new Date()) > 300){
            throw new DisplayException("验证码已过期,请重新发送");
        }

        AsserUtil.isTrue(existUsername(username),"账户已存在");

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setPassword(MD5.encode(password + username));
        loginInfo.setUsername(username);

        loginInfoMapper.insert(loginInfo);


        //初始化userinfo信息
        userInfoService.init(loginInfo);

        //初始化 account信息

        accountService.init(loginInfo);




    }

    public boolean existUsername(String username) {
        int count =  loginInfoMapper.countByUsername(username);
        return count > 0;
    }

    public void login(LoginInfo loginInfo) {
        //基本参数判断
        AsserUtil.isNull(loginInfo.getUsername(),"用户名不能为空");
        AsserUtil.isNull(loginInfo.getPassword(),"密码不能为空");

        //执行登录
        LoginInfo login = loginInfoMapper.login(loginInfo.getUsername(),MD5.encode(loginInfo.getPassword() + loginInfo.getUsername()));

        //判断是否登录成功
        AsserUtil.isNull(login,"用户名或密码错误");

        //判断用户是否锁定
        AsserUtil.isTrue(login.getState() == LoginInfo.STATE_LOCK,"该用户已被锁定,请更换用户");

        //判断用户类型
        AsserUtil.isTrue(loginInfo.getUserType() != login.getUserType(),"用户类型异常");

        //登录对象放入session中
        UserContext.setLoginInfo(login);
    }


    public List<LoginInfo> selectAuditors() {
        return loginInfoMapper.selectAuditors();
    }


    //创建默认管理员
    private void initAdmin(){
        if(existUsername("admin")){
            return;
        }
        LoginInfo admin = new LoginInfo();
        admin.setUsername("admin");
        admin.setPassword(MD5.encode("1111admin"));
        admin.setUserType(LoginInfo.USERTYPE_MGRSITE);
        loginInfoMapper.insert(admin);

    }
    //当监听到ContextRefreshedEvent容器刷新事件,触发该方法
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initAdmin();
    }
}
