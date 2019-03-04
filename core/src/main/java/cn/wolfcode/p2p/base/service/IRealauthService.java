package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.query.PageResult;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;

public interface IRealauthService {
    void realAuthSave(RealAuth realAuth);

    RealAuth selectById(Long realAuthId);

    PageResult query(RealAuthQueryObject qo);

    void audit(Long id,int state, String remark);
}
