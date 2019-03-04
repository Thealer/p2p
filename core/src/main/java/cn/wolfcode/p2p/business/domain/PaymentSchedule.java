package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.util.Constans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 还款计划对象，记录借款每月的还款信息(该信息针对借款用户，paymentscheduledetail针对投资人)
 * 
 * @author stef
 */
public class PaymentSchedule {

	private Long id;

	// 对应借款
	private Long bidRequestId;
	//借款名称
	private String bidRequestTitle;
	// 还款人
	private LoginInfo borrowUser;
	// 本期还款截止期限
	private Date deadLine;
	// 还款时间
	private Date payDate;

	// 本期还款总金额，利息 +本金
	private BigDecimal totalAmount = Constans.ZERO;
	// 本期还款本金
	private BigDecimal principal = Constans.ZERO;
	// 本期还款总利息
	private BigDecimal interest = Constans.ZERO;
	// 第几期 (即第几个月)
	private int monthIndex;
	// 本期还款状态（默认正常待还）
	private int state = Constans.PAYMENT_STATE_NORMAL;
	// 借款类型
	private int bidRequestType;
	// 还款方式，等同借款(BidRequest)中的还款方式
	private int returnType;
	//滞纳金
	private BigDecimal money;

	// 本期还款计划对应的收款计划明细
	private List<PaymentScheduleDetail> paymentScheduleDetails = new ArrayList<PaymentScheduleDetail>();

	public String getStateDisplay() {
		switch (state) {
		case Constans.PAYMENT_STATE_NORMAL:
			return "正常待还";
		case Constans.PAYMENT_STATE_DONE:
			return "已还";
		case Constans.PAYMENT_STATE_OVERDUE:
			return "逾期";
		default:
			return "未知";
		}
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBidRequestId() {
		return bidRequestId;
	}

	public void setBidRequestId(Long bidRequestId) {
		this.bidRequestId = bidRequestId;
	}

	public String getBidRequestTitle() {
		return bidRequestTitle;
	}

	public void setBidRequestTitle(String bidRequestTitle) {
		this.bidRequestTitle = bidRequestTitle;
	}

	public LoginInfo getBorrowUser() {
		return borrowUser;
	}

	public void setBorrowUser(LoginInfo borrowUser) {
		this.borrowUser = borrowUser;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public int getMonthIndex() {
		return monthIndex;
	}

	public void setMonthIndex(int monthIndex) {
		this.monthIndex = monthIndex;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getBidRequestType() {
		return bidRequestType;
	}

	public void setBidRequestType(int bidRequestType) {
		this.bidRequestType = bidRequestType;
	}

	public int getReturnType() {
		return returnType;
	}

	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}

	public List<PaymentScheduleDetail> getPaymentScheduleDetails() {
		return paymentScheduleDetails;
	}

	public void setPaymentScheduleDetails(List<PaymentScheduleDetail> paymentScheduleDetails) {
		this.paymentScheduleDetails = paymentScheduleDetails;
	}
}
