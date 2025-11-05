package org.example.controller;

import org.example.conf.ResponseResult;
import org.example.entity.AiKnowledgeDTO;
import org.example.service.AiKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * AI知识库管理控制层 - 后台管理接口
 *
 * @author system
 * @since 2025-11-05
 */
@RestController
@RequestMapping("/content/knowledge")
public class AiKnowledgeAdminController {

    @Autowired
    private AiKnowledgeService aiKnowledgeService;

    /**
     * 获取知识库列表（后台管理，包含所有状态）
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
     * 添加知识库
     */
    @PostMapping
    public ResponseResult addKnowledge(@RequestBody AiKnowledgeDTO dto) {
        return aiKnowledgeService.addKnowledge(dto);
    }

    /**
     * 更新知识库
     */
    @PutMapping
    public ResponseResult updateKnowledge(@RequestBody AiKnowledgeDTO dto) {
        return aiKnowledgeService.updateKnowledge(dto);
    }

    /**
     * 删除知识库
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteKnowledge(@PathVariable Long id) {
        return aiKnowledgeService.deleteKnowledge(id);
    }

    /**
     * 获取知识库详情
     */
    @GetMapping("/{id}")
    public ResponseResult getKnowledgeDetail(@PathVariable Long id) {
        return aiKnowledgeService.getKnowledgeDetail(id);
    }

    /**
     * 获取所有分类
     */
    @GetMapping("/categories")
    public ResponseResult getAllCategories() {
        return aiKnowledgeService.getAllCategories();
    }
}
