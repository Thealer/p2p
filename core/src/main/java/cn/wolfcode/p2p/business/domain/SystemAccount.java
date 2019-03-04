package cn.wolfcode.p2p.business.domain;


import cn.wolfcode.p2p.base.util.Constans;

import java.math.BigDecimal;

//系统账户
public class SystemAccount {

    private Long id;
    //可用余额
    private BigDecimal usableAmount = Constans.ZERO;

    //冻结金额
    private BigDecimal freezedAmount = Constans.ZERO;;

    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
