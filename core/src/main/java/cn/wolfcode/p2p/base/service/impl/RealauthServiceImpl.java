package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.mapper.RealauthMapper;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;
import cn.wolfcode.p2p.base.service.IRealauthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.util.AsserUtil;
import cn.wolfcode.p2p.base.util.BitStatesUtils;
import cn.wolfcode.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RealauthServiceImpl implements IRealauthService {
    @Autowired
    private RealauthMapper realauthMapper;

    @Autowired
    private IUserInfoService userInfoService;

    //保存一个实名申请
    public void realAuthSave(RealAuth realAuth) {
        //判断基本参数
        //判断是否已经通过实名认证
        LoginInfo loginInfo = UserContext.getLoginInfo();
        UserInfo userInfo = userInfoService.selectById(loginInfo.getId());
        //判断是否有实名申请
        AsserUtil.isTrue(userInfo.isRealAuth(),"该账号已完成实名认证");
        //保存一个实名申请
        RealAuth ra = new RealAuth();
        ra.setAddress(realAuth.getAddress());
        ra.setApplier(loginInfo);
        ra.setApplyTime(new Date());
        ra.setBornDate(realAuth.getBornDate());
        ra.setIdNumber(realAuth.getIdNumber());
        ra.setImage1(realAuth.getImage1());
        ra.setImage2(realAuth.getImage2());
        ra.setRealName(realAuth.getRealName());
        ra.setSex(realAuth.getSex());
        realauthMapper.insert(ra);
        //把当前申请对象的id设置给userInfo的realAuthId
        userInfo.setRealAuthId(ra.getId());
        userInfoService.update(userInfo);

    }

    public RealAuth selectById(Long realAuthId) {
        return realauthMapper.selectByPrimaryKey(realAuthId);
    }

    public PageResult query(RealAuthQueryObject qo) {

        int count = realauthMapper.selectForCount(qo);
        if(count == 0){
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(realauthMapper.selectForList(qo),count,qo.getCurrentPage(),qo.getPageSize());
    }


    public void audit(Long id, int state, String remark) {
        //判断基本参数
        //实名认证对象状态要处于待审核
        //查询实名认证对象
        RealAuth realAuth = realauthMapper.selectByPrimaryKey(id);
        AsserUtil.isTrue(realAuth.getState() != RealAuth.STATE_NORMAL,"该用户不处于待审核状态");
        //设置实名认证状态
        realAuth.setState(state);
        //设置审核人
        realAuth.setAuditor(UserContext.getLoginInfo());
        //设置审核备注
        realAuth.setRemark(remark);
        //设置审核时间
        realAuth.setAuditTime(new Date());
        update(realAuth);
        //如果审核成功,修改申请人的userInfo.bit_state,增加实名通过状态,设置userInfo的idNumber和realNanme
        UserInfo userInfo = userInfoService.selectById(realAuth.getApplier().getId());
        if(state == RealAuth.STATE_SUCCESS){
            userInfo.setBit_state(BitStatesUtils.addState(userInfo.getBit_state(),BitStatesUtils.OP_REAL_AUTH));
            userInfo.setId_number(realAuth.getIdNumber());
            userInfo.setReal_name(realAuth.getRealName());
        }else{
            //审核失败,设置userInfo的realAuthId为null
            userInfo.setRealAuthId(null);
        }
        userInfoService.update(userInfo);
    }

    private void update(RealAuth realAuth) {
        realauthMapper.updateByPrimaryKey(realAuth);
    }

}
