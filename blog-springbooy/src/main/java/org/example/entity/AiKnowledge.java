package org.example.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI知识库表(AiKnowledge)表实体类
 *
 * @author system
 * @since 2025-11-05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_ai_knowledge")
public class AiKnowledge implements Serializable {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    // 知识标题
    private String title;
    
    // 问题内容
    private String question;
    
    // 答案内容
    private String answer;
    
    // 知识分类
    private String category;
    
    // 标签（逗号分隔）
    private String tags;
    
    // 难度等级 (1初级 2中级 3高级)
    private Integer difficulty;
    
    // 浏览次数
    private Integer viewCount;
    
    // 点赞次数
    private Integer likeCount;
    
    // 是否公开 (0否 1是)
    private Integer isPublic;
    
    // 状态 (0草稿 1已发布 2已归档)
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
