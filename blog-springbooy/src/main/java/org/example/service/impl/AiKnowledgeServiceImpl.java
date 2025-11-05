package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.conf.AppHttpCodeEnum;
import org.example.conf.ResponseResult;
import org.example.dao.AiKnowledgeDao;
import org.example.entity.*;
import org.example.service.AiKnowledgeService;
import org.example.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * AI知识库表(AiKnowledge)表服务实现类
 *
 * @author system
 * @since 2025-11-05
 */
@Service("aiKnowledgeService")
public class AiKnowledgeServiceImpl extends ServiceImpl<AiKnowledgeDao, AiKnowledge> implements AiKnowledgeService {

    @Override
    public ResponseResult getKnowledgeList(Integer pageNum, Integer pageSize, String category, Integer difficulty, String keyword) {
        LambdaQueryWrapper<AiKnowledge> queryWrapper = new LambdaQueryWrapper<>();
        
        // 只查询已发布且公开的知识
        queryWrapper.eq(AiKnowledge::getStatus, 1)
                   .eq(AiKnowledge::getIsPublic, 1);
        
        // 分类筛选
        if (StringUtils.hasText(category)) {
            queryWrapper.eq(AiKnowledge::getCategory, category);
        }
        
        // 难度筛选
        if (Objects.nonNull(difficulty)) {
            queryWrapper.eq(AiKnowledge::getDifficulty, difficulty);
        }
        
        // 关键词搜索（标题或问题）
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                .like(AiKnowledge::getTitle, keyword)
                .or()
                .like(AiKnowledge::getQuestion, keyword)
                .or()
                .like(AiKnowledge::getTags, keyword)
            );
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(AiKnowledge::getCreateTime);
        
        // 分页查询
        Page<AiKnowledge> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        
        // 转换为VO
        List<AiKnowledgeListVO> knowledgeListVOs = BeanCopyUtils.copyBeanList(
            page.getRecords(), AiKnowledgeListVO.class);
        
        PageVo pageVo = new PageVo(knowledgeListVOs, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getKnowledgeListAdmin(Integer pageNum, Integer pageSize, String category, Integer difficulty, String keyword) {
        LambdaQueryWrapper<AiKnowledge> queryWrapper = new LambdaQueryWrapper<>();
        
        // 管理后台查询所有状态的知识，不过滤发布状态和公开状态
        
        // 分类筛选
        if (StringUtils.hasText(category)) {
            queryWrapper.eq(AiKnowledge::getCategory, category);
        }
        
        // 难度筛选
        if (Objects.nonNull(difficulty)) {
            queryWrapper.eq(AiKnowledge::getDifficulty, difficulty);
        }
        
        // 关键词搜索（标题或问题）
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                .like(AiKnowledge::getTitle, keyword)
                .or()
                .like(AiKnowledge::getQuestion, keyword)
                .or()
                .like(AiKnowledge::getTags, keyword)
            );
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(AiKnowledge::getCreateTime);
        
        // 分页查询
        Page<AiKnowledge> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        
        // 转换为VO
        List<AiKnowledgeListVO> knowledgeListVOs = BeanCopyUtils.copyBeanList(
            page.getRecords(), AiKnowledgeListVO.class);
        
        PageVo pageVo = new PageVo(knowledgeListVOs, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getKnowledgeDetail(Long id) {
        AiKnowledge knowledge = getById(id);
        if (Objects.isNull(knowledge)) {
            return ResponseResult.errorResult(500, "知识库不存在");
        }
        
        // 转换为详情VO
        AiKnowledgeDetailVO detailVO = BeanCopyUtils.copyBean(knowledge, AiKnowledgeDetailVO.class);
        
        return ResponseResult.okResult(detailVO);
    }

    @Override
    public ResponseResult addKnowledge(AiKnowledgeDTO dto) {
        AiKnowledge knowledge = new AiKnowledge();
        BeanUtils.copyProperties(dto, knowledge);
        knowledge.setViewCount(0);
        knowledge.setLikeCount(0);
        
        boolean saved = save(knowledge);
        if (!saved) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateKnowledge(AiKnowledgeDTO dto) {
        if (Objects.isNull(dto.getId())) {
            return ResponseResult.errorResult(500, "知识库ID不能为空");
        }
        
        AiKnowledge knowledge = getById(dto.getId());
        if (Objects.isNull(knowledge)) {
            return ResponseResult.errorResult(500, "知识库不存在");
        }
        
        BeanUtils.copyProperties(dto, knowledge);
        
        boolean updated = updateById(knowledge);
        if (!updated) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteKnowledge(Long id) {
        AiKnowledge knowledge = getById(id);
        if (Objects.isNull(knowledge)) {
            return ResponseResult.errorResult(500, "知识库不存在");
        }
        
        boolean removed = removeById(id);
        if (!removed) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 使用原子性的数据库操作来避免并发问题
        LambdaUpdateWrapper<AiKnowledge> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AiKnowledge::getId, id)
                    .setSql("view_count = view_count + 1");
        
        boolean updated = update(updateWrapper);
        if (!updated) {
            return ResponseResult.errorResult(500, "知识库不存在");
        }
        
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult likeKnowledge(Long id) {
        // 使用原子性的数据库操作来避免并发问题
        LambdaUpdateWrapper<AiKnowledge> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AiKnowledge::getId, id)
                    .setSql("like_count = like_count + 1");
        
        boolean updated = update(updateWrapper);
        if (!updated) {
            return ResponseResult.errorResult(500, "知识库不存在");
        }
        
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAllCategories() {
        LambdaQueryWrapper<AiKnowledge> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(AiKnowledge::getCategory)
                   .eq(AiKnowledge::getStatus, 1)
                   .eq(AiKnowledge::getIsPublic, 1)
                   .groupBy(AiKnowledge::getCategory);
        
        List<AiKnowledge> knowledgeList = list(queryWrapper);
        List<String> categories = knowledgeList.stream()
            .map(AiKnowledge::getCategory)
            .distinct()
            .collect(Collectors.toList());
        
        return ResponseResult.okResult(categories);
    }
}
