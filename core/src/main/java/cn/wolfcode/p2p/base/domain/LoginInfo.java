package cn.wolfcode.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo {

    //状态正常
    public static final int STATE_NORMAL = 0;
    //状态异常(锁定)
    public static final int STATE_LOCK = 1;

    //前台用户
    public static final int USERTYPE_WEBSITE = 0;

    //后台用户
    public static final int USERTYPE_MGRSITE = 1;

    private Long id;
    private String username;
    private String password;
    private int state = STATE_NORMAL;//状态

    //用户类型
    private int userType = USERTYPE_WEBSITE;

    //是否是客服人员

    private boolean auditor = false;
}
