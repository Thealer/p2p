package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.base.util.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RealAuthQueryObject extends QueryObject{

    private int state=-1;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

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
}
