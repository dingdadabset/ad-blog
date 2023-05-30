package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.entity
 * @Author: dingquan
 * @CreateTime: 2023-05-30  15:50
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo2 {
    private List<RoleVo> rows;
    private Integer total;
}
