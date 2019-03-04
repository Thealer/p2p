package com.example.controller;

import com.example.service.MyFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/eurekaConsumer")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyFeignClient myFeignClient;

    @RequestMapping(value = "/consumer",method = RequestMethod.GET)
    public String consumer(@RequestParam(value = "name") String name){
        String result = null;
        try {
            result = restTemplate.getForObject("http://PRODUCER/eurekaProvide/provide?name=" + name,String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/feign")
    public String feignTest(@RequestParam("name") String name){
        String res = null;
        try {
            res = myFeignClient.provide(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
