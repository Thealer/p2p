package cn.wolfcode.p2p.base.domain;

import cn.wolfcode.p2p.base.util.Constans;
import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.wolfcode.p2p.base.util.Constans.*;

public class BidRequest {
    private Long id;

    private int version;

    private int returnType;

    private int bidRequestType;

    private int bidRequestState;

    private BigDecimal bidRequestAmount;

    private BigDecimal currentRate;

    private int monthes2Return;

    private int bidCount;

    private BigDecimal totalRewardAmount;

    private BigDecimal currentSum = Constans.ZERO;

    private String title;

    private String description;

    private String note;

    private Date disableDate;

    private LoginInfo createUser;

    private int disableDays;

    private BigDecimal minBidAmount;

    private Date applyTime;

    private Date publishTime;

    private List<Bid> bids;

    //状态回显
    public String getBidRequestStateDisplay(){
        switch (bidRequestState){
            case BIDREQUEST_STATE_APPLY:return "申请中";
            case BIDREQUEST_STATE_PUBLISH_PENDING:return "待发布";
            case BIDREQUEST_STATE_BIDDING:return "招标中";
            case BIDREQUEST_STATE_UNDO:return "已撤销";
            case BIDREQUEST_STATE_BIDDING_OVERDUE:return "流标";
            case BIDREQUEST_STATE_APPROVE_PENDING_1:return "满标1审";
            case BIDREQUEST_STATE_APPROVE_PENDING_2:return "满标2审";
            case BIDREQUEST_STATE_REJECTED:return "满标审核被拒绝";
            case BIDREQUEST_STATE_PAYING_BACK:return "还款中";
            case BIDREQUEST_STATE_COMPLETE_PAY_BACK:return "已还清";
            case BIDREQUEST_STATE_PAY_BACK_OVERDUE:return "逾期";
            case BIDREQUEST_STATE_PUBLISH_REFUSE:return "发标审核拒绝状态";
        }
        return "状态异常";
    }

    //还需要多少金额投满
    public BigDecimal getRemainAmount(){
        return bidRequestAmount.subtract(currentSum);
    }


    //数据共享回显
    public String getJsonString(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("title",title);
        map.put("username",getCreateUser().getUsername());
        map.put("bidRequestAmount",bidRequestAmount);
        map.put("monthes2Return",monthes2Return);
        map.put("currentRate",currentRate);
        map.put("totalRewardAmount",totalRewardAmount);
        map.put("returnType",getReturnTypeDisplay());

        return JSON.toJSONString(map);
    }

    public String getReturnTypeDisplay(){
        return returnType == Constans.RETURN_TYPE_MONTH_INTEREST_PRINCIPAL ? "按月分期" : "按月到期";

    }


    //投标金额
    public int getPersent(){
        return currentSum.divide(bidRequestAmount,Constans.SCAL_CAL,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).intValue();
    }



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

    public int getReturnType() {
        return returnType;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }

    public int getBidRequestType() {
        return bidRequestType;
    }

    public void setBidRequestType(int bidRequestType) {
        this.bidRequestType = bidRequestType;
    }

    public int getBidRequestState() {
        return bidRequestState;
    }

    public void setBidRequestState(int bidRequestState) {
        this.bidRequestState = bidRequestState;
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

    public int getMonthes2Return() {
        return monthes2Return;
    }

    public void setMonthes2Return(int monthes2Return) {
        this.monthes2Return = monthes2Return;
    }

    public int getBidCount() {
        return bidCount;
    }

    public void setBidCount(int bidCount) {
        this.bidCount = bidCount;
    }

    public BigDecimal getTotalRewardAmount() {
        return totalRewardAmount;
    }

    public void setTotalRewardAmount(BigDecimal totalRewardAmount) {
        this.totalRewardAmount = totalRewardAmount;
    }

    public BigDecimal getCurrentSum() {
        return currentSum;
    }

    public void setCurrentSum(BigDecimal currentSum) {
        this.currentSum = currentSum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDisableDate() {
        return disableDate;
    }

    public void setDisableDate(Date disableDate) {
        this.disableDate = disableDate;
    }

    public LoginInfo getCreateUser() {
        return createUser;
    }

    public void setCreateUser(LoginInfo createUser) {
        this.createUser = createUser;
    }

    public int getDisableDays() {
        return disableDays;
    }

    public void setDisableDays(int disableDays) {
        this.disableDays = disableDays;
    }

    public BigDecimal getMinBidAmount() {
        return minBidAmount;
    }

    public void setMinBidAmount(BigDecimal minBidAmount) {
        this.minBidAmount = minBidAmount;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}