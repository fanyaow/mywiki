package com.fyw.wiki.controller;
import com.fyw.wiki.domain.Demo;
import com.fyw.wiki.domain.Ebook;
import com.fyw.wiki.service.DemoService;
import com.fyw.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    public EbookService ebookService;

//    @GetMapping("/demo/list")
    @GetMapping("/list")
    public List<Ebook> list() {
        return ebookService.list() ;
    }
}
