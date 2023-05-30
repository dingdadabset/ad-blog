package org.example.controller;




import org.example.conf.ResponseResult;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用户表(User)表控制层
 *
 * @author makejava
 * @since 2023-05-23 16:13:39
 */
@RestController
//@RequestMapping("/user")
public class UserController<R> {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return userService.login(user);
    }
    @PutMapping("/user/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
    @GetMapping("/user/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param user 查询实体
     * @return 所有数据
     */
    @PostMapping("/logout")
    public ResponseResult logout(){
        return userService.logout();
    }
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return userService.updateViewCount(id);
    }
    //@GetMapping
    //public R selectAll(Page<User> page, User user) {
    //    return (this.userService.page(page, new QueryWrapper<>(user)));
    //}
    //
    ///**
    // * 通过主键查询单条数据
    // *
    // * @param id 主键
    // * @return 单条数据
    // */
    //@GetMapping("{id}")
    //public R selectOne(@PathVariable Serializable id) {
    //    return this.userService.getById(id);
    //}
    //
    ///**
    // * 新增数据
    // *
    // * @param user 实体对象
    // * @return 新增结果
    // */
    //@PostMapping
    //public R insert(@RequestBody User user) {
    //    return (this.userService.save(user));
    //}
    //
    ///**
    // * 修改数据
    // *
    // * @param user 实体对象
    // * @return 修改结果
    // */
    //@PutMapping
    //public R update(@RequestBody User user) {
    //    return (this.userService.updateById(user));
    //}
    //
    ///**
    // * 删除数据
    // *
    // * @param idList 主键结合
    // * @return 删除结果
    // */
    //@DeleteMapping
    //public R delete(@RequestParam("idList") List<Long> idList) {
    //    return (this.userService.removeByIds(idList));
    //}
}

