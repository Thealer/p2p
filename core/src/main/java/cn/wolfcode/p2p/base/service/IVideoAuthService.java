package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.VideoAuth;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.query.VideoAuthQueryObject;

public interface IVideoAuthService {
    VideoAuth selectById(Long videoAuthId);

    //保存一个视频预约申请
    void saveVideoAuditOrder(VideoAuth videoAuth);

    PageResult query(VideoAuthQueryObject qo);

    void audit(Long id, int state, String remark);
}
