package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * AI知识库列表视图对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiKnowledgeListVO {
    
    private Long id;
    
    // 知识标题
    private String title;
    
    // 问题内容
    private String question;
    
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
}
