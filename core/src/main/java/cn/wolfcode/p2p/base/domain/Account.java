package cn.wolfcode.p2p.base.domain;

import cn.wolfcode.p2p.base.util.Constans;

import java.math.BigDecimal;
/**
 *
 */


public class Account {

    private Long id;

    private String trade_password;

    private BigDecimal usable_amount = Constans.ZERO;

    private BigDecimal freezed_amount = Constans.ZERO;

    private BigDecimal unreceive_interest = Constans.ZERO;

    private BigDecimal unreceive_principal = Constans.ZERO;

    private BigDecimal unreturn_amount = Constans.ZERO;



    private BigDecimal remain_borrow_limit = Constans.BORROW_LIMIT;

    private BigDecimal borrow_limit = Constans.BORROW_LIMIT;

    private int version;


    public BigDecimal getTotalAmount(){
        return usable_amount.add(freezed_amount).add(unreceive_principal);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrade_password() {
        return trade_password;
    }

    public void setTrade_password(String trade_password) {
        this.trade_password = trade_password;
    }

    public BigDecimal getUsable_amount() {
        return usable_amount;
    }

    public void setUsable_amount(BigDecimal usable_amount) {
        this.usable_amount = usable_amount;
    }

    public BigDecimal getFreezed_amount() {
        return freezed_amount;
    }

    public void setFreezed_amount(BigDecimal freezed_amount) {
        this.freezed_amount = freezed_amount;
    }

    public BigDecimal getUnreceive_interest() {
        return unreceive_interest;
    }

    public void setUnreceive_interest(BigDecimal unreceive_interest) {
        this.unreceive_interest = unreceive_interest;
    }

    public BigDecimal getUnreceive_principal() {
        return unreceive_principal;
    }

    public void setUnreceive_principal(BigDecimal unreceive_principal) {
        this.unreceive_principal = unreceive_principal;
    }

    public BigDecimal getUnreturn_amount() {
        return unreturn_amount;
    }

    public void setUnreturn_amount(BigDecimal unreturn_amount) {
        this.unreturn_amount = unreturn_amount;
    }

    public BigDecimal getRemain_borrow_limit() {
        return remain_borrow_limit;
    }

    public void setRemain_borrow_limit(BigDecimal remain_borrow_limit) {
        this.remain_borrow_limit = remain_borrow_limit;
    }

    public BigDecimal getBorrow_limit() {
        return borrow_limit;
    }

    public void setBorrow_limit(BigDecimal borrow_limit) {
        this.borrow_limit = borrow_limit;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}