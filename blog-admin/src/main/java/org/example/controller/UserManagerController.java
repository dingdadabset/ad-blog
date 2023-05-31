package org.example.controller;

import com.sun.org.apache.regexp.internal.RE;
import org.example.conf.ResponseResult;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.service.impl.AdminUserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.controller
 * @Author: dingquan
 * @CreateTime: 2023-05-31  16:10
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("system/user")
public class UserManagerController {

    @Resource
    private UserService userService;
    @Resource
    private AdminUserServiceImpl AdminUserServiceImpl;
    @GetMapping("list")
    public ResponseResult getList(Integer pageNum,Integer pageSize,String userName,String phonenumber,String status){
        return userService.getList(pageNum,pageSize,userName,phonenumber,status);
    }
    @DeleteMapping("{id}")
    public ResponseResult delUserById(@PathVariable String id){
        return ResponseResult.okResult(userService.removeById(id));
    }

    @GetMapping("{id}")
    public ResponseResult getOneUserById(@PathVariable String id){
        return ResponseResult.okResult(AdminUserServiceImpl.getDetail(id));
    }
    @PutMapping ()
    public ResponseResult updateUser(@RequestBody User user){
        return ResponseResult.okResult(userService.save(user));
    }
}
