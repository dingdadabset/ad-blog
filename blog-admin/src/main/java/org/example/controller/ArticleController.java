package org.example.controller;

import org.example.conf.ResponseResult;
import org.example.entity.AddArticleDto;
import org.example.entity.ArticleListVo;
import org.example.entity.SgArticle;
import org.example.service.SgArticleService;
import org.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private SgArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }
    @GetMapping("list")
    public ResponseResult<List<ArticleListVo>> getList(Integer pageNum,Integer pageSize,String title,String summary){
        return articleService.articleList(pageNum,pageSize,null,title,summary);
    }
    @DeleteMapping("{id}")
    public ResponseResult del(@PathVariable Long id){
        return ResponseResult.okResult(articleService.removeById(id));
    }
    @GetMapping("{id}")
    public ResponseResult selectDetail(@PathVariable Long id){
        return ResponseResult.okResult(articleService.getById(id));
    }
    @PutMapping
    public ResponseResult update(@RequestBody AddArticleDto article){
        SgArticle sgArticle = BeanCopyUtils.copyBean(article, SgArticle.class);
        return  ResponseResult.okResult(articleService.updateById(sgArticle));
    }
}