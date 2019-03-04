package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.domain.VideoAuth;
import cn.wolfcode.p2p.base.mapper.VideoAuthMapper;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.query.VideoAuthQueryObject;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.service.IVideoAuthService;
import cn.wolfcode.p2p.base.util.AsserUtil;
import cn.wolfcode.p2p.base.util.BitStatesUtils;
import cn.wolfcode.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VideoAuthServiceImpl implements IVideoAuthService {

    @Autowired
    private VideoAuthMapper videoAuthMapper;
    @Autowired
    private IUserInfoService userInfoService;

    public VideoAuth selectById(Long videoAuthId) {
        return videoAuthMapper.selectByPrimaryKey(videoAuthId);
    }

    public void saveVideoAuditOrder(VideoAuth videoAuth) {
        //基本参数判定
        //申请人
        LoginInfo applier = UserContext.getLoginInfo();
        //用户userInfo(信息)
        UserInfo userInfo = userInfoService.selectById(applier.getId());
        //判断是否已经通过视频认证申请
        AsserUtil.isTrue(userInfo.isVedioAuth(),"您已经通过视频认证审核");
        //判断是否有视频认证申请
        AsserUtil.isTrue(userInfo.getVideoAuthId()!=null,"您已经有一个视频认证申请");
        //保存一个视频申请对象
        VideoAuth va = new VideoAuth();
        va.setApplier(applier);
        va.setApplyTime(new Date());
        va.setAuditor(videoAuth.getAuditor());
        va.setOrderDate(videoAuth.getOrderDate());
        va.setOrderTime(videoAuth.getOrderTime());
        videoAuthMapper.insert(va);

        //设置用户的videoAuthId
        userInfo.setVideoAuthId(va.getId());
        userInfoService.update(userInfo);

    }

    public PageResult query(VideoAuthQueryObject qo) {
        int count = videoAuthMapper.selectForCount(qo);
        if(count == 0){
            return PageResult.empty(qo.getPageSize());
        }
        return new PageResult(videoAuthMapper.selectForList(qo),count,qo.getCurrentPage(),qo.getPageSize());
    }
    //视频审核
    public void audit(Long id, int state, String remark) {
        //基本判断
        //视频审核对象是否为待审核状态
        VideoAuth va = videoAuthMapper.selectByPrimaryKey(id);
        AsserUtil.isTrue(va.getState() != VideoAuth.STATE_NORMAL,"不处于待审核状态");
        //设置审核相关信息
        va.setAuditTime(new Date());
        va.setRemark(remark);
        va.setState(state);
        update(va);
        //如果审核成功修改用户的bitstate增加视频审核通过
        UserInfo userInfo = userInfoService.selectById(va.getApplier().getId());
        if(state == VideoAuth.STATE_SUCCESS){
            Long bitstate = BitStatesUtils.addState(userInfo.getBit_state(),BitStatesUtils.OP_VEDIO_AUTH);
            userInfo.setBit_state(bitstate);
        }else{
        //如果审核失败,修改用户的videoAUthId为null
            userInfo.setVideoAuthId(null);
        }
        userInfoService.update(userInfo);
    }

    private void update(VideoAuth va) {
        videoAuthMapper.updateByPrimaryKey(va);
    }
}
