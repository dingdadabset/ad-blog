package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.conf.AppHttpCodeEnum;
import org.example.conf.RedisCache;
import org.example.conf.ResponseResult;
import org.example.dao.UserDao;
import org.example.entity.*;
import org.example.service.AuthService;
import org.example.utils.BeanCopyUtils;
import org.example.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 便捷的用户认证服务实现
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private RedisCache redisCache;
    
    // 邮箱正则表达式
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    
    // 密码强度正则 (至少6位，包含字母和数字)
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

    @Override
    public ResponseResult register(UserRegisterDTO registerDTO) {
        // 1. 参数验证
        if (!StringUtils.hasText(registerDTO.getUsername())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(registerDTO.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(registerDTO.getEmail())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(registerDTO.getNickname())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        
        // 2. 验证两次密码是否一致
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            return ResponseResult.errorResult(500, "两次输入的密码不一致");
        }
        
        // 3. 验证密码强度
        if (!Pattern.matches(PASSWORD_PATTERN, registerDTO.getPassword())) {
            return ResponseResult.errorResult(500, "密码至少6位且必须包含字母和数字");
        }
        
        // 4. 验证邮箱格式
        if (!Pattern.matches(EMAIL_PATTERN, registerDTO.getEmail())) {
            return ResponseResult.errorResult(500, "邮箱格式不正确");
        }
        
        // 5. 检查用户名是否已存在
        LambdaQueryWrapper<User> usernameQuery = new LambdaQueryWrapper<>();
        usernameQuery.eq(User::getUserName, registerDTO.getUsername());
        if (userDao.selectCount(usernameQuery) > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
        }
        
        // 6. 检查昵称是否已存在
        LambdaQueryWrapper<User> nicknameQuery = new LambdaQueryWrapper<>();
        nicknameQuery.eq(User::getNickName, registerDTO.getNickname());
        if (userDao.selectCount(nicknameQuery) > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        
        // 7. 检查邮箱是否已存在
        LambdaQueryWrapper<User> emailQuery = new LambdaQueryWrapper<>();
        emailQuery.eq(User::getEmail, registerDTO.getEmail());
        if (userDao.selectCount(emailQuery) > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_EXIST);
        }
        
        // 8. 创建用户对象
        User user = new User();
        user.setUserName(registerDTO.getUsername());
        user.setNickName(registerDTO.getNickname());
        user.setEmail(registerDTO.getEmail());
        // 密码加密
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        // 设置默认值
        user.setType("0"); // 普通用户
        user.setStatus("0"); // 正常状态
        user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"); // 默认头像
        
        // 9. 保存到数据库
        int insert = userDao.insert(user);
        if (insert <= 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        
        return ResponseResult.okResult("注册成功");
    }

    @Override
    public ResponseResult login(UserLoginDTO loginDTO) {
        // 1. 参数验证
        if (!StringUtils.hasText(loginDTO.getUsername())) {
            return ResponseResult.errorResult(500, "用户名或邮箱不能为空");
        }
        if (!StringUtils.hasText(loginDTO.getPassword())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        
        // 2. 判断是邮箱还是用户名登录
        String loginField = loginDTO.getUsername();
        User user = null;
        
        // 如果包含@符号，认为是邮箱登录
        if (loginField.contains("@")) {
            LambdaQueryWrapper<User> emailQuery = new LambdaQueryWrapper<>();
            emailQuery.eq(User::getEmail, loginField);
            user = userDao.selectOne(emailQuery);
        } else {
            LambdaQueryWrapper<User> usernameQuery = new LambdaQueryWrapper<>();
            usernameQuery.eq(User::getUserName, loginField);
            user = userDao.selectOne(usernameQuery);
        }
        
        // 3. 检查用户是否存在
        if (Objects.isNull(user)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        
        // 4. 使用Spring Security进行认证
        UsernamePasswordAuthenticationToken authToken = 
            new UsernamePasswordAuthenticationToken(user.getUserName(), loginDTO.getPassword());
        
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        
        // 5. 判断是否认证通过
        if (Objects.isNull(authenticate)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        
        // 6. 获取用户信息并生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        
        // 根据是否记住我设置不同的过期时间
        String jwt;
        if (loginDTO.getRememberMe() != null && loginDTO.getRememberMe()) {
            // 记住我：7天过期
            jwt = JwtUtil.createJWT(userId, 7 * 24 * 60 * 60 * 1000L);
        } else {
            // 默认：24小时过期
            jwt = JwtUtil.createJWT(userId);
        }
        
        // 7. 把用户信息存入redis
        redisCache.setCacheObject("bloglogin:" + userId, loginUser);
        
        // 8. 封装返回数据
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !(authentication.getPrincipal() instanceof LoginUser)) {
            return ResponseResult.errorResult(500, "用户未登录");
        }
        
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        
        // 删除redis中的用户信息
        redisCache.deleteObject("bloglogin:" + userId);
        
        return ResponseResult.okResult("登出成功");
    }

    @Override
    public ResponseResult checkUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return ResponseResult.errorResult(500, "用户名不能为空");
        }
        
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getUserName, username);
        long count = userDao.selectCount(query);
        
        if (count > 0) {
            return ResponseResult.errorResult(500, "用户名已被使用");
        }
        
        return ResponseResult.okResult("用户名可用");
    }

    @Override
    public ResponseResult checkEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return ResponseResult.errorResult(500, "邮箱不能为空");
        }
        
        // 验证邮箱格式
        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            return ResponseResult.errorResult(500, "邮箱格式不正确");
        }
        
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getEmail, email);
        long count = userDao.selectCount(query);
        
        if (count > 0) {
            return ResponseResult.errorResult(500, "邮箱已被使用");
        }
        
        return ResponseResult.okResult("邮箱可用");
    }
}
