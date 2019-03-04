package cn.wolfcode.p2p.business.query;

import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.base.util.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

public class RechargeQueryObject extends QueryObject{

    private int state = -1;

    //银行
    private long bankInfoId = -1;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String tradeCode;



    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getBankInfoId() {
        return bankInfoId;
    }

    public void setBankInfoId(long bankInfoId) {
        this.bankInfoId = bankInfoId;
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

    public String getTradeCode() {
        return StringUtils.hasLength(tradeCode)?tradeCode:null;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String toString() {
        return "RechargeQueryObject{" +
                "state=" + state +
                ", bankInfoId=" + bankInfoId +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", tradeCode='" + tradeCode + '\'' +
                '}';
    }
}
