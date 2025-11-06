package org.example.controller;

import org.example.conf.ResponseResult;
import org.example.entity.UserLoginDTO;
import org.example.entity.UserRegisterDTO;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 便捷的用户认证控制器
 * 提供简化的注册和登录接口
 * 
 * @author system
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户注册
     * 
     * 接口地址: POST /auth/register
     * 
     * 请求体示例:
     * {
     *   "username": "testuser",
     *   "password": "abc123",
     *   "confirmPassword": "abc123",
     *   "nickname": "测试用户",
     *   "email": "test@example.com"
     * }
     * 
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserRegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }

    /**
     * 用户登录
     * 支持用户名或邮箱登录
     * 
     * 接口地址: POST /auth/login
     * 
     * 请求体示例:
     * {
     *   "username": "testuser",  // 或者使用邮箱: "test@example.com"
     *   "password": "abc123",
     *   "rememberMe": true       // 可选，记住我7天
     * }
     * 
     * @param loginDTO 登录信息
     * @return 登录结果，包含token和用户信息
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserLoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    /**
     * 用户登出
     * 
     * 接口地址: POST /auth/logout
     * 需要在请求头中携带token
     * 
     * @return 登出结果
     */
    @PostMapping("/logout")
    public ResponseResult logout() {
        return authService.logout();
    }

    /**
     * 检查用户名是否可用
     * 用于注册时实时验证
     * 
     * 接口地址: GET /auth/check-username?username=testuser
     * 
     * @param username 用户名
     * @return 是否可用
     */
    @GetMapping("/check-username")
    public ResponseResult checkUsername(@RequestParam String username) {
        return authService.checkUsername(username);
    }

    /**
     * 检查邮箱是否可用
     * 用于注册时实时验证
     * 
     * 接口地址: GET /auth/check-email?email=test@example.com
     * 
     * @param email 邮箱
     * @return 是否可用
     */
    @GetMapping("/check-email")
    public ResponseResult checkEmail(@RequestParam String email) {
        return authService.checkEmail(email);
    }
}
