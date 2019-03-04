package com.example.erueka.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eurekaProvide")
public class ProviderController {


    @RequestMapping(value = "/provide",method = RequestMethod.GET)
    public String provide(@RequestParam(value = "name") String name){

        return name + "wellcome to eureka";
    }
}
