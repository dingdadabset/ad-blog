package org.example.controller;

import org.example.conf.ResponseResult;
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
    public ResponseResult selList(Integer pageNum,Integer pageSize,String roleName,String status){

        return roleService.selList(pageNum,pageSize,roleName,status);
    }
    @PutMapping("changeStatus")
    public ResponseResult changeStatus(String roleId,String status){
        Role byId = roleService.getById(roleId);
        byId.setStatus(status);

        return ResponseResult.okResult(roleService.save(byId));
    }
    @PostMapping()
    public ResponseResult addRole(@RequestBody Role role){

        return ResponseResult.okResult(roleService.save(role));
    }
    @GetMapping("{id}")
    public ResponseResult selOneRole(@PathVariable String id){

        return ResponseResult.okResult(roleService.getById(id));
    }
    @PutMapping()
    public ResponseResult selOneRole(@RequestBody Role role){

        return ResponseResult.okResult(roleService.save(role));
    }

}
