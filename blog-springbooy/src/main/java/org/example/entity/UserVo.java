package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.entity
 * @Author: dingquan
 * @CreateTime: 2023-05-31  16:18
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class UserVo {
    private List<User> rows;
    private Long total;
}
