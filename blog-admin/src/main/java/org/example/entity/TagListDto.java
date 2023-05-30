package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.entity
 * @Author: dingquan
 * @CreateTime: 2023-05-29  14:57
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {
    private Long id;
    private String name;

    //备注
    private String remark;
}
