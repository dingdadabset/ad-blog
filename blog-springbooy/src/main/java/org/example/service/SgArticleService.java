package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.conf.ResponseResult;
import org.example.entity.AddArticleDto;
import org.example.entity.SgArticle;

/**
 * 文章表(SgArticle)表服务接口
 *
 * @author makejava
 * @since 2023-05-22 17:47:48
 */
public interface SgArticleService extends IService<SgArticle> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult add(AddArticleDto article);
}

