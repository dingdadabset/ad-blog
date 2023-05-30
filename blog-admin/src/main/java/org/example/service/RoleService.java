package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.conf.ResponseResult;
import org.example.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-05-26 15:56:35
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult selList(Integer pageNum, Integer pageSize, String roleName, String status);
}

