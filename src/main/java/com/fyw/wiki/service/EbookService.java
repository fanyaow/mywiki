package com.fyw.wiki.service;


import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fyw.wiki.domain.Ebook;
import com.fyw.wiki.domain.EbookExample;
import com.fyw.wiki.mapper.EbookMapper;
import com.fyw.wiki.req.EbookReq;
import com.fyw.wiki.resp.EbookResp;
import com.fyw.wiki.util.CopyUtil;
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

//
//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
//
//            //未使用工具了CopyUtil时写法
////            EbookResp ebookResp = new EbookResp();
////            BeanUtils.copyProperties(ebook,ebookResp);
//            //对象复制 使用工具类CopyUtil
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//        }
//        return respList;
        //列表复制
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);
        return list;
    }
}
