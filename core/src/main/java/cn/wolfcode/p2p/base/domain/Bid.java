package cn.wolfcode.p2p.base.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Bid {
    private Long id;

    private BigDecimal actualRate;

    private BigDecimal availableAmount;

    private Long bidRequestId;

    private LoginInfo bidUser;

    private Date bidTime;

    private String bidRequestTitle;

    private int bidRequestState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getActualRate() {
        return actualRate;
    }

    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Long getBidRequestId() {
        return bidRequestId;
    }

    public void setBidRequestId(Long bidRequestId) {
        this.bidRequestId = bidRequestId;
    }

    public LoginInfo getBidUser() {
        return bidUser;
    }

    public void setBidUser(LoginInfo bidUser) {
        this.bidUser = bidUser;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    public String getBidRequestTitle() {
        return bidRequestTitle;
    }

    public void setBidRequestTitle(String bidRequestTitle) {
        this.bidRequestTitle = bidRequestTitle;
    }

    public int getBidRequestState() {
        return bidRequestState;
    }

    public void setBidRequestState(int bidRequestState) {
        this.bidRequestState = bidRequestState;
    }
}