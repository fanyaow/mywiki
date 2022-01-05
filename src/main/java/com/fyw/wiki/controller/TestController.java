package com.fyw.wiki.controller;
import com.fyw.wiki.config.PropConfig;
import com.fyw.wiki.domain.Test;
import com.fyw.wiki.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {

    @Resource
    public TestService testService;

    @Resource
    public PropConfig propConfig;

    @RequestMapping("/hello")
    public String hell(){
        return "Hello World!";
    }

    @GetMapping("/test/list")
    public List<Test> list() {
        return testService.list() ;
    }

    @GetMapping("/test/upload")
    public String test(){
       return propConfig.getFilePath();
    }
}
