package cn.wolfcode.p2p.business.domain;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class PlatformBankInfo {
    private Long id;

    //银行名称
    private String bankName;
    //开户人
    private String accountName;
    //开户账号
    private String accountNumber;
    //支行名称
    private String bankForkName;

    //页面数据回显
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("bankName",bankName);
        map.put("accountName",accountName);
        map.put("accountNumber",accountNumber);
        map.put("bankForkName",bankForkName);

        return JSON.toJSONString(map);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankForkName() {
        return bankForkName;
    }

    public void setBankForkName(String bankForkName) {
        this.bankForkName = bankForkName;
    }
}