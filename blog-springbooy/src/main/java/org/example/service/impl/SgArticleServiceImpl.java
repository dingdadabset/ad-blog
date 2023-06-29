package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.conf.RedisCache;
import org.example.conf.ResponseResult;
import org.example.conf.SystemConstants;
import org.example.dao.SgArticleDao;
import org.example.entity.*;
import org.example.service.ArticleTagService;
import org.example.service.SgArticleService;
import org.example.service.SgCategoryService;
import org.example.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(SgArticle)表服务实现类
 *
 * @author makejava
 * @since 2023-05-22 17:47:48
 */
@Service("sgArticleService")
public class SgArticleServiceImpl extends ServiceImpl<SgArticleDao, SgArticle> implements SgArticleService {

    @Override
    public ResponseResult hotArticleList() {

        LambdaQueryWrapper<SgArticle> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(SgArticle::getStatus,0);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(SgArticle::getViewCount);

        //最多只查询10条
        Page<SgArticle> page = new Page(1,10);
        page(page,queryWrapper);
        List<HotArticleVo> articleVos = new ArrayList<>();
        List<SgArticle> articles = page.getRecords();
        for (SgArticle article : articles) {
            /*目前我们的响应格式其实是不符合接口文档的标准的，多返回了很多字段。这是因为我们查询出来的结果是Article来封装的，Article中字段比较多。

​	我们在项目中一般最后还要把VO来接受查询出来的结果。一个接口对应一个VO，这样即使接口响应字段要修改也只要改VO即可。*/
            HotArticleVo hotArticleVo = new HotArticleVo();
            BeanUtils.copyProperties(article,hotArticleVo);
            articleVos.add(hotArticleVo);
        }
        return ResponseResult.okResult(articleVos);
      
    }
    @Autowired
    private SgCategoryService categoryService;
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId, String title, String summary) {
        LambdaQueryWrapper<SgArticle> qu = new LambdaQueryWrapper<SgArticle>()
                .eq(Objects.nonNull(categoryId), SgArticle::getCategoryId, categoryId)
                .eq(Objects.nonNull(title),SgArticle::getTitle,title)
                .eq(Objects.nonNull(summary),SgArticle::getSummary,summary)
                .eq(SgArticle::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(SgArticle::getIsTop);
        Page<SgArticle> sgArticlePage = new Page<>(pageNum, pageSize);
        Page<SgArticle> page = page(sgArticlePage, qu);
        List<SgArticle> articles = page.getRecords();
        //查询categoryName
        articles.stream()
                .filter((x)->Objects.nonNull(x.getCategoryId()))
                .map(article ->{
                    article.setCategoryName(categoryService.
                            getById(article.getCategoryId()).getName());
                    return article;
                }
                )
                .collect(Collectors.toList());
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //LambdaQueryWrapper<SgArticle> eq = new LambdaQueryWrapper<SgArticle>().eq(SgArticle::getId, id);
        SgArticle byId = getById(id);
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        byId.setViewCount(viewCount.longValue());
        SgArticleVO sgArticleVO = BeanCopyUtils.copyBean(byId, SgArticleVO.class);
        String categoryId = sgArticleVO.getCategoryId();
        SgCategory byId1 = categoryService.getById(categoryId);
        if (Objects.nonNull(byId1)){
            sgArticleVO.setCategoryName(byId1.getName());
        }

        return ResponseResult.okResult(sgArticleVO);
    }

    @Override
    public ResponseResult add(AddArticleDto article) {
        SgArticle article2 = BeanCopyUtils.copyBean(article, SgArticle.class);
        save(article2);


        List<ArticleTag> articleTags = article.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        //articleTagService.saveBatch();
        return ResponseResult.okResult(articleTags);
    }

    @Override
    public ResponseResult updateViewCount(String id) {
        SgArticle byId = getById(id);
        byId.setViewCount(byId.getViewCount()+1);
        saveOrUpdate(byId);
        return ResponseResult.okResult();
    }


}

