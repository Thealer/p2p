package cn.wolfcode.p2p.base.util;

import java.math.BigDecimal;

public class Constans {

    /*
    * 用户名长度常量
    * */
    public static final int VALUE_USERNAME_LENGTH = 11;

    /**
     * 零常量
     */
    public static final BigDecimal ZERO = new BigDecimal("0.0000");

    /**
     * 默认授信额度
     */
    public static final BigDecimal BORROW_LIMIT = new BigDecimal("5000.0000");


    /**
     * 显示所需精度
     */
    public static final int SCALE_SHOW = 2;

    /**
     * 保存所需精度
     */
    public static final int SCAL_STORE = 4;

    /**
     * 计算所需精度
     */
    public static final int SCAL_CAL = 8;

    /**
     * 最小借款金额
     */
    public static final BigDecimal BORROW_MIN_AMOUNT = new BigDecimal("1000.00");

    /**
     * 最小利率
     */
    public static final BigDecimal BORROW_MIN_RATE = new BigDecimal("5.00");

    /**
     * 最大利率
     */
    public static final BigDecimal  BORROW_MAX_RATE = new BigDecimal("20.00");
    /**
     * 最小投标限额
     */
    public static final BigDecimal BORROW_MIN_BIT_AMOUNT = new BigDecimal("50.00");

    // 按月分期还款(等额本息)
    public final static int RETURN_TYPE_MONTH_INTEREST_PRINCIPAL = 0;

    // 按月到期还款(每月还利息,到期还本息)
    public final static int RETURN_TYPE_MONTH_INTEREST = 1;
    // ---------------------标的类型--------------------------

    // 普通信用标
    public final static int BIDREQUEST_TYPE_NORMAL = 0;

    // ---------------------借款状态---------------------------
    public final static int BIDREQUEST_STATE_PUBLISH_PENDING = 0;   // 待发布
    public final static int BIDREQUEST_STATE_BIDDING = 1;           // 招标中
    public final static int BIDREQUEST_STATE_UNDO = 2;              // 已撤销
    public final static int BIDREQUEST_STATE_BIDDING_OVERDUE = 3;   // 流标
    public final static int BIDREQUEST_STATE_APPROVE_PENDING_1 = 4; // 满标1审
    public final static int BIDREQUEST_STATE_APPROVE_PENDING_2 = 5; // 满标2审
    public final static int BIDREQUEST_STATE_REJECTED = 6;          // 满标审核被拒绝
    public final static int BIDREQUEST_STATE_PAYING_BACK = 7;       // 还款中
    public final static int BIDREQUEST_STATE_COMPLETE_PAY_BACK = 8; // 已还清
    public final static int BIDREQUEST_STATE_PAY_BACK_OVERDUE = 9;  // 逾期
    public final static int BIDREQUEST_STATE_PUBLISH_REFUSE = 10;   // 发标审核拒绝状态
    public final static int BIDREQUEST_STATE_APPLY = 11;   // 申请中


    /** =============================账户流水类型================================ */

    // 资金流水类别：线下充值
    // 可用余额增加
    public final static int ACCOUNT_ACTIONTYPE_RECHARGE_OFFLINE = 0;

    // 资金流水类别：提现成功
    // 冻结金额减少
    public final static int ACCOUNT_ACTIONTYPE_WITHDRAW = 1;

    // 资金流水类别：成功借款
    // 可用余额增加
    public final static int ACCOUNT_ACTIONTYPE_BIDREQUEST_SUCCESSFUL = 2;

    // 资金流水类别：成功投标
    // 冻结金额减少
    public final static int ACCOUNT_ACTIONTYPE_BID_SUCCESSFUL = 3;

    // 资金流水类别：还款
    // 可用余额减少
    public final static int ACCOUNT_ACTIONTYPE_RETURN_MONEY = 4;

    // 资金流水类别：回款
    // 可用余额增加
    public final static int ACCOUNT_ACTIONTYPE_CALLBACK_MONEY = 5;

    // 资金流水类别：支付平台管理费
    // 可用余额减少
    public final static int ACCOUNT_ACTIONTYPE_CHARGE = 6;

    // 资金流水类别：利息管理费
    // 可用余额减少
    public final static int ACCOUNT_ACTIONTYPE_INTEREST_SHARE = 7;

    // 资金流水类别：提现手续费
    // 冻结金额减少
    public final static int ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE = 8;

    // 资金流水类别：充值手续费
    // 可用余额减少
    public final static int ACCOUNT_ACTIONTYPE_RECHARGE_CHARGE = 9;

    // 资金流水类别：投标冻结金额
    // 冻结金额增加 可用余额减少
    public final static int ACCOUNT_ACTIONTYPE_BID_FREEZED = 10;

    // 资金流水类别：取消投标冻结金额
    // 标审核失败
    // 冻结金额减少
    // 可用余额增加
    public final static int ACCOUNT_ACTIONTYPE_BID_UNFREEZED = 11;

    // 资金流水类别：提现申请冻结金额
    // 冻结金额增加
    // 可用余额减少
    public final static int ACCOUNT_ACTIONTYPE_WITHDRAW_FREEZED = 12;

    // 资金流水类别:提现申请失败取消冻结金额
    // 冻结金额减少
    // 可用余额增加
    public final static int ACCOUNT_ACTIONTYPE_WITHDRAW_UNFREEZED = 13;

    //债权转让成功
    public final static int ACCOUNT_ACTIONTYPE_TRANSFER_SUCCESS = 14;
    //债权认购成功
    public final static int ACCOUNT_ACTIONTYPE_SUBSCRIBE_SUCCESS = 15;


    /** ============系统账户流水类型============= */

    // 系统账户收到账户管理费（借款管理费）
    public final static int SYSTEM_ACCOUNT_ACTIONTYPE_MANAGE_CHARGE = 1;

    // 系统账户收到利息管理费
    public final static int SYSTEM_ACCOUNT_ACTIONTYPE_INTREST_MANAGE_CHARGE = 2;

    // 系统账户收到提现手续费
    public final static int SYSTEM_ACCOUNT_ACTIONTYPE_WITHDRAW_MANAGE_CHARGE = 3;

    /** =========还款状态=============== */

    // 正常待还
    public final static int PAYMENT_STATE_NORMAL = 0;

    // 已还
    public final static int PAYMENT_STATE_DONE = 1;

    // 逾期
    public final static int PAYMENT_STATE_OVERDUE = 2;

}
