package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.domain.BidRequest;
import cn.wolfcode.p2p.base.service.IBidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IBidRequestService bidRequestService;

    //首页
    @RequestMapping("/index")
    public String index(Model model) {
        //投资列表
        List<BidRequest> bidRequests = bidRequestService.indexBidRequestList();
        model.addAttribute("bidRequests",bidRequests);

        //发标公告列表
        List<BidRequest> publishPendngBidRequests = bidRequestService.indexPublistList();
        model.addAttribute("publishPendngBidRequests",publishPendngBidRequests);

        return "main";
    }


}
