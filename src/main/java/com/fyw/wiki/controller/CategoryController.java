package com.fyw.wiki.controller;

import com.fyw.wiki.req.CategoryQueryReq;
import com.fyw.wiki.req.CategorySaveReq;
import com.fyw.wiki.resp.CommonResp;
import com.fyw.wiki.resp.CategoryQueryResp;
import com.fyw.wiki.resp.PageResp;
import com.fyw.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    public CategoryService categoryService;

//    @GetMapping("/category/list")
    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req){
        CommonResp<PageResp<CategoryQueryResp>> resp =new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    //修改保存
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable long id) {
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}
