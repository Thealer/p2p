package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import com.alibaba.fastjson.JSON;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RechargeOffline {

    //申请中
    public static final int STATE_NORMAL = 0;
    //审核成功
    public static final int STATE_SUCCESS = 1;
    //审核失败
    public static final int STATE_REJECT = 2;


    private Long id;

    private int state = STATE_NORMAL;

    private String remark;

    private Date auditTime;

    private Date applyTime;

    private LoginInfo auditor;

    private LoginInfo applier;

    //交易号
    private String tradeCode;

    //交易时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tradeTime;

    //充值金额
    private BigDecimal amount;

    //充值备注
    private String note;

    //充值银行
    private PlatformBankInfo bankInfo;


    //显示状态

    public String getStateDisplay(){
        switch (state){
            case STATE_NORMAL : return "申请中";
            case STATE_SUCCESS : return "审核成功";
            case STATE_REJECT : return "审核失败";
        }
        return "状态异常";
    }

    //页面数据回显
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("username",getApplier().getUsername());
        map.put("tradeCode",tradeCode);
        map.put("amount",amount);
        map.put("tradeTime",tradeTime);
        return JSON.toJSONString(map);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public LoginInfo getAuditor() {
        return auditor;
    }

    public void setAuditor(LoginInfo auditor) {
        this.auditor = auditor;
    }

    public LoginInfo getApplier() {
        return applier;
    }

    public void setApplier(LoginInfo applier) {
        this.applier = applier;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PlatformBankInfo getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(PlatformBankInfo bankInfo) {
        this.bankInfo = bankInfo;
    }
}