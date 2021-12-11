package com.fyw.wiki.service;


import com.fyw.wiki.domain.Ebook;
import com.fyw.wiki.domain.EbookExample;
import com.fyw.wiki.mapper.EbookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource  //jdk的
//    @Autowired  //spring的
    public EbookMapper ebookMapper;

    public List<Ebook> list(String name) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%"+ name + "%");
        return ebookMapper.selectByExample(ebookExample);
    }
}
