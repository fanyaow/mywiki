package com.fyw.wiki.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @RequestMapping("/hello")
    public String hell(){
        return "Hello World!";
    }
}
