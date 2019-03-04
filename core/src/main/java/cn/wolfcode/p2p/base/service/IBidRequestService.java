package cn.wolfcode.p2p.base.service;

import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.query.BidRequestQueryObject;
import cn.wolfcode.p2p.base.query.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IBidRequestService {

    //保存借款对象
    void apply(BidRequest bidRequest);

    //发标前审核页面数据
    PageResult query(BidRequestQueryObject qo);

    //发表前审核
    void auditPublish(Long id, int state, String remark, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date publishTime);

    //前台首页
    List<BidRequest> indexBidRequestList();

    BidRequest seletById(Long id);

    //前台投标
    void bid(Long bidRequestId, BigDecimal amount);

    //满标一审
    void audit1(Long id, int state, String remark);

    //满标二审
    void audit2(Long id, int state, String remark);

    //还款
    void returnMoney(Long id);

    //首页发标公告列表
    List<BidRequest> indexPublistList();

    //定时发标
    void bidRequestPublishCheck();

    //检查还款日期
    //void bidRequestCheck();

}
