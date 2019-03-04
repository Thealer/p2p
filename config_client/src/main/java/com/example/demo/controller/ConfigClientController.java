package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {

    @Value("${notify}")
    private String notify;

    @RequestMapping("/hello")
    public String hello(){
        return notify;
    }
}
