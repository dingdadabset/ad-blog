/**
  * Copyright 2023 json.cn 
  */
package org.example.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Auto-generated: 2023-05-30 16:13:42
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Menus {

    private List<Menus> children;
    private String id;
    private String label;
    private String parentId;
}