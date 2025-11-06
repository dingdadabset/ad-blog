package org.example.service;

import org.example.conf.ResponseResult;
import org.example.entity.UserLoginDTO;
import org.example.entity.UserRegisterDTO;

/**
 * 便捷的用户认证服务接口
 * 提供简化的注册和登录功能
 */
public interface AuthService {
    
    /**
     * 用户注册
     * 自动进行数据验证和密码加密
     * 
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    ResponseResult register(UserRegisterDTO registerDTO);
    
    /**
     * 用户登录
     * 支持用户名或邮箱登录
     * 
     * @param loginDTO 登录信息
     * @return 登录结果，包含token和用户信息
     */
    ResponseResult login(UserLoginDTO loginDTO);
    
    /**
     * 用户登出
     * 
     * @return 登出结果
     */
    ResponseResult logout();
    
    /**
     * 检查用户名是否可用
     * 
     * @param username 用户名
     * @return 是否可用
     */
    ResponseResult checkUsername(String username);
    
    /**
     * 检查邮箱是否可用
     * 
     * @param email 邮箱
     * @return 是否可用
     */
    ResponseResult checkEmail(String email);
}
