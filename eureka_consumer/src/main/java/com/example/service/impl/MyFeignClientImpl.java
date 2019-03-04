package com.example.service.impl;

import com.example.service.MyFeignClient;
import org.springframework.stereotype.Component;

/**
 * 实现熔断器
 */
@Component
public class MyFeignClientImpl implements MyFeignClient {

    @Override
    public String provide(String name) {
        return "你好," + name + ",访问出错";
    }
}
