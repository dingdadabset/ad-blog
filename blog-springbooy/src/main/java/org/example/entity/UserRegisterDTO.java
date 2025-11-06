package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    
    // 用户名
    private String username;
    
    // 密码
    private String password;
    
    // 确认密码
    private String confirmPassword;
    
    // 昵称
    private String nickname;
    
    // 邮箱
    private String email;
}
