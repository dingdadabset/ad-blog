package org.example.controller;

import org.example.conf.ResponseResult;
import org.example.entity.Menu;
import org.example.entity.Role;
import org.example.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.controller
 * @Author: dingquan
 * @CreateTime: 2023-05-30  15:41
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("system/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("list")
    public ResponseResult selList(Integer pageNum, Integer pageSize, String roleName, String status) {

        return roleService.selList(pageNum, pageSize, roleName, status);
    }

    @PutMapping("changeStatus")
    public ResponseResult changeStatus(@RequestBody Role role) {
        Role byId = roleService.getById(role.getId());
        byId.setStatus(role.getStatus());

        return ResponseResult.okResult(roleService.save(byId));
    }

    @PostMapping()
    public ResponseResult addRole(@RequestBody Role role) {

        return ResponseResult.okResult(roleService.save(role));
    }

    @GetMapping("{id}")
    public ResponseResult selOneRole(@PathVariable String id) {

        return ResponseResult.okResult(roleService.getById(id));
    }

    @PutMapping()
    public ResponseResult updateOneRole(@RequestBody Role menu) {

        return ResponseResult.okResult(roleService.save(menu));
    }

    @DeleteMapping("{id}")
    public ResponseResult delOneRole(@PathVariable String id) {

        return ResponseResult.okResult(roleService.removeById(id));
    }

    @GetMapping("listAllRole")
    public ResponseResult listAllRole() {
/*todo 需要新增用户功能。新增用户时可以直接关联角色。

​	注意：新增用户时注意密码加密存储。

​	用户名不能为空，否则提示：必需填写用户名

​	用户名必须之前未存在，否则提示：用户名已存在

​    手机号必须之前未存在，否则提示：手机号已存在

​	邮箱必须之前未存在，否则提示：邮箱已存在*/
        return ResponseResult.okResult(roleService.list());
    }
}
