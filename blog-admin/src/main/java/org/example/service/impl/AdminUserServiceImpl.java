package org.example.service.impl;

import org.example.conf.ResponseResult;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.entity.UserAndRoleS;
import org.example.mapper.RoleMapper;
import org.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example.service.impl
 * @Author: dingquan
 * @CreateTime: 2023-05-31  16:53
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class AdminUserServiceImpl extends UserServiceImpl {

    @Autowired
    private RoleMapper roleService;

    public ResponseResult getDetail(String id) {
        User byId = getById(id);
        List<Role> roles = roleService.selectUserIdFindRoles(byId.getId());
        List<String> rolesId = roles.stream().map(x -> x.getId().toString()).collect(Collectors.toList());
       return ResponseResult.okResult(new UserAndRoleS(rolesId,roles,byId));
    }
}
