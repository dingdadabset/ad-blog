package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.entity
 * @Author: dingquan
 * @CreateTime: 2023-05-23  15:20
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SgArticleVO {
        private String categoryId;
        private String categoryName;
        private String content;
        private Date createTime;
        private String id;
        private String isComment;
        private String title;
        private String viewCount;
}
