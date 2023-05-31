package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.conf.ResponseResult;
import org.example.entity.User;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-05-23 16:13:39
 */
public interface UserService extends IService<User> {

    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult updateViewCount(Long id);

    ResponseResult getList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);
}

