package cn.wolfcode.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VideoAuth {

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

    //预约的认证时间日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    //预约的认证时间段
    private OrderTime orderTime;


    //显示状态
    public String getStateDisplay(){
        switch (state){
            case STATE_NORMAL : return "申请中";
            case STATE_SUCCESS : return "审核成功";
            case STATE_REJECT : return "审核失败";
        }
        return "状态异常";
    }

    //页面回显
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("username",getApplier().getUsername());
        map.put("orderTime",getOrderTimeString());

        return JSON.toJSONString(map);

    }

    public String getOrderTimeString() {
        return DateFormat.getDateInstance().format(orderDate) +" "+ orderTime.getBegin() + " - " + orderTime.getEnd();
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(OrderTime orderTime) {
        this.orderTime = orderTime;
    }
}