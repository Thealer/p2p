package cn.wolfcode.p2p.business.domain;

import java.math.BigDecimal;
import java.util.Date;

public class AccountFlow {
    private Long id;

    //账户
    private Long accountId;
    //时间
    private Date actionTime;
    //类型
    private int actionType;
    //金额
    private BigDecimal amount;
    //交易后的账户余额
    private BigDecimal usableAmount;
    //冻结金额
    private BigDecimal freezedAmount;
    //备注
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(BigDecimal usableAmount) {
        this.usableAmount = usableAmount;
    }

    public BigDecimal getFreezedAmount() {
        return freezedAmount;
    }

    public void setFreezedAmount(BigDecimal freezedAmount) {
        this.freezedAmount = freezedAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String toString() {
        return "AccountFlow{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", actionTime=" + actionTime +
                ", actionType=" + actionType +
                ", amount=" + amount +
                ", usableAmount=" + usableAmount +
                ", freezedAmount=" + freezedAmount +
                ", note='" + note + '\'' +
                '}';
    }
}
