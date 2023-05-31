/**
  * Copyright 2023 json.cn 
  */
package org.example.entity;
import java.util.List;

/**
 * Auto-generated: 2023-05-31 16:29:47
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class UserAndRoleS {

    private List<String> roleIds;
    private List<Role> roles;
    private User user;

    public UserAndRoleS(List<String> roleIds, List<Role> roles, User user) {
        this.roleIds = roleIds;
        this.roles = roles;
        this.user = user;
    }

    public void setRoleIds(List<String> roleIds) {
         this.roleIds = roleIds;
     }
     public List<String> getRoleIds() {
         return roleIds;
     }

    public void setRoles(List<Role> roles) {
         this.roles = roles;
     }
     public List<Role> getRoles() {
         return roles;
     }

    public void setUser(User user) {
         this.user = user;
     }
     public User getUser() {
         return user;
     }

}