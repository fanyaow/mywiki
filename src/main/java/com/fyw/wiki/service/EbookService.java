package com.fyw.wiki.service;


import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fyw.wiki.domain.Ebook;
import com.fyw.wiki.domain.EbookExample;
import com.fyw.wiki.mapper.EbookMapper;
import com.fyw.wiki.req.EbookReq;
import com.fyw.wiki.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {

    @Resource  //jdk的
//    @Autowired  //spring的
    public EbookMapper ebookMapper;


    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%"+ req.getName() + "%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = new EbookResp();
//            ebookResp.setId(ebook.getId());
            BeanUtils.copyProperties(ebook,ebookResp);
//            ebookResp.setId(123L);
            respList.add(ebookResp);
        }
        return respList;
    }
}
