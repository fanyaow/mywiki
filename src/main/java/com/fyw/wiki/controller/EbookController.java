package com.fyw.wiki.controller;

import com.fyw.wiki.config.PropConfig;
import com.fyw.wiki.req.EbookQueryReq;
import com.fyw.wiki.req.EbookSaveReq;
import com.fyw.wiki.resp.CommonResp;
import com.fyw.wiki.resp.EbookQueryResp;
import com.fyw.wiki.resp.PageResp;
import com.fyw.wiki.service.DocService;
import com.fyw.wiki.service.EbookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    public EbookService ebookService;

    @Resource
    public PropConfig propConfig;

//    @GetMapping("/ebook/list")
    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryReq req){
        CommonResp<PageResp<EbookQueryResp>> resp =new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    //修改保存
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable long id) {
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }

    @RequestMapping("/upload/avatar")
    public CommonResp upload(@RequestParam MultipartFile avatar) throws IOException{
        LOG.info("上传文件开始:{}",avatar);
        LOG.info("文件名:{}",avatar.getOriginalFilename());
        LOG.info("文件大小:{}",avatar.getSize());

        //保存文件到本地
        String fileName = avatar.getOriginalFilename();
//        String fullPath = "D:/tools/fyw/wiki/upload/" + fileName;
        String fullPath = propConfig.getFilePath() + fileName;
        File dest =new File(fullPath);
        avatar.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        return new CommonResp();

    }
}
