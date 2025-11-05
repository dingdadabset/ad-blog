package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI知识库添加/更新DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiKnowledgeDTO {
    
    private Long id;
    
    private String title;
    
    private String question;
    
    private String answer;
    
    private String category;
    
    private String tags;
    
    private Integer difficulty;
    
    private Integer isPublic;
    
    private Integer status;
}
