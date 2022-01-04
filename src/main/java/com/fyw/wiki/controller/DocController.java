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
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    public DocService docService;

    @GetMapping("/all/{ebookId}")
    public CommonResp all(@PathVariable Long ebookId){
        CommonResp<List<DocQueryResp>> resp =new CommonResp<>();
        List<DocQueryResp> list = docService.all(ebookId);
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
    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr) {
        CommonResp resp = new CommonResp<>();
        List<String> list = Arrays.asList(idsStr.split(","));
        docService.delete(list);
        return resp;
    }
    //文档查找内容表
    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id){
        CommonResp<String> resp =new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }

    //点赞
    @GetMapping("/vote/{id}")
    public CommonResp vote(@PathVariable Long id){
        CommonResp resp =new CommonResp();
        docService.vote(id);
        return resp;
    }

//    @PostMapping("/upload")
//    public CommonResp fileUpload(@RequestParam MultipartFile file) {
//        // 将file放到本地，或是图片服务器
//    }
}
