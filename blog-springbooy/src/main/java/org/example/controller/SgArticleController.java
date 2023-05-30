package org.example.controller;




import org.example.conf.ResponseResult;
import org.example.entity.SgArticle;
import org.example.service.SgArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 文章表(SgArticle)表控制层
 *
 * @author makejava
 * @since 2023-05-22 17:47:48
 */
@RestController
@RequestMapping("article")
public class SgArticleController<R>  {
    @Autowired
    private SgArticleService sgArticleService;
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(Long id){
        return sgArticleService.getArticleDetail(id);
    }
    @GetMapping("/articleList")
    public ResponseResult articleList( Integer pageNum, Integer pageSize, Long categoryId){
        return sgArticleService.articleList(pageNum,pageSize,categoryId);
    }
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        return sgArticleService.hotArticleList();
    }
}

