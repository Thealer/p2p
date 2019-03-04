package cn.wolfcode.p2p.business.service.impl;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.util.AsserUtil;
import cn.wolfcode.p2p.base.util.UserContext;
import cn.wolfcode.p2p.business.domain.RechargeOffline;
import cn.wolfcode.p2p.business.mapper.RechargeOfflineMapper;
import cn.wolfcode.p2p.business.query.RechargeQueryObject;
import cn.wolfcode.p2p.business.service.IAccountFlowService;
import cn.wolfcode.p2p.business.service.IRechargeOfflineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {
    @Autowired
    private RechargeOfflineMapper rechargeOfflineMapper;

    @Autowired
    private IAccountFlowService accountFlowService;

    @Autowired
    private IAccountService accountService;

    public void rechargeSave(RechargeOffline rechargeOffline) {
        RechargeOffline re = new RechargeOffline();
        re.setAmount(rechargeOffline.getAmount());
        re.setApplier(UserContext.getLoginInfo());
        re.setApplyTime(new Date());
        re.setBankInfo(rechargeOffline.getBankInfo());
        re.setNote(rechargeOffline.getNote());
        re.setTradeCode(rechargeOffline.getTradeCode());
        re.setTradeTime(rechargeOffline.getTradeTime());

        rechargeOfflineMapper.insert(re);
    }

    public PageResult query(RechargeQueryObject qo) {
        int count = rechargeOfflineMapper.selectForCount(qo);
        if (count == 0) {
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(rechargeOfflineMapper.selectForList(qo), count, qo.getCurrentPage(), qo.getPageSize());
    }

    public void audit(Long id, int state, String remark) {
        //判断充值对象是否处于待审核状态
        RechargeOffline re = rechargeOfflineMapper.selectByPrimaryKey(id);
        AsserUtil.isTrue(re.getState() != RechargeOffline.STATE_NORMAL,
                "该充值不处于待审核状态");
        //设置审核相关信息
        re.setRemark(remark);
        re.setAuditor(UserContext.getLoginInfo());
        re.setAuditTime(new Date());
        re.setState(state);
        update(re);
        //充值成功,用户余额增加,创建流水充值
        if(state == RechargeOffline.STATE_SUCCESS){
            Account account = accountService.selectById(re.getApplier().getId());
            account.setUsable_amount(account.getUsable_amount().add(re.getAmount()));
            accountService.update(account);

            accountFlowService.createRechargeFlow(account,re.getAmount());
        }
        //充值失败,无操作
    }

    private void update(RechargeOffline re) {
        rechargeOfflineMapper.updateByPrimaryKey(re);
    }
}
