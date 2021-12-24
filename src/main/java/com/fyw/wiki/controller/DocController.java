package com.fyw.wiki.controller;

import com.fyw.wiki.req.DocQueryReq;
import com.fyw.wiki.req.DocSaveReq;
import com.fyw.wiki.resp.DocQueryResp;
import com.fyw.wiki.resp.CommonResp;
import com.fyw.wiki.resp.PageResp;
import com.fyw.wiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    public DocService docService;

    @GetMapping("/all")
    public CommonResp all(){
        CommonResp<List<DocQueryResp>> resp =new CommonResp<>();
        List<DocQueryResp> list = docService.all();
        resp.setContent(list);
        return resp;
    }

//    @GetMapping("/doc/list")
    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq req){
        CommonResp<PageResp<DocQueryResp>> resp =new CommonResp<>();
        PageResp<DocQueryResp> list = docService.list(req);
        resp.setContent(list);
        return resp;
    }

    //修改保存
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req) {
        CommonResp resp = new CommonResp<>();
        docService.save(req);
        return resp;
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable long id) {
        CommonResp resp = new CommonResp<>();
        docService.delete(id);
        return resp;
    }
}
