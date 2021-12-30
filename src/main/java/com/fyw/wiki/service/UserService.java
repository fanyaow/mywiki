package com.fyw.wiki.service;


import com.fyw.wiki.domain.User;
import com.fyw.wiki.domain.UserExample;
import com.fyw.wiki.exception.BusinessException;
import com.fyw.wiki.exception.BusinessExceptionCode;
import com.fyw.wiki.mapper.UserMapper;
import com.fyw.wiki.req.UserQueryReq;
import com.fyw.wiki.req.UserSaveReq;
import com.fyw.wiki.resp.UserQueryResp;
import com.fyw.wiki.resp.PageResp;
import com.fyw.wiki.util.CopyUtil;
import com.fyw.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource  //jdk的
//    @Autowired  //spring的
    private UserMapper userMapper;

    @Resource  //jdk的
//    @Autowired  //spring的
    private SnowFlake snowFlake;




    public PageResp<UserQueryResp> list(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        //增加判断 如果name为空,则返回所有
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
//            criteria.andNameLike("%" + req.getLoginName() + "%");
            criteria.andLoginNameEqualTo(req.getLoginName());
        }

        //返回每页数据记录
        PageHelper.startPage(req.getPage(),req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo =new PageInfo<>(userList);
            //获取总条数
        LOG.info("总行数:{}", pageInfo.getTotal());
            //获取总页数
        LOG.info("总页数:{}", pageInfo.getPages());

//
//        List<UserResp> respList = new ArrayList<>();
//        for (User user : userList) {
//
//            //未使用工具了CopyUtil时写法
////            UserResp userResp = new UserResp();
////            BeanUtils.copyProperties(user,userResp);
//            //对象复制 使用工具类CopyUtil
//            UserResp userResp = CopyUtil.copy(user, UserResp.class);
//            respList.add(userResp);
//        }
//        return respList;
        //列表复制
        List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);
        PageResp<UserQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    //保存
    public void save(UserSaveReq req){
        User user = CopyUtil.copy(req,User.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增前判断loginName 是否存在
            User userDB =selectByLoginName(req.getLoginName());
            if (ObjectUtils.isEmpty(userDB)){
                //新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            }else {
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        }else {
            //保存
//            userMapper.updateByPrimaryKey(user);
            //保存时,如果loginName 为空,都不在更新LoginName
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    //删除
    public void delete(Long id){
        userMapper.deleteByPrimaryKey(id);
    }

    //判断登陆名是否存在
    public User selectByLoginName (String LoginName){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(LoginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)){
            return null;
        }else {
            return userList.get(0);
        }
    }
}
