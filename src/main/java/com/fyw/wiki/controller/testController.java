package com.fyw.wiki.controller;
import com.fyw.wiki.domain.Test;
import com.fyw.wiki.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class testController {

    @Resource
    public TestService testService;


    @RequestMapping("/hello")
    public String hell(){
        return "Hello World!";
    }

    @GetMapping("/test/list")
    public List<Test> list() {
        return testService.list() ;
    }
}
