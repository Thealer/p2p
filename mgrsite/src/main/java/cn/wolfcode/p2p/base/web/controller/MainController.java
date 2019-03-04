package cn.wolfcode.p2p.base.web.controller;

import cn.wolfcode.p2p.base.rabbitMq.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Autowired
    private Provider provider;

    @RequestMapping("/main")
    public String main(){
        return "main";
    }

//    @RequestMapping("/sendDirect")
//    public String sendDirect(){
//        provider.directQueueProvider();
//        return "发送成功";
//    }
}
