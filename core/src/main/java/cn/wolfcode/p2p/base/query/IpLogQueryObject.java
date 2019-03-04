package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.base.util.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

public class IpLogQueryObject extends QueryObject{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private int state = -1;

    private String username;

    private int userType = -1;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return StringUtils.hasLength(username)?username:null;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return DateUtil.getEndDate(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
