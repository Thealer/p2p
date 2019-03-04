package com.example.service;

import com.example.service.impl.MyFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//配置feign                                                                        //开启熔断器(类似于事务):feign默认实现熔断器功能, 不用额外引包
@FeignClient(value = "PRODUCER",configuration = FooConfiguration.class,fallback = MyFeignClientImpl.class)
public interface MyFeignClient {

    //当此方法别调用会自动请求到 PRODUCER服务的 /eurekaProvide/provide 资源
    @RequestMapping(value = "/eurekaProvide/provide")
    public String provide(@RequestParam("name") String name);
}
