package com.fyw.wiki.controller;

import com.alibaba.fastjson.JSONObject;
import com.fyw.wiki.req.UserLoginReq;
import com.fyw.wiki.req.UserQueryReq;
import com.fyw.wiki.req.UserResetPasswordReq;
import com.fyw.wiki.req.UserSaveReq;
import com.fyw.wiki.resp.CommonResp;
import com.fyw.wiki.resp.PageResp;
import com.fyw.wiki.resp.UserLoginResp;
import com.fyw.wiki.resp.UserQueryResp;
import com.fyw.wiki.service.UserService;
import com.fyw.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    public UserService userService;
    @Resource  //jdk的
    private SnowFlake snowFlake;

    @Resource
    private RedisTemplate redisTemplate;

//    @GetMapping("/user/list")
    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req){
        CommonResp<PageResp<UserQueryResp>> resp =new CommonResp<>();
        PageResp<UserQueryResp> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }

    //修改保存
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.save(req);
        return resp;
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable long id) {
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }
    //重置密码
    @PostMapping("/reset-password")
    public CommonResp save(@Valid @RequestBody UserResetPasswordReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.resetPassword(req);
        return resp;
    }

    //重置密码
    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody UserLoginReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        UserLoginResp userLoginResp = userService.login(req);

        Long token =snowFlake.nextId();
        LOG.info("生成单点登录token：{}，并放入redis中", token);
        userLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp), 3600 * 24, TimeUnit.SECONDS);


        resp.setContent(userLoginResp);
        return resp;
    }
}
