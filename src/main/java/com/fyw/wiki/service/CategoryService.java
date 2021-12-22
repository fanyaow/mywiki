package com.fyw.wiki.service;


import com.fyw.wiki.domain.Category;
import com.fyw.wiki.domain.CategoryExample;
import com.fyw.wiki.mapper.CategoryMapper;
import com.fyw.wiki.req.CategoryQueryReq;
import com.fyw.wiki.req.CategorySaveReq;
import com.fyw.wiki.resp.CategoryQueryResp;
import com.fyw.wiki.resp.PageResp;
import com.fyw.wiki.util.CopyUtil;
import com.fyw.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource  //jdk的
//    @Autowired  //spring的
    private CategoryMapper categoryMapper;

    @Resource  //jdk的
//    @Autowired  //spring的
    private SnowFlake snowFlake;




    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        //返回每页数据记录
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo =new PageInfo<>(categoryList);
            //获取总条数
        LOG.info("总行数:{}", pageInfo.getTotal());
            //获取总页数
        LOG.info("总页数:{}", pageInfo.getPages());

//
//        List<CategoryResp> respList = new ArrayList<>();
//        for (Category category : categoryList) {
//
//            //未使用工具了CopyUtil时写法
////            CategoryResp categoryResp = new CategoryResp();
////            BeanUtils.copyProperties(category,categoryResp);
//            //对象复制 使用工具类CopyUtil
//            CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
//            respList.add(categoryResp);
//        }
//        return respList;
        //列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    //保存
    public void save(CategorySaveReq req){
        Category category = CopyUtil.copy(req,Category.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        }else {
            //保存
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    //删除
    public void delete(Long id){
        categoryMapper.deleteByPrimaryKey(id);
    }
}
