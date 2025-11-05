package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * AI知识库详情视图对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiKnowledgeDetailVO {
    
    private Long id;
    
    // 知识标题
    private String title;
    
    // 问题内容
    private String question;
    
    // 答案内容
    private String answer;
    
    // 知识分类
    private String category;
    
    // 标签
    private String tags;
    
    // 难度等级
    private Integer difficulty;
    
    // 浏览次数
    private Integer viewCount;
    
    // 点赞次数
    private Integer likeCount;
    
    // 创建时间
    private Date createTime;
    
    // 更新时间
    private Date updateTime;
}
