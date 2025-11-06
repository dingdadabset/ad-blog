package org.example.controller;

import org.example.conf.ResponseResult;
import org.example.service.AiKnowledgeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * AI知识库表(AiKnowledge)表控制层 - 前台接口
 *
 * @author system
 * @since 2025-11-05
 */
@RestController
@RequestMapping("knowledge")
public class AiKnowledgeController {
    
    @Resource
    private AiKnowledgeService aiKnowledgeService;
    
    /**
     * 获取知识库列表（分页）
     */
    @GetMapping("/list")
    public ResponseResult getKnowledgeList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) String keyword) {
        return aiKnowledgeService.getKnowledgeList(pageNum, pageSize, category, difficulty, keyword);
    }
    
    /**
     * 获取知识库详情
     */
    @GetMapping("/{id}")
    public ResponseResult getKnowledgeDetail(@PathVariable("id") Long id) {
        return aiKnowledgeService.getKnowledgeDetail(id);
    }
    
    /**
     * 增加浏览次数
     */
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id) {
        return aiKnowledgeService.updateViewCount(id);
    }
    
    /**
     * 点赞知识库
     */
    @PostMapping("/like/{id}")
    public ResponseResult likeKnowledge(@PathVariable("id") Long id) {
        return aiKnowledgeService.likeKnowledge(id);
    }
    
    /**
     * 获取所有分类
     */
    @GetMapping("/categories")
    public ResponseResult getAllCategories() {
        return aiKnowledgeService.getAllCategories();
    }
}
