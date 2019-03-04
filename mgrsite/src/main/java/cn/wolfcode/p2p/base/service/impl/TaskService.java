package cn.wolfcode.p2p.base.service.impl;

import cn.wolfcode.p2p.base.rabbitMq.Consummer;
import cn.wolfcode.p2p.base.rabbitMq.Provider;
import cn.wolfcode.p2p.base.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskService {
    @Autowired
    private IBidRequestService bidRequestService;

    @Autowired
    private Provider provider;
    @Autowired
    private Consummer consummer;

    //定时查询数据库,查询待发标的借款,判断发标时间,执行发标
    @Scheduled(cron = "*/10 * * * * ?")
    public void bidRequestPublishCheck(){
        bidRequestService.bidRequestPublishCheck();
    }
    //定时查询数据库,查询每一期还款计划的时间,超过还款计划的时间的增加滞纳金

    /*@Scheduled(cron = "*//*10 * * * * ?")
    public void bidRequestCheck(){
        bidRequestService.bidRequestCheck();
    }*/

//    @Scheduled(cron = "*/10 * * * * ?")
//    public void directQueueProvider(){
//        provider.directQueueProvider();
//    }
}
