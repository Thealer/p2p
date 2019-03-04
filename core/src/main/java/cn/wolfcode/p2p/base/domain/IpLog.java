package cn.wolfcode.p2p.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class IpLog {

    public static final int ERROR_STATE = 1;
    public static final int SUCCESS_STATE = 0;

    private Long id;

    private String ip;

    private int state = SUCCESS_STATE;

    private String username;


    private int userType;


    public String getUserTypeName(){
        return userType == LoginInfo.USERTYPE_MGRSITE?"后台用户":"前台用户";
    }

    public String getStateName(){
        return state == SUCCESS_STATE ? "登录成功" : "登录失败";
    }
    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date loginTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}