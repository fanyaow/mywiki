package com.fyw.wiki.service;


import com.fyw.wiki.domain.Test;
import com.fyw.wiki.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource  //jdk的
//    @Autowired  //spring的
    public TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }
}
