package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Strings;
import org.example.conf.AppHttpCodeEnum;
import org.example.conf.RedisCache;
import org.example.conf.ResponseResult;
import org.example.conf.SecurityUtils;
import org.example.dao.UserDao;
import org.example.entity.*;
import org.example.service.UserService;
import org.example.utils.BeanCopyUtils;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.nio.ch.SelChImpl;

import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-05-23 16:13:39
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired

    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
       //得到一个认证
        Authentication authenticate = authenticationManager.authenticate(token);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        LoginUser principal = (LoginUser) authenticate.getPrincipal();
        String id = principal.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        redisCache.setCacheObject("bloglogin:"+id,principal);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(principal.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);



    }

    @Override
    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userInfo() {
        Long userId = SecurityUtils.getUserId();
        User byId = getById(userId);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(byId, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);

        return ResponseResult.okResult();
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult register(User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult getList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> eq = new LambdaQueryWrapper<User>().eq(Strings.hasText(userName), User::getUserName, userName)
                .eq(Strings.hasText(phonenumber), User::getPhonenumber, pageNum)
                .eq(Strings.hasText(status), User::getStatus, status);
        Page<User> userPage = new Page<User>().setCurrent(pageNum).setSize(pageSize);
        Page<User> page = page(userPage, eq);

    return  ResponseResult.okResult(new UserVo(page.getRecords(),page.getTotal()));
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> eq = new LambdaQueryWrapper<User>().eq(User::getNickName, nickName);
        int count = count(eq);
        if (count>0){
            return true;
        }else {
            return false;
        }

    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> eq = new LambdaQueryWrapper<User>().eq(User::getUserName, userName);
        int count = count(eq);
        if (count>0){
            return true;
        }else {
            return false;
        }
    }
}

