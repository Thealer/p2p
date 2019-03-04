package cn.wolfcode.p2p.business.domain;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.util.Constans;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 针对一个还款计划,投资人的回款明细
 * 
 * @author Administrator
 * 
 */
@Alias("PaymentScheduleDetail")
public class PaymentScheduleDetail  {

	//转让状态:没转让
	public static final int TRANSFER_STATE_NORMAL = 0;
	//转让状态:已转让
	public static final int TRANSFER_STATE_YET = 1;

	private Long id;
	// 该投标人总共投标金额,便于还款/垫付查询
	private BigDecimal bidAmount; 
	// 对应的投标ID
	private Long bidId;
	// 本期还款总金额(=本金+利息)
	private BigDecimal totalAmount = Constans.ZERO;
	// 本期应还款本金
	private BigDecimal principal = Constans.ZERO;
	// 本期应还款利息
	private BigDecimal interest = Constans.ZERO;
	// 第几期（即第几个月）
	private int monthIndex;
	// 本期还款截止时间
	private Date deadLine;
	// 所属哪个借款
	private Long bidRequestId;
	// 实际付款日期
	private Date payDate;
	// 还款方式
	private int returnType;
	// 所属还款计划
	private Long paymentScheduleId;
	// 还款人(即发标人)
	private LoginInfo fromLoginInfo;
	// 收款人(即投标人)
	private Long toLoginInfoId;
	//是否已转让债权
	private boolean transfer = false;

	public boolean isTransfer() {
		return transfer;
	}

	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}

	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
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

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public Long getBidRequestId() {
		return bidRequestId;
	}

	public void setBidRequestId(Long bidRequestId) {
		this.bidRequestId = bidRequestId;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public int getReturnType() {
		return returnType;
	}

	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}

	public Long getPaymentScheduleId() {
		return paymentScheduleId;
	}

	public void setPaymentScheduleId(Long paymentScheduleId) {
		this.paymentScheduleId = paymentScheduleId;
	}

	public LoginInfo getFromLoginInfo() {
		return fromLoginInfo;
	}

	public void setFromLoginInfo(LoginInfo fromLoginInfo) {
		this.fromLoginInfo = fromLoginInfo;
	}

	public Long getToLoginInfoId() {
		return toLoginInfoId;
	}

	public void setToLoginInfoId(Long toLoginInfoId) {
		this.toLoginInfoId = toLoginInfoId;
	}
}
