package cn.wolfcode.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RealAuth {
    //男
    public static final int SEX_MAN = 0;
    //女
    public static final int SEX_WOMAN = 1;
    //申请中
    public static final int STATE_NORMAL = 0;
    //审核成功
    public static final int STATE_SUCCESS = 1;
    //审核失败
    public static final int STATE_REJECT = 2;

    private Long id;

    private String realName;

    private int sex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bornDate;

    private String idNumber;

    private String address;

    private int state = STATE_NORMAL;

    private String image1;

    private String image2;

    private String remark;

    private Date auditTime;

    private Date applyTime;

    private LoginInfo auditor;

    private LoginInfo applier;

    //显示性别
    public String getSexDisplay(){
        return sex == SEX_MAN ? "男人" : "女人";
    }
    //显示状态
    public String getStateDisplay(){
        switch (state){
            case STATE_NORMAL : return "申请中";
            case STATE_SUCCESS : return "审核成功";
            case STATE_REJECT : return "审核失败";
        }
        return "状态异常";
    }

    //回显jsonString
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("username",getApplier().getUsername());
        map.put("realname",realName);
        map.put("idNumber",idNumber);
        map.put("sex",getSexDisplay());
        map.put("bornDate", DateFormat.getDateInstance().format(bornDate));
        map.put("address",address);
        map.put("image1",image1);
        map.put("image2",image2);

        return JSON.toJSONString(map);

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
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
}