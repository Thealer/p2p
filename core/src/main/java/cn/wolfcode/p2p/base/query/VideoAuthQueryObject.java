package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.base.util.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

//视频认证查询对象
public class VideoAuthQueryObject extends QueryObject{

    private int state = -1;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    //预约用户名
    private String username;
    //审核人
    private Long auditorId;

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getUsername() {
        return StringUtils.hasLength(username)?username:null;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
