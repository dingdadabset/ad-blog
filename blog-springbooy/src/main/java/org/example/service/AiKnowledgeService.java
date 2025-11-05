package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.conf.ResponseResult;
import org.example.entity.AiKnowledge;
import org.example.entity.AiKnowledgeDTO;

/**
 * AI知识库表(AiKnowledge)表服务接口
 *
 * @author system
 * @since 2025-11-05
 */
public interface AiKnowledgeService extends IService<AiKnowledge> {
    
    /**
     * 获取知识库列表（分页）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param category 分类
     * @param difficulty 难度
     * @param keyword 关键词
     * @return 知识库列表
     */
    ResponseResult getKnowledgeList(Integer pageNum, Integer pageSize, String category, Integer difficulty, String keyword);
    
    /**
     * 获取知识库详情
     * @param id 知识库ID
     * @return 知识库详情
     */
    ResponseResult getKnowledgeDetail(Long id);
    
    /**
     * 添加知识库
     * @param dto 知识库DTO
     * @return 添加结果
     */
    ResponseResult addKnowledge(AiKnowledgeDTO dto);
    
    /**
     * 更新知识库
     * @param dto 知识库DTO
     * @return 更新结果
     */
    ResponseResult updateKnowledge(AiKnowledgeDTO dto);
    
    /**
     * 删除知识库
     * @param id 知识库ID
     * @return 删除结果
     */
    ResponseResult deleteKnowledge(Long id);
    
    /**
     * 增加浏览次数
     * @param id 知识库ID
     * @return 结果
     */
    ResponseResult updateViewCount(Long id);
    
    /**
     * 点赞知识库
     * @param id 知识库ID
     * @return 结果
     */
    ResponseResult likeKnowledge(Long id);
    
    /**
     * 获取所有分类
     * @return 分类列表
     */
    ResponseResult getAllCategories();
}
