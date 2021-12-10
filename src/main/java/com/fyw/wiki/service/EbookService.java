package com.fyw.wiki.service;


import com.fyw.wiki.domain.Demo;
import com.fyw.wiki.domain.Ebook;
import com.fyw.wiki.mapper.DemoMapper;
import com.fyw.wiki.mapper.EbookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource  //jdk的
//    @Autowired  //spring的
    public EbookMapper ebookMapper;

    public List<Ebook> list() {
        return ebookMapper.selectByExample(null);
    }
}
