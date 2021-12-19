package com.fyw.wiki.controller;

import com.fyw.wiki.req.EbookReq;
import com.fyw.wiki.resp.CommonResp;
import com.fyw.wiki.resp.EbookResp;
import com.fyw.wiki.resp.PageResp;
import com.fyw.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    public EbookService ebookService;

//    @GetMapping("/ebook/list")
    @GetMapping("/list")
    public CommonResp list(EbookReq req){
        CommonResp<PageResp<EbookResp>> resp =new CommonResp<>();
        PageResp<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

}
