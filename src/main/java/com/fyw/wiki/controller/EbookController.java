package com.fyw.wiki.controller;

import com.fyw.wiki.req.EbookQueryReq;
import com.fyw.wiki.req.EbookSaveReq;
import com.fyw.wiki.resp.CommonResp;
import com.fyw.wiki.resp.EbookQueryResp;
import com.fyw.wiki.resp.PageResp;
import com.fyw.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    public EbookService ebookService;

//    @GetMapping("/ebook/list")
    @GetMapping("/list")
    public CommonResp list(EbookQueryReq req){
        CommonResp<PageResp<EbookQueryResp>> resp =new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    //修改保存
    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }
}
