/**
  * Copyright 2023 json.cn 
  */
package org.example.entity;

import lombok.Data;

import java.util.Date;

/**
 * Auto-generated: 2023-05-30 15:47:49
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class RoleVo {

    private Long id;
    private String roleKey;
    private String roleName;
    private String roleSort;
    private Date createTime;
    private String status;

    public void setRoleKey(String roleKey) {
         this.roleKey = roleKey;
     }
     public String getRoleKey() {
         return roleKey;
     }

    public void setRoleName(String roleName) {
         this.roleName = roleName;
     }
     public String getRoleName() {
         return roleName;
     }

    public void setRoleSort(String roleSort) {
         this.roleSort = roleSort;
     }
     public String getRoleSort() {
         return roleSort;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

}