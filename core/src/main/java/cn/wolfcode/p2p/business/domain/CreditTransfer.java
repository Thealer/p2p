package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.LoginInfo;

import java.math.BigDecimal;
import java.util.Date;

public class CreditTransfer {

    private Long id;
    private int version;// 乐观锁
    private Long bidId;// 对应投标id
    private Long bidRequestId;// 对应借款
    private BigDecimal bidRequestAmount;// 认购本金
    private BigDecimal currentRate;// 借款利息
    private int returnType;// 还款方式
    private int monthIndex;// 总还款期数
    private int remainMonthIndex;// 剩余还款期数
    private BigDecimal remainInterest;// 剩余利息
    private Date closestDeadLine;// 最近还款时间
    private String bidRequestTitle;// 原借款名称
    private int bidRequestState;// 债权状态

    private LoginInfo transFrom;// 转出人
    private LoginInfo transTo;// 接手人
    private Date publishDate;// 发布时间
    private Date transDate;// 接手时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public Long getBidRequestId() {
        return bidRequestId;
    }

    public void setBidRequestId(Long bidRequestId) {
        this.bidRequestId = bidRequestId;
    }

    public BigDecimal getBidRequestAmount() {
        return bidRequestAmount;
    }

    public void setBidRequestAmount(BigDecimal bidRequestAmount) {
        this.bidRequestAmount = bidRequestAmount;
    }

    public BigDecimal getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(BigDecimal currentRate) {
        this.currentRate = currentRate;
    }

    public int getReturnType() {
        return returnType;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }

    public int getMonthIndex() {
        return monthIndex;
    }

    public void setMonthIndex(int monthIndex) {
        this.monthIndex = monthIndex;
    }

    public int getRemainMonthIndex() {
        return remainMonthIndex;
    }

    public void setRemainMonthIndex(int remainMonthIndex) {
        this.remainMonthIndex = remainMonthIndex;
    }

    public BigDecimal getRemainInterest() {
        return remainInterest;
    }

    public void setRemainInterest(BigDecimal remainInterest) {
        this.remainInterest = remainInterest;
    }

    public Date getClosestDeadLine() {
        return closestDeadLine;
    }

    public void setClosestDeadLine(Date closestDeadLine) {
        this.closestDeadLine = closestDeadLine;
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

    public LoginInfo getTransFrom() {
        return transFrom;
    }

    public void setTransFrom(LoginInfo transFrom) {
        this.transFrom = transFrom;
    }

    public LoginInfo getTransTo() {
        return transTo;
    }

    public void setTransTo(LoginInfo transTo) {
        this.transTo = transTo;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }
}