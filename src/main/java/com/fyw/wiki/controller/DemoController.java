package com.fyw.wiki.controller;
import com.fyw.wiki.domain.Demo;
import com.fyw.wiki.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    public DemoService demoService;

//    @GetMapping("/demo/list")
    @GetMapping("/list")
    public List<Demo> list() {
        return demoService.list() ;
    }
}
