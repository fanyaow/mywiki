package com.fyw.wiki.service;


import com.fyw.wiki.domain.Demo;
import com.fyw.wiki.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService {

    @Resource  //jdk的
//    @Autowired  //spring的
    public DemoMapper demoMapper;

    public List<Demo> list() {
        return demoMapper.selectByExample(null);
    }
}
