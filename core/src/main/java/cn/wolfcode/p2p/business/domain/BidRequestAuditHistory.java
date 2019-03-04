package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.LoginInfo;

import java.util.Date;

//借款审核对象
public class BidRequestAuditHistory {
    //申请中
    public static final int STATE_NORMAL = 0;
    //审核成功
    public static final int STATE_SUCCESS = 1;
    //审核失败
    public static final int STATE_REJECT = 2;

    //发标审核
    public static final int AUDIT_TYPE_PUBLISH = 0;
    //满标一审
    public static final int AUDIT_TYPE_AUDIT1 = 1;
    //满标二审
    public static final int AUDIT_TYPE_AUDIT2 = 2;

    private Long id;
    private int state = STATE_NORMAL;
    private String remark;
    private Date auditTime;
    private Date applyTime;
    private LoginInfo auditor;
    private LoginInfo applier;
    //审核类型
    private int auditType;
    //借款id
    private Long bidRequestId;

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

    public int getAuditType() {
        return auditType;
    }

    public void setAuditType(int auditType) {
        this.auditType = auditType;
    }

    public Long getBidRequestId() {
        return bidRequestId;
    }

    public void setBidRequestId(Long bidRequestId) {
        this.bidRequestId = bidRequestId;
    }
}
