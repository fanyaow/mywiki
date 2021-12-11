package com.fyw.wiki.controller;

import com.fyw.wiki.domain.Ebook;
import com.fyw.wiki.resp.CommonResp;
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

//    @GetMapping("/ebook/list")
    @GetMapping("/list")
    public CommonResp list(String name){
        CommonResp<List<Ebook>> resp =new CommonResp<>();
        List<Ebook> list = ebookService.list(name);
        resp.setContent(list);
        return resp;
    }

}
