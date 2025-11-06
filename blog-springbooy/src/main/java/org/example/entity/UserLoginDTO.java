package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    
    // 用户名或邮箱
    private String username;
    
    // 密码
    private String password;
    
    // 记住我（可选）
    private Boolean rememberMe;
}
