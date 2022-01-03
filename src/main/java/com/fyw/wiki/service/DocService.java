package com.fyw.wiki.service;


import com.fyw.wiki.domain.Content;
import com.fyw.wiki.domain.Doc;
import com.fyw.wiki.domain.DocExample;
import com.fyw.wiki.exception.BusinessException;
import com.fyw.wiki.exception.BusinessExceptionCode;
import com.fyw.wiki.mapper.ContentMapper;
import com.fyw.wiki.mapper.DocMapper;
import com.fyw.wiki.mapper.DocMapperCust;
import com.fyw.wiki.req.DocQueryReq;
import com.fyw.wiki.req.DocSaveReq;
import com.fyw.wiki.resp.DocQueryResp;
import com.fyw.wiki.resp.PageResp;
import com.fyw.wiki.util.CopyUtil;
import com.fyw.wiki.util.RedisUtil;
import com.fyw.wiki.util.RequestContext;
import com.fyw.wiki.util.SnowFlake;
import com.fyw.wiki.websocket.WebSocketServer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource  //jdk的
//    @Autowired  //spring的
    private DocMapper docMapper;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    public RedisUtil redisUtil;
    @Resource
    private DocMapperCust docMapperCust;

    @Resource
    public WebSocketServer webSocketServer;

    @Resource  //jdk的
//    @Autowired  //spring的
    private SnowFlake snowFlake;

    @Resource
    private WsService wsService;

    public List<DocQueryResp> all(Long ebookId) {
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
        docExample.setOrderByClause("sort asc");
        //返回每页数据记录
        List<Doc> docList = docMapper.selectByExample(docExample);

        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        return list;
    }



    public PageResp<DocQueryResp> list(DocQueryReq req) {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        //返回每页数据记录
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo =new PageInfo<>(docList);
            //获取总条数
        LOG.info("总行数:{}", pageInfo.getTotal());
            //获取总页数
        LOG.info("总页数:{}", pageInfo.getPages());

//
//        List<DocResp> respList = new ArrayList<>();
//        for (Doc doc : docList) {
//
//            //未使用工具了CopyUtil时写法
////            DocResp docResp = new DocResp();
////            BeanUtils.copyProperties(doc,docResp);
//            //对象复制 使用工具类CopyUtil
//            DocResp docResp = CopyUtil.copy(doc, DocResp.class);
//            respList.add(docResp);
//        }
//        return respList;
        //列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);
        PageResp<DocQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    //保存
    public void save(DocSaveReq req){
        Doc doc = CopyUtil.copy(req,Doc.class);
        Content content = CopyUtil.copy(req, Content.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增
            doc.setId(snowFlake.nextId());
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);

            content.setId(doc.getId());
            contentMapper.insert(content);
        }else {
            //保存
            docMapper.updateByPrimaryKey(doc);
            int count= contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count == 0){
                contentMapper.insert(content);
            }
        }
    }

    //删除
    public void delete(Long id){
        docMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<String> ids){

        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }

    //查询内容
    public String findContent(Long id){
        Content content = contentMapper.selectByPrimaryKey(id);
        //文档阅读数+1
        docMapperCust.updateDocViewCount(id);

        if (ObjectUtils.isEmpty(content)) {
            return "";
        }else {
            return content.getContent();
        }
    }

    //点赞
    public void  vote(Long id){
        // docMapperCust.updateDocVoteCount(id);
        // 远程IP+doc.id作为key，24小时内不能重复
        String ip = RequestContext.getRemoteAddr();

        if (redisUtil.validateRepeat("DOC_VOTE_" + id + "_" + ip, 3600*24)) {
            docMapperCust.updateDocVoteCount(id);
        } else {
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }

        //推送消息
        Doc docDb = docMapper.selectByPrimaryKey(id);
        wsService.sendInfo("["+docDb.getName()+"]被点赞!");
    }

    //定时更新ebook里边的相关数据
    public void updateEbookInfo(){
        docMapperCust.updateEbookInfo();
    }
}
