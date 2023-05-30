package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.entity
 * @Author: dingquan
 * @CreateTime: 2023-05-22  18:44
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SgCategoryVO {
    private Long id;
    //分类名
    private String name;
    //访问量
    private Long viewCount;
    private String description;
}
