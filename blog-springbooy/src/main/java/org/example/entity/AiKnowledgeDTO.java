package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * AI知识库添加/更新DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiKnowledgeDTO {
    
    private Long id;
    
    @NotBlank(message = "标题不能为空")
    private String title;
    
    @NotBlank(message = "问题内容不能为空")
    private String question;
    
    @NotBlank(message = "答案内容不能为空")
    private String answer;
    
    @NotBlank(message = "分类不能为空")
    private String category;
    
    private String tags;
    
    @NotNull(message = "难度等级不能为空")
    private Integer difficulty;
    
    @NotNull(message = "公开状态不能为空")
    private Integer isPublic;
    
    @NotNull(message = "发布状态不能为空")
    private Integer status;
}
