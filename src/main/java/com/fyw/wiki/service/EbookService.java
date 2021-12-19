package com.fyw.wiki.service;


import com.fyw.wiki.domain.Ebook;
import com.fyw.wiki.domain.EbookExample;
import com.fyw.wiki.mapper.EbookMapper;
import com.fyw.wiki.req.EbookQueryReq;
import com.fyw.wiki.req.EbookSaveReq;
import com.fyw.wiki.resp.EbookQueryResp;
import com.fyw.wiki.resp.PageResp;
import com.fyw.wiki.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource  //jdk的
//    @Autowired  //spring的
    private EbookMapper ebookMapper;



    public PageResp<EbookQueryResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        //增加判断 如果name为空,则返回所有
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        //返回每页数据记录
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo =new PageInfo<>(ebookList);
            //获取总条数
        LOG.info("总行数:{}", pageInfo.getTotal());
            //获取总页数
        LOG.info("总页数:{}", pageInfo.getPages());

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
        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        PageResp<EbookQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    //保存
    public void save(EbookSaveReq req){
        Ebook ebook = CopyUtil.copy(req,Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增
            ebookMapper.insert(ebook);
        }else {
            //保存
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }
}
